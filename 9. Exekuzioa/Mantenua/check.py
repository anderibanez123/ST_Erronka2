import time
import threading
import requests
import psycopg2
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

# Zerbitzuak egiaztatzeko konfigurazioa
services = [
    {"name": "API", "url": "http://10.23.28.190:8012/ping", "expected_response": "¡API ondo dabil, OK!"},
    {"name": "Odoo", "url": "http://10.23.28.192:8069", "expected_response": "Odoo"},
]

# Email konfigurazioa
sender_email = "st.enpresa.sl@gmail.com"
receiver_email = "aibanez22@izarraitz.eus"
password = "kifc lhgh fldv ayjk"

def send_email(subject, message):
    msg = MIMEMultipart()
    msg['From'] = sender_email
    msg['To'] = receiver_email
    msg['Subject'] = subject
    msg.attach(MIMEText(message, 'plain'))

    with smtplib.SMTP('smtp.gmail.com', 587) as server:
        server.starttls()
        server.login(sender_email, password)
        text = msg.as_string()
        server.sendmail(sender_email, receiver_email, text)

def check_service(service):
    try:
        if service["name"] == "PostgreSQL":
            # Konexioa PostgreSQLean saiatu
            conn = psycopg2.connect(
                host="10.23.28.192",  
                port="5434",
                user="odoo",
                password="odoo"
            )
            # Konexioa egokia bada, ez bidali emailik
            conn.close()
        else:
            # Beste zerbitzuak egiaztatu lehen bezala
            response = requests.get(service["url"])
            if service["expected_response"] not in response.text:
                send_email("Kontuz: Zerbitzu {} geldituta".format(service["name"]),
                           "Zerbitzuak {} martxan ez dago.".format(service["name"]))
    except Exception as e:
        send_email("Errorea zerbitzua {} egiaztatzean".format(service["name"]),
                   "Zerbitzua {} egiaztatzean errorea gertatu da: {}".format(service["name"], str(e)))

def check_services():
    while True:
        for service in services:
            thread = threading.Thread(target=check_service, args=(service,))
            thread.start()
            thread.join()

        time.sleep(600)  # Hurrengo egiaztapena egin baino lehen 10 minutu itxaron

if __name__ == "__main__":
    check_services()
