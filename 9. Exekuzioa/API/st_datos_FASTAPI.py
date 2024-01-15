from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware  # Importa el middleware CORS

app = FastAPI()



# Configura CORS para permitir solicitudes desde todos los dominios
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)



class Item(BaseModel):
    ruta: str



@app.post('/datuak_berritu')
async def datuak_transferentzia(item: Item):
    try:
        # SQLite datu basera konexioa ireki
        sqlite_conn = sqlite3.connect(item.ruta)
        sqlite_cursor = sqlite_conn.cursor()

        # Postgres datu basera konexioa egin
        postgres_conn = psycopg2.connect(
            database="st_db",
            user="odoo",
            password="odoo",
            host="10.23.28.192",
            port="5434"
        )
        
        postgres_cursor = postgres_conn.cursor()

        # SQLiteko datuak hartu eta Postgresera pasatu
        sqlite_cursor.execute("SELECT * FROM table_name")
        data = sqlite_cursor.fetchall()

        # Postgres-era datuak sartu
        for row in data:
            postgres_cursor.execute("INSERT INTO table_name (NAN, izena, abizena, denbora, puntuaketa) VALUES (%s, %s, %s, %s, %s)",
                                    (row[0], row[1], row[2], row[3], row[4]))

        # Commit egin datuak gordetzeko
        postgres_conn.commit()

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

    finally:
        # Konexioak itxi
        sqlite_conn.close()
        postgres_conn.close()

    return JSONResponse(content={"Mezua": "Datuak berritu dira."}, status_code=200)



@app.get('/lortu_datuak')
async def lortu_datuak():
    try:
        # Postgres datu basera konexioa egin
        postgres_conn = psycopg2.connect(
            database="st_db",
            user="odoo",
            password="odoo",
            host="10.23.28.192",
            port="5434"
        )
        
        postgres_cursor = postgres_conn.cursor()

        # Adibide bat: Postgres-etik datuak irakurri
        postgres_cursor.execute("SELECT * FROM table_name")
        data = postgres_cursor.fetchall()

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

    finally:
        # Konexioa itxi
        postgres_conn.close()

    return JSONResponse(content={"data": data}, status_code=200)


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
