package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckInActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    TextView nights,total, mainTV;
    SeekBar seekBar;
    Spinner spinner;
    int totalInt, price=0;
    RadioButton withRS, withoutRS;
    Button buttonCount;
    String roomNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        Bundle b = getIntent().getExtras();
        String priceString = "";
        if(b != null) {
            priceString = b.getString("price2");
            roomNo = b.getString("key2");
        }
        price= Integer.parseInt(priceString);

        initialize();

        mainTV.setText(roomNo);
        withoutRS.setChecked(true);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nights.setText(progress);
                totalInt = price * progress;
                total.setText(totalInt);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);
    }

    private void initialize() {
        nights = findViewById(R.id.textViewNights);
        total = findViewById(R.id.textViewTotal);
        seekBar = findViewById(R.id.seekBar);
        buttonCount = findViewById(R.id.buttonCont);
        mainTV = findViewById(R.id.textViewMain);
        withRS = findViewById(R.id.radioButtonWithRoomService);
        withoutRS = findViewById(R.id.radioButtonWithoutRoomService);
        spinner = findViewById(R.id.spinner);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        switch(text){
         //here we could decide what to do with the spinner choice
            default: { break;}
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void withRoomServiceFunc(View view) {
        withoutRS.setChecked(false);
        totalInt = (int) (totalInt * 1.25);
        total.setText(totalInt);
    }

    public void withoutRoomServiceFunc(View view) {
        withRS.setChecked(false);
        totalInt = (Integer.parseInt(nights.getText().toString())) * price;
        total.setText(totalInt);
    }

    public void continueFunc(View view) {
        String number = roomNo;

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            //Starting Write and Read data with URL
            //Creating array for parameters
            String[] field = new String[1];
            field[0] = "number";
            //Creating array for data
            String[] data = new String[1];
            data[0] = number;

            PutData putData = new PutData("http://192.168.1.253/hotel/book.php", "POST", field, data);
            if(putData.startPut()) {
                if(putData.onComplete()) {
                    String result = putData.getResult();
                    if(result.equals("Booking Success")){
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

                        Bundle b1 = new Bundle();
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
                        b1.putString("roomNumber", roomNo); //Your id
                        b1.putString("numberOfNights", nights.getText().toString().trim()); //Your id
                        b1.putString("date", currentDate); //Your id
                        b1.putString("Total", total.getText().toString().trim()); //Your id
                        Intent intent = new Intent(CheckInActivity.this , CheckInResultActivity.class);
                        intent.putExtras(b1); //Put your id to your next Intent
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    }

                }
            }
            //End Write and Read data with URL
        });
    }

}