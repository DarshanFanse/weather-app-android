package com.example.myapplication;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;

public class WeatherDBHelper extends SQLiteOpenHelper {

    public WeatherDBHelper(Context c) {
        super(c, "WeatherDB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE weather(city TEXT, temp INTEGER, cond TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int o, int n) {}

    public void insertData(String city, int temp, String cond) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("city", city);
        cv.put("temp", temp);
        cv.put("cond", cond);
        db.insert("weather", null, cv);
    }

    public Cursor getData() {
        return getReadableDatabase().rawQuery("SELECT * FROM weather", null);
    }
}