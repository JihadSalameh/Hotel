package com.example.hotel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class ProfileActivity extends AppCompatActivity {

    TextView usernameTxt, emailTxt;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameTxt = findViewById(R.id.usernameTxt);
        emailTxt = findViewById(R.id.emailTxt);

        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        String username1 = sharedPreferences.getString(MainActivity.USERNAME, "");
        getUserInfoFromServer(username1);
    }

    private void getUserInfoFromServer(String username1) {
        String url = "http://192.168.1.14/hotel/get_user.php?username=" + username1;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String username = response.getJSONObject(0).getString("username");
                        String email = response.getJSONObject(0).getString("email");

                        usernameTxt.setText(username);
                        emailTxt.setText(email);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        requestQueue.add(request);
    }

    public void returnHome(View view) {
        finish();
    }
}
