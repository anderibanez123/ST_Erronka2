from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from fastapi.responses import JSONResponse

app = FastAPI()

class Item(BaseModel):
    ruta: str

@app.get('/datuak_berritu')
async def datuak_transferentzia(item: Item):
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
    try:
        
        # Adibide bat: SQLite-tik datuak irakurri
        sqlite_cursor.execute("SELECT * FROM table_name")
        data = sqlite_cursor.fetchall()

        # Adibide bat: Postgres-era datuak sartu
        for row in data:
            postgres_cursor.execute("INSERT INTO table_name VALUES (%s, %s)", (row[0], row[1]))

        # Commit egin datuak gordetzeko
        postgres_conn.commit()
        
        
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    finally:
        
        # Konexioak itxi
        sqlite_conn.close()
        postgres_conn.close()

    return JSONResponse(content={"Mezua": "Datuak berritu dira."}, status_code=200)
