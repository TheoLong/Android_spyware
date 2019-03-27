import serial
import datetime

# ========  Init   ========
ser = serial.serial('/dev/ttyACM0',9600)

with open(datetime.datetime.now(),'a') as f:
    f.write('1')


# while 1:
