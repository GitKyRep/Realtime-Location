package com.rizkysaraan.livelocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    private static final int PERMISSIONS_REQUEST = 1;
    Button btnShowMap;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShowMap = findViewById(R.id.btnShowMap);
        btnRegister= findViewById(R.id.btnRegister);

        btnShowMap .setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent showMap = new Intent(MainActivity.this,ShowMap.class);
                startActivity(showMap);
            }
        });

        btnRegister .setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerAccoount = new Intent(MainActivity.this,RegisterAccountFirebase.class);
                startActivity(registerAccoount);
            }
        });
        //setContentView(R.layout.activity_main);
        // Check GPS is enabled
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }


    }

    private void startTrackerService() {
        startService(new Intent(this, TrackerService.class));
        //finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the service when the permission is granted
            startTrackerService();
        } else {
            finish();
        }
    }

}
