package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class CurrentReservationActivity extends AppCompatActivity {

    TextView room, nights, date, total, checkoutALL;
    Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_reservation);

        initialize();

        room.setText(HomeActivity.currentRoomNumber);
        nights.setText(HomeActivity.nights);
        date.setText(HomeActivity.date);
        total.setText(HomeActivity.total);

        if(room.getText().toString().isEmpty()) {
            checkout.setEnabled(false);
        }
    }

    private void initialize() {
        room = findViewById(R.id.ResRoomNumber);
        nights = findViewById(R.id.ResNights);
        date = findViewById(R.id.ResCheckInDate);
        total = findViewById(R.id.ResTotalPayment);
        checkoutALL = findViewById(R.id.checkOutAll);
        checkout = findViewById(R.id.buttonCheckOut);
    }

    public void checkOutAllFunc(View view) {
        String number = room.getText().toString();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            //Starting Write and Read data with URL
            //Creating array for parameters
            String[] field = new String[1];
            field[0] = "number";
            //Creating array for data
            String[] data = new String[1];
            data[0] = number;
            PutData putData = new PutData("http://192.168.1.14/hotel/checkoutall.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    if(result.equals("checkout Success")){
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CurrentReservationActivity.this , HomeActivity.class);
                        startActivity(intent);
                        finish();
                        HomeActivity.clearReservation();
                    }
                    else{
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    }

                }
            }
            //End Write and Read data with URL
        });
    }

    public void checkOut(View view) {
        String number= room.getText().toString();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            //Starting Write and Read data with URL
            //Creating array for parameters
            String[] field = new String[1];
            field[0] = "number";
            //Creating array for data
            String[] data = new String[1];
            data[0] = number;
            PutData putData = new PutData("http://192.168.1.14/hotel/checkout.php", "POST", field, data);
            if(putData.startPut()) {
                if(putData.onComplete()) {
                    String result = putData.getResult();
                    if(result.equals("checkout Success")){
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CurrentReservationActivity.this , HomeActivity.class);
                        startActivity(intent);
                        finish();
                        HomeActivity.clearReservation();
                    } else {
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    }

                }
            }
            //End Write and Read data with URL
        });
    }

}