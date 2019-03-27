import gmplot

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
        