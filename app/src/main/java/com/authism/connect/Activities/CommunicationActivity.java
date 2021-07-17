package com.authism.connect.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.authism.connect.Helpers.ArraysKeeper;
import com.authism.connect.Models.Category;
import com.authism.connect.Models.DbHelper;
import com.authism.connect.R;

import java.io.File;
import java.util.ArrayList;
/**
 * в этой и подобных Activity основную роль выполняет функция generateCards()
 * сначала в ArrayList вносятся все карточки (как встроенные, так и кастомные)
 * встроенные карточки отличаются от кастомных тем, что картинка и звук и них хранятся в качестве ресурсов
 * вызываются ресурсы функциями getSound(), getPicture()
 * в кастомных карточках вышестоящие функции возвращают null
 * вместо них используются getSoundPath(), getPicturePath()
 * эти функции возвращают местоположение файлов в памяти телефона
 * сохраняются эти файлы по адресу Android/data/com.authism.connect/Files/Music или Pictures
 * поэтому каждый раз, прежде чем поставить картинку или звук, нужна проверка, кастомная это карточка или встроенная
 * */
public class CommunicationActivity extends AppCompatActivity {
    LinearLayout box;
    int width, height;
    ImageButton home, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        box = findViewById(R.id.boxCommunication);
        home = findViewById(R.id.btnHomeCommunication);
        settings = findViewById(R.id.btnBackCommunication);
        //берем размеры экрана
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        generateCards();
    }

    public void onCommunicationClick(View v) {
        if (v.getId() == R.id.btnHomeCommunication) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } else if (v.getId() == R.id.btnBackCommunication) {
            finish();
        }
    }

    private void generateCards() {
        //добавление карточек в box
        ArrayList<Category> categories = new ArrayList<>(ArraysKeeper.communicationCards);;
        DbHelper dbHelper = new DbHelper(this);
        categories.addAll(dbHelper.getAllChildCategories("Общение"));
        for (Category category : categories) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setGravity(Gravity.CENTER);
            card.setBackgroundColor(getResources().getColor(R.color.white));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) height / 3, (int) (height / 2.5));
            params.setMarginEnd(10);
            card.setLayoutParams(params);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer mediaPlayer;
                    if (category.getSoundPath() == null) {
                        mediaPlayer = MediaPlayer.create(CommunicationActivity.this, category.getSound());
                    } else {
                        mediaPlayer = MediaPlayer.create(CommunicationActivity.this, Uri.parse(category.getSoundPath()));
                    }
                    mediaPlayer.start();
                    switch (category.getName()) {
                        case "Есть":
                            Intent i = new Intent(CommunicationActivity.this, EatActivity.class);
                            startActivity(i);
                            break;
                        case "Играть":
                            Intent i2 = new Intent(CommunicationActivity.this, PlayActivity.class);
                            startActivity(i2);
                            break;
                        case "Пойти":
                            Intent i3 = new Intent(CommunicationActivity.this, GoActivity.class);
                            startActivity(i3);
                            break;
                        case "Гигиена":
                            Intent i4 = new Intent(CommunicationActivity.this, HygieneActivity.class);
                            startActivity(i4);
                            break;
                        case "Пить":
                            Intent i5 = new Intent(CommunicationActivity.this, DrinkActivity.class);
                            startActivity(i5);
                            break;
                        case "Рисовать":
                            Intent i6 = new Intent(CommunicationActivity.this, DrawActivity.class);
                            startActivity(i6);
                            break;
                        case "Отдыхать":
                            Intent i7 = new Intent(CommunicationActivity.this, ChillActivity.class);
                            startActivity(i7);
                            break;
                    }
                }
            });
            ImageView img = new ImageView(this);
            if (category.getPicturePath() == null) {
                img.setImageResource(category.getPicture());
            } else {
                File imgFile = new File(category.getPicturePath());
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    img.setImageBitmap(myBitmap);
                }
            }
            img.setLayoutParams(new LinearLayout.LayoutParams(height / 4, height / 4));
            card.addView(img);
            TextView tv = new TextView(this);
            tv.setText(category.getName());
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            card.addView(tv);
            box.addView(card);
        }
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER);
        card.setBackgroundColor(getResources().getColor(R.color.white));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) height / 3, (int) (height / 2.5));
        params.setMarginEnd(10);
        card.setLayoutParams(params);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CommunicationActivity.this, AddNewCardActivity.class);
                i.putExtra("parent", "Общение");
                startActivity(i);
            }
        });
        ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.add);
        img.setLayoutParams(new LinearLayout.LayoutParams(height / 4, height / 4));
        card.addView(img);
        TextView tv = new TextView(this);
        tv.setText("Добавить карточку");
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        card.addView(tv);
        box.addView(card);
    }
}