package com.authism.connect.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    //данные о бд
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Cards";

    public static final String TABLE_CATEGORIES = "Categories";
    public static final String KEY_ID = "_id";
    public static final String CATEGORY_NAME = "name";
    public static final String SOUND_PATH = "soundPath";
    public static final String PICTURE_PATH = "picturePath";
    public static final String CATEGORY_PARENT = "parentName";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //создание таблицы в бд
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_CATEGORIES + " ( " + KEY_ID + " integer, " +
                CATEGORY_NAME + " text, " + SOUND_PATH + " text, " +
                PICTURE_PATH + " text, " + CATEGORY_PARENT + " text, " + " primary key ( " + KEY_ID + "))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //добавление новой карточки в бд
    public void createCategory(Category category){
        SQLiteDatabase database = getWritableDatabase();
        String name = category.getName();
        String sound = category.getSoundPath();
        String picture = category.getPicturePath();
        String parentName = category.getParentName();
        String sql = "insert into " + TABLE_CATEGORIES + " ( " + CATEGORY_NAME +
                ", " + SOUND_PATH + ", " + PICTURE_PATH + ", " + CATEGORY_PARENT + ") values ( '" +
                name + "', '" + sound + "', '" + picture + "', '" + parentName + "' )";
        database.execSQL(sql);
    }

    //взять все карточки по родителю
    public ArrayList<Category> getAllChildCategories(String parent){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "select * from " + TABLE_CATEGORIES + " where " + CATEGORY_PARENT + " = '" + parent + "' ";
        Cursor cursor = database.rawQuery(sql, null);
        ArrayList<Category> categories = new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
            String sound = cursor.getString(cursor.getColumnIndex(SOUND_PATH));
            String picture = cursor.getString(cursor.getColumnIndex(PICTURE_PATH));
            String parentCategory = cursor.getString(cursor.getColumnIndex(CATEGORY_PARENT));
            categories.add(new Category(name, sound, picture, parent));
        }
        cursor.close();
        return categories;
    }
}
