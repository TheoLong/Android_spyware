package com.example.myfirstapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import stuff
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.*;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button access;
    LocationManager manager;
    LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //----text and button
        text = (TextView) findViewById(R.id.textView);
        access = (Button) findViewById(R.id.button);
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        HttpURLConnection urlConn = null;
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                HttpURLConnection client;
                try {
                    URL url = new URL("http://exampleurl.com/");
                    client = (HttpURLConnection) url.openConnection();
                    client.setRequestMethod("POST");
                    client.setRequestProperty("Key","Value");
                    client.setDoOutput(true);
                    //
                    OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
                    outputPost.flush();
                    outputPost.close();

                }


                catch(MalformedURLException error) {
                    //Handles an incorrectly entered URL
                }
                catch (IOException error) {
                    //Handles input and output errors
                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.requestLocationUpdates("gps", 5000, 20, listener);
                gettext();
            }
        });

    }

    public void gettext()
    {
        Toast.makeText(getApplicationContext(), "gps accessed", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
