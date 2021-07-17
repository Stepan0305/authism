package com.authism.connect.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.authism.connect.Models.Category;
import com.authism.connect.Models.DbHelper;
import com.authism.connect.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNewCardActivity extends AppCompatActivity {
    ImageButton btnBack, btnHome;
    Button btnRecord, btnSave;
    ImageView photo;
    EditText cardName;
    MediaRecorder recorder;
    boolean recording = false;
    String currentMusicPath, currentPicturePath;
    String parent;
    TextView recordInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);

        btnBack = findViewById(R.id.btnBackNewCard);
        btnHome = findViewById(R.id.btnHomeNewCard);
        btnSave = findViewById(R.id.saveCard);
        btnRecord = findViewById(R.id.recordAudio);
        photo = findViewById(R.id.addPhoto);
        cardName = findViewById(R.id.newCardName);
        recordInfo = findViewById(R.id.hasRecord);

        parent = getIntent().getStringExtra("parent");
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE) &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(AddNewCardActivity.this, new String[]{
                    Manifest.permission.RECORD_AUDIO}, 1);

        }
    }

    public void onNewCardClick(View v) {
        if (v.getId() == btnBack.getId()) {
            finish();
        } else if (v.getId() == btnHome.getId()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (v.getId() == btnRecord.getId()) {
            if (!recording) {
                try {
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setOutputFile(getFile("mp3"));
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    recorder.prepare();
                    recorder.start();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                btnRecord.setText("Завершить");
                recordInfo.setText("Запись идет...");
            } else {
                recorder.stop();
                recorder.release();
                recorder = null;
                btnRecord.setText("Записать аудио");
                recordInfo.setText("Записано!");
            }
            recording = !recording;
        } else if (v.getId() == photo.getId()) {
            //взять фото
            Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 1);

        }else if (v.getId() == btnSave.getId()){
            if (cardName.getText() != null && currentPicturePath != null && currentMusicPath != null){
                //сохранение в бд
                DbHelper dbHelper = new DbHelper(this);
                dbHelper.createCategory(new Category(cardName.getText().toString(), currentMusicPath,
                        currentPicturePath, parent));
                Toast.makeText(this, "Сохранено!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getFile(String format) {
        //создание файла в системе для фото или аудио
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File directory;
        if (format.equals("mp3")) {
            directory = wrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        } else {
            directory = wrapper.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        File file = new File(directory, getFileName() + "." + format);
        if (format.equals("mp3")) {
            currentMusicPath = file.getPath();
        } else currentPicturePath = file.getPath();
        return file.getPath();
    }

    private String getFileName() {
        return new SimpleDateFormat("dd-MM-yyyy HH.mm.ss", Locale.getDefault()).format(new Date());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                Uri chosenImageUri = data.getData();

                Bitmap bitmap = null;
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                photo.setImageBitmap(bitmap);
                File file = new File(getFile("jpg"));
                OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}