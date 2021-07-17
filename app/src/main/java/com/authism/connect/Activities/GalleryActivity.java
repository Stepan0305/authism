package com.authism.connect.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.authism.connect.Helpers.ArraysKeeper;
import com.authism.connect.Models.Category;
import com.authism.connect.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    CarouselView carousel;
    ArrayList<Category> elements = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        carousel = findViewById(R.id.carousel);
    }
    public void onMenuClick(View v){
        switch (v.getId()){
            case R.id.itemAnimals:
                elements = new ArrayList<>(ArraysKeeper.animalCards);
                break;
            case R.id.itemBirds:
                elements = new ArrayList<>(ArraysKeeper.birdCards);
                break;
            case R.id.itemColors:
                elements = new ArrayList<>(ArraysKeeper.colorCards);
                break;
            case R.id.itemPlants:
                elements = new ArrayList<>(ArraysKeeper.plantCards);
                break;
            case R.id.itemTech:
                elements = new ArrayList<>(ArraysKeeper.techCards);

        }
        ArrayList<Category> finalElements = elements;
        carousel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setImageResource(finalElements.get(position).getPicture());
            }
        });
        carousel.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                MediaPlayer player = MediaPlayer.create(GalleryActivity.this, finalElements.get(position).getSound());
                player.start();
            }
        });
        carousel.setPageCount(elements.size());
    }

}