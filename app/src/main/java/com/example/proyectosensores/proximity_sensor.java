package com.example.proyectosensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class proximity_sensor extends AppCompatActivity {

    SensorManager sm;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    Button btnFlash;
    boolean encendido = false;
    TextView txtTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_sensor);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        btnFlash = findViewById(R.id.btnFlash);
        txtTexto = findViewById(R.id.txtTexto);

        if(sensor == null) {
            finish();
        }

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String cameraId = null; // usualmente la camara delantera esta en la posicion 0
                try {
                    cameraId = camManager.getCameraIdList()[0];
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                if(sensorEvent.values[0] < sensor.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                    txtTexto.setText("Linterna encendida");
                    txtTexto.setTextColor(Color.BLACK);
                            try {
                                camManager.setTorchMode(cameraId, true);

                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                }else{
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    txtTexto.setText("Linterna apagada");
                    txtTexto.setTextColor(Color.WHITE);

                    try {
                        camManager.setTorchMode(cameraId, false);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sm.registerListener(sensorEventListener,sensor,2*1000*1000);
    }
}