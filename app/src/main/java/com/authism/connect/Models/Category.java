package com.authism.connect.Models;

import java.io.Serializable;

public class Category implements Serializable{
    private String name;
    private int sound, picture;
    private String soundPath, picturePath;
    private String parentName = "null";

    //для ресурсов
    public Category(String name, int sound, int picture) {
        this.name = name;
        this.sound = sound;
        this.picture = picture;
    }
    //для кастомных карточек
    public Category(String name, String soundPath, String picturePath, String parentName) {
        this.name = name;
        this.soundPath = soundPath;
        this.picturePath = picturePath;
        this.parentName = parentName;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
