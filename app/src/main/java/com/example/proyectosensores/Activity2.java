package com.example.proyectosensores;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Activity2 extends AppCompatActivity {
    Button btnVolver, btnFlash, btnProximidad,btnGPS;
    Camera camera;
    TextView tvLatitud,tvLongitud,tvUbicacion;
    boolean encendido = false;

    public Activity2() throws CameraAccessException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        btnVolver =findViewById(R.id.btnVolver);
        btnFlash = findViewById(R.id.btnFlash);
        btnProximidad = findViewById(R.id.btnProximidad);
        btnGPS = findViewById(R.id.btnGPS);
        tvLatitud = findViewById(R.id.tvLatitud);
        tvLongitud = findViewById(R.id.tvLongitud);
        tvUbicacion = findViewById(R.id.tvUbicacion);

        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){

          if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

          }else{

              ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
          }

        }

        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationManager locationManager = (LocationManager) Activity2.this.getSystemService(Context.LOCATION_SERVICE);

                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        tvLatitud.setText("LATITUD: " + location.getLatitude());
                        tvLongitud.setText("LONGITUD: " + location.getLongitude());

                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        try {
                            List<Address> direccion = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            tvUbicacion.setText(direccion.get(0).getLocality());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) { }

                    @Override
                    public void onProviderEnabled(String s) { }

                    @Override
                    public void onProviderDisabled(String s) { }
                };
                int permissionCheck = ContextCompat.checkSelfPermission(Activity2.this,Manifest.permission.ACCESS_FINE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        });



        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });

        btnProximidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProximity();
            }
        });
        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String cameraId = null; // usualmente la camara delantera esta en la posicion 0
                try {
                    cameraId = camManager.getCameraIdList()[0];
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                try {
                    if(!encendido){
                        camManager.setTorchMode(cameraId, true);
                        btnFlash.setText("Apagar linterna");
                        encendido = true;
                    } else{
                        camManager.setTorchMode(cameraId, false);
                        btnFlash.setText("Encender linterna");
                        encendido = false;
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void openMainActivity(){
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
    }

    public void openProximity(){
        Intent intent2 = new Intent(this, proximity_sensor.class);
        startActivity(intent2);
    }



}