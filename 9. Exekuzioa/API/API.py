
# Instalazioa egiteko "pip install Flask psycopg2"
from flask import Flask, jsonify
import sqlite3
import psycopg2

app = Flask(__name__)

@app.route('/datuak_berritu', methods=['GET'])
def transferir_datos():
    
    # SQLite datu basera konexioa ireki
    sqlite_conn = sqlite3.connect('ruta/de/tu/bd.sqlite')
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

    # Konexioak itxi
    sqlite_conn.close()
    postgres_conn.close()

    return jsonify({"Mezua": "Datuak berritu dira."})

if __name__ == '__main__':
    app.run(debug=True)
