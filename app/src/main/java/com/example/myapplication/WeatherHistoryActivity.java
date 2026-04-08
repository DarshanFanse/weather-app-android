package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WeatherHistoryActivity extends AppCompatActivity {

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_history);

        TextView tv = findViewById(R.id.historyText);
        WeatherDBHelper db = new WeatherDBHelper(this);

        Cursor c = db.getData();
        StringBuilder data = new StringBuilder();

        while (c.moveToNext()) {
            data.append(c.getString(0)).append(" ")
                    .append(c.getInt(1)).append("°C\n");
        }

        tv.setText(data.toString());
    }
}