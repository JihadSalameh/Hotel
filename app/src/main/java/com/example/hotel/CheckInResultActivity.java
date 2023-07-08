package com.example.hotel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckInResultActivity extends AppCompatActivity {

    Button finish, rent, noRent;
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
            openCarRentalDialog();
        });
    }

    private void openCarRentalDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View locationPopUp = getLayoutInflater().inflate(R.layout.popup, null);

        rent = locationPopUp.findViewById(R.id.rent);
        noRent = locationPopUp.findViewById(R.id.cancel);

        alertDialogBuilder.setView(locationPopUp);
        alertDialogBuilder.create();
        alertDialogBuilder.show();

        rent.setOnClickListener(v -> {
            Intent intent=new Intent(CheckInResultActivity.this , CarRentalActivity.class);
            startActivity(intent);
            finishAffinity();
            setData();
        });

        noRent.setOnClickListener(v -> {
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