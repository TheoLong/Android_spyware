import requests
from flask import Flask, request, Response, make_response, send_file
import io
import json
import pickle
import socket
import sys
from flask import abort


# led_addr = 'http://'+LED_ip
# custom_addr = 'http://'+custom_ip
#--------------set up flask--------------
app = Flask(__name__)

#--------------------------------web sever-------------------------------

#curl -X POST "http://localhost:8081/report_gps" -d id=device_01 -d gps=12412.21321\ 12312.312312
@app.route('/report_gps', strict_slashes=True, methods=['POST'])
def gps():
    id = request.form['id']
    gps = request.form['gps']
    print('==========>  Got gps:',id,gps)
    return ' '.join([id,gps]) + '\n'

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=8081, debug=True)
