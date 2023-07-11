package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView mainImageView, secondImageView;
    TextView mainText, secondText, skip;
    Button startButton;
    int index=1;
    static final String SHARED_PREFS = "sharedPrefs", USERNAME = "username", CAR_NAME = "car_name", CAR_NUMBER = "car_number", ROOM = "room",NIGHTS = "nights",DATE = "date",TOTAL = "total";
    String mainText2="Ticket Booking", mainText3="Enjoy Your Holiday";
    String secondText2="Your room is ready as soon as you verify the booking of your fly ticket. Only few moments and the room is yours";
    String secondText3="There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadSharedPref();
        initialize();
    }

    private void initialize() {
        mainImageView = findViewById(R.id.startMainImageView);
        secondImageView = findViewById(R.id.startIndexImageView);
        mainText = findViewById(R.id.startMainTextView);
        secondText = findViewById(R.id.startSecondTextView);
        skip = findViewById(R.id.startSkipTextView);
        startButton = findViewById(R.id.startButton);
    }

    public void loadSharedPref(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        HomeActivity.currentRoomNumber=sharedPreferences.getString(ROOM,"");
        HomeActivity.nights=sharedPreferences.getString(NIGHTS,"");
        HomeActivity.date=sharedPreferences.getString(DATE,"");
        HomeActivity.total=sharedPreferences.getString(TOTAL,"");
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void start(View view) {
        if(index==1) {
            mainImageView.setImageDrawable(getDrawable(R.drawable.start2));
            secondImageView.setImageDrawable(getDrawable(R.drawable.index2));
            mainText.setText(mainText2);
            secondText.setText(secondText2);
            index++;
        }
        else if(index == 2) {
            mainImageView.setImageDrawable(getDrawable(R.drawable.start3));
            secondImageView.setImageDrawable(getDrawable(R.drawable.index3));
            mainText.setText(mainText3);
            secondText.setText(secondText3);
            startButton.setText("Start");
            index++;
        }
        else if(index == 3) {
            openNextActivity();
        }
    }

    public void skip(View view) {
        openNextActivity();
    }

    public void openNextActivity() {
        Intent intent = new Intent(MainActivity.this , LoginActivity.class);
        startActivity(intent);
        finish();
    }

}