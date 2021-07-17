package com.authism.connect.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.authism.connect.R;

public class MainActivity extends AppCompatActivity {
Button btnCommunication, btnGallery;
ImageButton btnSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCommunication = findViewById(R.id.btnCommunication);
        btnGallery = findViewById(R.id.btnGallery);
        btnSettings = findViewById(R.id.btnSettings);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO}, 1);
    }

    public void onMainClick(View v){
        switch (v.getId()){
            case R.id.btnCommunication:
                Intent i = new Intent(MainActivity.this, CommunicationActivity.class);
                startActivity(i);
                break;
            case R.id.btnGallery:
                Intent i1 = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(i1);
                break;
            case R.id.btnSettings:

            default:
                break;
        }
    }
}