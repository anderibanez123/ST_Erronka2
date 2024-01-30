import time
import requests
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

# Configuración de los servicios a comprobar
services = [
    {"name": "API", "url": "http://10.23.28.190:8012/ping", "expected_response": "¡API ondo dabil, OK!"},
    {"name": "Odoo", "url": "http://10.23.28.192:8069", "expected_response": "Odoo"},
    {"name": "PostgreSQL", "url": "http://10.23.28.192:5434/", "expected_response": "PostgreSQL"}
]

# Configuración del correo electrónico
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

def check_services():
    while True:
        for service in services:
            try:
                response = requests.get(service["url"])
                if response.text.strip() != service["expected_response"]:
                    send_email("Alerta: Servicio {} parado".format(service["name"]),
                               "El servicio {} no está en marcha.".format(service["name"]))
            except Exception as e:
                send_email("Error al verificar servicio {}".format(service["name"]),
                           "Se produjo un error al verificar el servicio {}: {}".format(service["name"], str(e)))

        time.sleep(600)  # Esperar 10 minutos antes de realizar la próxima comprobación

if __name__ == "__main__":
    check_services()
