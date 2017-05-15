package com.example.manh.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ID_READ_WRITE_PERMISSION = 99;
    Button btnCapture;
    ImageView imgPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hello, I want to test a bit
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handingCapture();
            }
        });
    }

    private void handingCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int REQUEST_ID_IMAGE_CAPTURE = 100;

        startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT>=23) {
            int readPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (writePermission != PackageManager.PERMISSION_GRANTED ||
                    readPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_ID_READ_WRITE_PERMISSION
                );
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case (REQUEST_ID_READ_WRITE_PERMISSION): {
                if (grantResults.length > 1
                        && grantResults[0]==PackageManager.PERMISSION_GRANTED
                        && grantResults[1]==PackageManager.PERMISSION_GRANTED) {

                            Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
                            handingCapture();;
                }
            }
        }
    }

    private void addControls() {
        btnCapture = (Button) findViewById(R.id.btnCapture);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);
    }
}
