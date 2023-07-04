package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RoomActivity extends AppCompatActivity {

    TextView tvPrice, tvRoomNumber;
    ImageView imageViewPhoto;
    Button checkIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        initialize();

        Bundle b = getIntent().getExtras();
        String value = "null";
        String price = "null";
        if(b != null) {
            value = b.getString("key");
            price = b.getString("price");
        }

        tvRoomNumber.setText(value);
        tvPrice.setText(price);
        /*Intent intent = getIntent();
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);*/
        imageViewPhoto.setImageDrawable(HomeActivity.imageView.getDrawable());

    }

    private void initialize() {
        tvRoomNumber = findViewById(R.id.textViewRoomNumberMain);
        tvPrice = findViewById(R.id.textViewRoomPrice);
        imageViewPhoto = findViewById(R.id.imageViewRoom);
        checkIn = findViewById(R.id.buttonCheckIn);
    }

    public void checkInFunc(View view) {
        Intent intent = new Intent(RoomActivity.this , CheckInActivity.class);
        Bundle b = new Bundle();
        b.putString("price2", tvPrice.getText().toString().trim()); //Your id
        b.putString("key2", tvRoomNumber.getText().toString().trim()); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
}