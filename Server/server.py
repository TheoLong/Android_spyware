import requests
from flask import Flask, request, Response, make_response, send_file, abort
import datetime
import gmplot


# led_addr = 'http://'+LED_ip
# custom_addr = 'http://'+custom_ip
#--------------set up flask--------------
app = Flask(__name__)


#--------------setup google map ----------------

def plot():
    data = {}
    gmap = gmplot.GoogleMapPlotter(37.23710123168917, -80.43514623283004,15) 
    with open('gpsResult.txt') as f:
        for l in f:
            id,gps,time = l.split('|')
            if id not in data:
                data[id] = {}
                data[id]['latt'] = []
                data[id]['long'] = []

            gps = gps.split()
            gps[0],gps[1] = float(gps[0]),float(gps[1])
            data[id]['latt'].append(gps[0])
            data[id]['long'].append(gps[1])



    for id in data.keys():
        latt,lon = data[id]['latt'],data[id]['long']
        gmap.marker(latt[-1], lon[-1], 'cornflowerblue')
        # gmap.scatter( latt[-2:], lon[-2:], '# FF0000',  size = 10, marker = True )
        gmap.plot(latt, lon, 'cornflowerblue', edge_width = 1) 
        gmap.heatmap(latt, lon,radius = 15)

    gmap.draw('map.html')

#--------------------------------web sever-------------------------------

#curl -X POST "http://73.12.117.100:8081/report_gps" -d id=device_01 -d gps=12412.21321\ 12312.312312
@app.route('/report_gps', strict_slashes=True, methods=['POST'])
def gps():
    id = request.form['id']
    gps = request.form['gps']
    print('==========>  ID:',id)
    print('==========> GPS:',gps)
    now = datetime.datetime.now()
    with open('gpsResult.txt','a') as f:
        f.write('|'.join([id,gps,str(now)],))
        f.write('\n')
    plot()

    return ' '.join([id,gps]) + '\n'


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=8081, debug=True)


