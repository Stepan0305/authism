package com.authism.connect.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.authism.connect.Helpers.ArraysKeeper;
import com.authism.connect.Helpers.MyScrollView;
import com.authism.connect.Models.Category;
import com.authism.connect.Models.DbHelper;
import com.authism.connect.R;

import java.io.File;
import java.io.IOException;
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
 */
public class CommunicationActivity extends AppCompatActivity {
    LinearLayout box, finalBox;
    int width, height;
    ImageButton home, settings;
    MyScrollView scrollView;
    ArrayList<Category> categories;
    String currentTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        box = findViewById(R.id.boxCommunication);
        home = findViewById(R.id.btnHomeCommunication);
        settings = findViewById(R.id.btnBackCommunication);
        scrollView = findViewById(R.id.scrollCommunication);
        finalBox = findViewById(R.id.finalBoxCommunication);
        //берем размеры экрана
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        currentTheme = "Общение";
        categories = new ArrayList<>(ArraysKeeper.communicationCards);
        DbHelper dbHelper = new DbHelper(this);
        categories.addAll(dbHelper.getAllChildCategories(currentTheme));
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

    @SuppressLint("ClickableViewAccessibility")
    private void generateCards() {
        //добавление карточек в box
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
            if (!currentTheme.equals("Общение") && (category.getName().equals("Я") || category.getName().equals("Хочу"))) {
                finalBox.addView(card);
            } else box.addView(card);
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
                Intent i = new Intent(CommunicationActivity.this, AddNewCardActivity.class);
                i.putExtra("parent", currentTheme);
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
        MediaPlayer mediaPlayer;
        if (category.getSoundPath() == null) {
            mediaPlayer = MediaPlayer.create(CommunicationActivity.this, category.getSound());
        } else {
            mediaPlayer = MediaPlayer.create(CommunicationActivity.this, Uri.parse(category.getSoundPath()));
        }
        mediaPlayer.start();
        if (category.getName().equals("Я")) {
            finalBox.addView(card, 0);
        } else if (category.getName().equals("Хочу")) {
            int index = finalBox.getChildCount() == 0 ? 0 : 1;
            finalBox.addView(card, index);
        } else {
            finalBox.addView(card, finalBox.getChildCount());
            reDrawActivity(category);

        }
    }

    private void reDrawActivity(Category category) {
        box.removeAllViews();
        finalBox.removeAllViews();
        currentTheme = category.getName();
        switch (currentTheme) {
            case "Есть":
                categories = new ArrayList<Category>(ArraysKeeper.eatCards);
                categories.addAll(new DbHelper(this).getAllChildCategories(currentTheme));
                generateCards();
                break;
            case "Играть":
                categories = new ArrayList<Category>(ArraysKeeper.playCards);
                categories.addAll(new DbHelper(this).getAllChildCategories(currentTheme));
                generateCards();
                break;
            case "Пойти":
                categories = new ArrayList<Category>(ArraysKeeper.goCards);
                categories.addAll(new DbHelper(this).getAllChildCategories(currentTheme));
                generateCards();
                break;
            case "Гигиена":
                categories = new ArrayList<Category>(ArraysKeeper.hygieneCards);
                categories.addAll(new DbHelper(this).getAllChildCategories(currentTheme));
                generateCards();
                break;
            case "Пить":
                categories = new ArrayList<Category>(ArraysKeeper.drinkCards);
                categories.addAll(new DbHelper(this).getAllChildCategories(currentTheme));
                generateCards();
                break;
            case "Рисовать":
                categories = new ArrayList<Category>(ArraysKeeper.drawCards);
                categories.addAll(new DbHelper(this).getAllChildCategories(currentTheme));
                generateCards();
                break;
            case "Отдыхать":
                categories = new ArrayList<Category>(ArraysKeeper.chillCards);
                categories.addAll(new DbHelper(this).getAllChildCategories(currentTheme));
                generateCards();
                break;
            default:
                try {
                    MediaPlayer player = MediaPlayer.create(this, R.raw.congrats);
                    player.getCurrentPosition();
                    player.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                categories = categories = new ArrayList<Category>(ArraysKeeper.communicationCards);
//                categories.addAll(new DbHelper(this).getAllChildCategories(currentTheme));
//                generateCards();
                recreate();
                break;
        }
    }
}