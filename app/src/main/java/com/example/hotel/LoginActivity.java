package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {

    ImageView dontHaveAccount;
    EditText editTextUsername, editTextPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }

    private void initialize() {
        dontHaveAccount = findViewById(R.id.imageViewDontHave);
        editTextUsername = findViewById(R.id.loginUser);
        editTextPassword = findViewById(R.id.loginPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
    }

    public void createAccount(View view) {
        Intent intent=new Intent(LoginActivity .this , SignupActivity.class);
        startActivity(intent);
        finish();
    }

    public void login(View view) {
        String username= String.valueOf(editTextUsername.getText());
        String password= String.valueOf(editTextPassword.getText());

        if(username.isEmpty()  || password.isEmpty()) {
            Toast.makeText(this, "Please make sure all fields are not empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println(username + " ////////////////////////////////////// " + password);

        loginConnection(username, password);
    }

    private void loginConnection(String username, String password) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            //Starting Write and Read data with URL
            //Creating array for parameters
            String[] field = new String[2];
            field[0] = "username";
            field[1] = "password";

            //Creating array for data
            String[] data = new String[2];
            data[0] = username;
            data[1] = password;

            PutData putData = new PutData("http://192.168.1.253/hotel/login.php", "POST", field, data);
            if(putData.startPut()) {
                if(putData.onComplete()) {
                    String result = putData.getResult();
                    if(result.equals("Login Success")) {
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        System.out.println(result);
                    }

                }
            }
        });
    }

}