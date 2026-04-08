package com.example.myapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.FileOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText cityInput;
    TextView cityText, tempText, conditionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityInput = findViewById(R.id.cityInput);
        Button btnFetch = findViewById(R.id.btnFetch);
        Button btnMap = findViewById(R.id.btnMap);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnHistory = findViewById(R.id.btnHistory);

        cityText = findViewById(R.id.cityText);
        tempText = findViewById(R.id.tempText);
        conditionText = findViewById(R.id.conditionText);

        WeatherDBHelper db = new WeatherDBHelper(this);

        btnFetch.setOnClickListener(v -> {

            String city = cityInput.getText().toString();
            int temp = new Random().nextInt(15) + 25;
            String cond = "Sunny";

            cityText.setText(city);
            tempText.setText(temp + "°C");
            conditionText.setText(cond);

            db.insertData(city, temp, cond);

            // SAFE notification (no crash)
            if (Build.VERSION.SDK_INT < 33) {
                Intent i = new Intent(this, MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "weather")
                        .setSmallIcon(android.R.drawable.ic_menu_compass)
                        .setContentTitle("Weather Checked")
                        .setContentText(city + " " + temp + "°C")
                        .setContentIntent(pi)
                        .setAutoCancel(true);

                NotificationManagerCompat.from(this).notify(1, builder.build());
            }

        });

        // Implicit Intent
        btnMap.setOnClickListener(v -> {
            String city = cityInput.getText().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + city));
            startActivity(intent);
        });

        // File Handling
        btnSave.setOnClickListener(v -> {
            try {
                String data = cityText.getText() + " " + tempText.getText() + "\n";
                FileOutputStream fos = openFileOutput("weather.txt", MODE_APPEND);
                fos.write(data.getBytes());
                fos.close();
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnHistory.setOnClickListener(v ->
                startActivity(new Intent(this, WeatherHistoryActivity.class)));
    }
}