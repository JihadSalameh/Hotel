package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class CheckInResultActivity extends AppCompatActivity {

    Button finish;
    String  roomNumber, nights, date, total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_result);

        Bundle b = getIntent().getExtras();
        if(b != null){
         roomNumber = b.getString("roomNumber");
         nights = b.getString("numberOfNights");
         date = b.getString("date");
         total = b.getString("Total");
        }
        finish = findViewById(R.id.buttonDone);

        finish.setOnClickListener(v -> {
            Intent intent=new Intent(CheckInResultActivity.this , HomeActivity.class);
            startActivity(intent);
            finishAffinity();
            setData();
        });
    }

    public void setData(){
        HomeActivity.currentRoomNumber= roomNumber;
        HomeActivity.nights= nights;
        HomeActivity.date= date;
        HomeActivity.total= total;
    }
}