package com.example.hotel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class ProfileActivity extends AppCompatActivity {

    TextView usernameTxt, emailTxt;
    RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameTxt = findViewById(R.id.usernameTxt);
        emailTxt = findViewById(R.id.emailTxt);

        requestQueue = Volley.newRequestQueue(this);
        getUserInfoFromServer(usernameTxt.getText().toString());
    }

    private void getUserInfoFromServer(String username1) {

        String url = "http://192.168.1.14/hotel/get_user.php?username=" + username1;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        System.out.println("//////////////////////////////////////");
                        String username = response.getString("username");
                        String email = response.getString("email");

                        usernameTxt.setText(username);
                        emailTxt.setText(email);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        requestQueue.add(request);
    }
}
