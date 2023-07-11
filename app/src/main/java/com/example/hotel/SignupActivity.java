package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    TextView alreadyHaveAccount;
    EditText usernameTxt, emailTxt, passwordTxt;
    Button buttonSignup;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialize();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    private void initialize() {
        alreadyHaveAccount = findViewById(R.id.HaveAccount);
        usernameTxt = findViewById(R.id.signupUsernameTxt);
        emailTxt = findViewById(R.id.signupEmailTxt);
        passwordTxt = findViewById(R.id.signupPasswordTxt);
        buttonSignup = findViewById(R.id.buttonSignUp);
    }

    public void signUp(View view) {
        String username= String.valueOf(usernameTxt.getText());
        String email= String.valueOf(emailTxt.getText());
        String password= String.valueOf(passwordTxt.getText());

        if(username.isEmpty() || email.isEmpty() || password.isEmpty() ){
            Toast.makeText(getApplicationContext(), "Please make sure all fields are not empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        signUpConnection(username, email, password);
    }

    private void signUpConnection(String username, String email, String password) {
        String url = "http://192.168.1.14/hotel/signup.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    if(response.equals("\nSign Up Success")) {
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Try Again Later1", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Try Again Later2", Toast.LENGTH_SHORT).show()) {
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
                params.put("email", email);
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void haveAccount(View view) {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }
}