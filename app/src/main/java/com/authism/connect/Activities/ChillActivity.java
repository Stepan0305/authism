package com.authism.connect.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.authism.connect.Helpers.ArraysKeeper;
import com.authism.connect.Helpers.MyScrollView;
import com.authism.connect.Models.Category;
import com.authism.connect.Models.DbHelper;
import com.authism.connect.R;

import java.io.File;
import java.util.ArrayList;

public class ChillActivity extends AppCompatActivity {
    LinearLayout box, finalBox;
    int width, height;
    ImageButton home, settings;
    MyScrollView scrollView;
    int finalSound;
    String strFinalSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chill);

        box = findViewById(R.id.boxChill);
        home = findViewById(R.id.btnHomeChill);
        settings = findViewById(R.id.btnBackChill);
        scrollView = findViewById(R.id.scrollChill);
        finalBox = findViewById(R.id.finalBoxChill);
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

    @SuppressLint("ClickableViewAccessibility")
    private void generateCards() {
        //добавление карточек в box
        ArrayList<Category> categories = new ArrayList<>(ArraysKeeper.chillCards);
        initFinal(categories.get(0));
        initFinal(categories.get(1));
        categories.remove(0);
        categories.remove(0);
        DbHelper dbHelper = new DbHelper(this);
        categories.addAll(dbHelper.getAllChildCategories("Отдых"));
        for (Category category : categories) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setGravity(Gravity.CENTER);
            card.setBackgroundColor(getResources().getColor(R.color.white));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) height / 4, (int) (height / 3.5));
            params.setMarginEnd(10);
            card.setLayoutParams(params);
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
            img.setLayoutParams(new LinearLayout.LayoutParams(height / 5, height / 5));
            card.addView(img);
            TextView tv = new TextView(this);
            tv.setText(category.getName());
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            card.addView(tv);
            box.addView(card);
            //так как из onTouch нельзя напрямую обращаться к переменным, создаем массив
            final float[] coordinates = {0, 0};
            card.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (coordinates[0] == 0 && coordinates[1] == 0) {
                        coordinates[0] = view.getX();
                        coordinates[1] = view.getY();
                        Log.d("coord", "x " + coordinates[0] + ", y" + coordinates[1]);
                    }

                    float xDown = 0, yDown = 0;
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            xDown = event.getX();
                            yDown = event.getY();
                            scrollView.setScrollingEnabled(false);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float movedX, movedY;
                            movedX = (float) (event.getX() - view.getWidth() / 2.0);
                            movedY = (float) (event.getY() - view.getHeight() / 2.0);
                            Log.d("inf", view.getY() + " of " + box.getHeight());
                            if (view.getY() > box.getHeight() * 0.6) {
                                box.removeView(card);
                                addCardToFinal(card, category);
                                break;
                            }
                            float dX = movedX - xDown;
                            float dY = movedY - yDown;
                            view.setX(view.getX() + dX);
                            view.setY(view.getY() + dY);
                            break;
                        case MotionEvent.ACTION_UP:
                            if (category.getName().equals("Я")) {
                                view.setX(0);
                                view.setY(0);
                            } else {
                                view.setX(coordinates[0]);
                                view.setY(coordinates[1]);
                            }
                            scrollView.setScrollingEnabled(true);
                            break;
                    }
                    return true;
                }
            });
        }
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER);
        card.setBackgroundColor(getResources().getColor(R.color.white));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) height / 4, (int) (height / 3.5));
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
        img.setLayoutParams(new LinearLayout.LayoutParams(height / 5, height / 5));
        card.addView(img);
        TextView tv = new TextView(this);
        tv.setText("Добавить карточку");
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        card.addView(tv);
        box.addView(card);
    }

    private void addCardToFinal(View card, Category category) {
        card.setOnTouchListener(null);
        card.setX(0);
        card.setY(0);
        if (category.getName().equals("Я")){
            finalBox.addView(card, 0);
        } else if(category.getName().equals("Хочу")){
            int index = finalBox.getChildCount() == 0 ? 0 : 1;
            finalBox.addView(card, index);
        } else {
            finalBox.addView(card, finalBox.getChildCount());
            MediaPlayer mediaPlayer;
            if (category.getSoundPath() == null) {
                mediaPlayer = MediaPlayer.create(ChillActivity.this, category.getSound());
                finalSound = category.getSound();
            } else {
                mediaPlayer = MediaPlayer.create(ChillActivity.this, Uri.parse(category.getSoundPath()));
                strFinalSound = category.getSoundPath();
            }
            mediaPlayer.start();
            finalBox.removeAllViews();
        }
    }

    private void initFinal(Category category){
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER);
        card.setBackgroundColor(getResources().getColor(R.color.white));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) height / 4, (int) (height / 3.5));
        params.setMarginEnd(10);
        card.setLayoutParams(params);
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
        img.setLayoutParams(new LinearLayout.LayoutParams(height / 5, height / 5));
        card.addView(img);
        TextView tv = new TextView(this);
        tv.setText(category.getName());
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        card.addView(tv);
        addCardToFinal(card, category);
    }

//    public void finalSounds(){
//        MediaPlayer p1 = MediaPlayer.create(this, R.raw.i);
//        MediaPlayer p2 = MediaPlayer.create(this, R.raw.want);
//        MediaPlayer p3;
//        if (strFinalSound != null){
//            p3 = MediaPlayer.create(this, Uri.parse(strFinalSound));
//        } else p3 = MediaPlayer.create(this, finalSound);
//        try{
//            p1.prepare();
//            p2.prepare();
//            p3.prepare();
//            p1.start();
//            p1.setNextMediaPlayer(p2);
//            p2.setNextMediaPlayer(p3);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}