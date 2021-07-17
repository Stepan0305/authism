package com.authism.connect.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.authism.connect.Helpers.ArraysKeeper;
import com.authism.connect.Models.Category;
import com.authism.connect.Models.DbHelper;
import com.authism.connect.R;

import java.io.File;
import java.util.ArrayList;

public class ChillActivity extends AppCompatActivity {
    LinearLayout box;
    int width, height;
    ImageButton home, settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chill);

        box = findViewById(R.id.boxChill);
        home = findViewById(R.id.btnHomeChill);
        settings = findViewById(R.id.btnBackChill);

        //берем размеры экрана
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        generateCards();
    }
    public void onChillClick(View v){
        if (v.getId() == R.id.btnHomeChill){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }else if (v.getId() == R.id.btnBackChill){
            finish();
        }
    }

    private void generateCards() {
        //добавление карточек в box
        ArrayList<Category> categories = new ArrayList<>(ArraysKeeper.chillCards); //встроенные карточки
        DbHelper dbHelper = new DbHelper(this);
        categories.addAll(dbHelper.getAllChildCategories("Отдых")); //кастомные карточки
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
                        mediaPlayer = MediaPlayer.create(ChillActivity.this, category.getSound());
                    } else {
                        mediaPlayer = MediaPlayer.create(ChillActivity.this, Uri.parse(category.getSoundPath()));
                    }
                    mediaPlayer.start();

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
                Intent i = new Intent(ChillActivity.this, AddNewCardActivity.class);
                i.putExtra("parent", "Отдых");
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