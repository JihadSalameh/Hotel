package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView dontHaveAccount;
    EditText usernameTxt, passwordTxt;
    Button loginBtn;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    private void initialize() {
        dontHaveAccount = findViewById(R.id.dontHaveAccount);
        usernameTxt = findViewById(R.id.signupEmailTxt);
        passwordTxt = findViewById(R.id.signupPasswordTxt);
        loginBtn = findViewById(R.id.loginBtn);
    }

    public void login(View view) {
        String username= String.valueOf(usernameTxt.getText());
        String password= String.valueOf(passwordTxt.getText());

        if(username.isEmpty()  || password.isEmpty()) {
            Toast.makeText(this, "Please make sure all fields are not empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        loginConnection(username, password);
    }

    private void loginConnection(String username, String password) {
        String url = "http://192.168.1.14/hotel/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    if(response.equals("\nLogin Success")) {

                        SharedPreferences sharedPreferences= getSharedPreferences(MainActivity.SHARED_PREFS,MODE_PRIVATE);
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString(MainActivity.USERNAME,username);
                        editor.apply();

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void createAccount(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        finish();
    }

}