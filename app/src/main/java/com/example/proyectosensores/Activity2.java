package com.example.proyectosensores;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {
    Button btnVolver, btnFlash;
    Camera camera;
    boolean encendido = false;

    public Activity2() throws CameraAccessException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        btnVolver =findViewById(R.id.btnVolver);
        btnFlash = findViewById(R.id.btnFlash);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
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

}