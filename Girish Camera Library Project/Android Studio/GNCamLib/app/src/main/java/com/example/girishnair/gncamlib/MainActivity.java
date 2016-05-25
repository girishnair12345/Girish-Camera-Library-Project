package com.example.girishnair.gncamlib;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import girish.cameraLibrary.CustomCamera;
import girish.cameraLibrary.OnPictureTaken;


public class MainActivity extends Activity implements OnClickListener, OnPictureTaken {

    private Button btnCustomCamera, btnDefaultCamera,btnFrontCamera;
    private TextView txtPath;
    private CustomCamera mCustomCamera;
    private ImageView imgCapture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCustomCamera = new CustomCamera(MainActivity.this);
        mCustomCamera.setPictureTakenListner(this);

        btnCustomCamera = (Button) findViewById(R.id.btnCustomCamera);
        btnCustomCamera.setOnClickListener(this);
        btnDefaultCamera = (Button) findViewById(R.id.btnDefaultCamera);
        btnDefaultCamera.setOnClickListener(this);
        btnFrontCamera = (Button) findViewById(R.id.btnFrontCamera);
        btnFrontCamera.setOnClickListener(this);

        imgCapture = (ImageView) findViewById(R.id.imgPic);
        txtPath = (TextView) findViewById(R.id.txtPath);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCustomCamera:
                mCustomCamera.startCamera();
                break;

            case R.id.btnDefaultCamera:
                mCustomCamera.sendCameraIntent();
                break;

            case R.id.btnFrontCamera:
                mCustomCamera.startCameraFront();
                break;
        }
    }

    @Override
    public void pictureTaken(Bitmap bitmap, File file) {
        imgCapture.setImageBitmap(bitmap);
        txtPath.setText(file.getAbsolutePath());
    }

}

