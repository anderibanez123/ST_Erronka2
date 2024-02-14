from fastapi import FastAPI, HTTPException, Depends
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from fastapi.responses import JSONResponse
from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, Session
from typing import List
import psycopg2


# SQLAlchemy erabiltzeko datu-basea definitu
Base = declarative_base()


# Jokalarien datu-mota
class Jokalariak(BaseModel):
    id: int
    izena: str
    abizena: str
    nan: str
    puntuaketa: int
    denbora: int


# Txapelketen datu-mota
class TxapelketaTxapelketa(Base):
    __tablename__ = 'txapelketa_txapelketa'

    id = Column(Integer, primary_key=True, index=True)
    izena = Column(String, index=True)
    abizena = Column(String, index=True)
    nan = Column(String, unique=True, index=True)
    puntuaketa = Column(Integer)
    denbora = Column(Integer)


# Datu-basearen konexioa sortu
DATABASE_URL = "postgresql://odoo:odoo@10.23.28.192:5434/st_db"
engine = create_engine(DATABASE_URL)
Base.metadata.create_all(bind=engine)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)


# FastAPI aplikazioa sortu
app = FastAPI()


# CORS Middlewarea gehitu
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# Ranking klasea definitu
class Ranking(BaseModel):
    #id: int
    izena: str
    abizena: str
    nan: str
    puntuaketa: int
    denbora: int


# Datu-basea lortzeko funtzioa
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


# Datuak berritzeko API endpoint-a
@app.post('/datuak_berritu')
async def datuak_transferentzia(ranking_list: List[Ranking], db: Session = Depends(get_db)):
    try:
        for ranking in ranking_list:
            db_entry = db.query(TxapelketaTxapelketa).filter(TxapelketaTxapelketa.nan == ranking.nan).first()

            if db_entry:
                db_entry.denbora = ranking.denbora
                db_entry.puntuaketa = ranking.puntuaketa
            else:
                db_entry = TxapelketaTxapelketa(**ranking.dict())
                db.add(db_entry)

        db.commit()
        return JSONResponse(content={"Mezua": "Datuak berritu dira."}, status_code=200)

    except HTTPException as http_exc:
        print(f"HTTPException: {http_exc}")
        raise http_exc

    except Exception as e:
        print(f"Exception: {e}")
        raise HTTPException(status_code=500, detail=str(e))


# Datuak lortzeko API endpoint-a
@app.get('/lortu_datuak', response_model=List[Jokalariak])
async def lortu_datuak(db: Session = Depends(get_db)):
    try:
        
        data = db.query(TxapelketaTxapelketa).order_by(TxapelketaTxapelketa.puntuaketa.desc()).all()

        # JSONera pasa datuak
        result_data = [{"id": row.id, "izena": row.izena, "abizena": row.abizena, "nan": row.nan,
                        "puntuaketa": row.puntuaketa, "denbora": row.denbora} for row in data]

        return JSONResponse(content={"Jokalariak": result_data}, status_code=200)

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


# Ping endpoint-a
@app.get("/ping")
def ping():
    return {"message": "¡API ondo dabil, OK!"}


# Server exekutatzeko kodea
if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8012)