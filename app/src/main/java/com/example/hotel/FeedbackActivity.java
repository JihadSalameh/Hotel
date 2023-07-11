package com.example.hotel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private EditText commentsText;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ratingBar=findViewById(R.id.ratingBar);
        commentsText=findViewById(R.id.editTextComments);
        btnSubmit=findViewById(R.id.buttonSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                submitFeedback();
            }
        });
    }
    private void submitFeedback() {
        float rating = ratingBar.getRating();
        String comments = commentsText.getText().toString().trim();

        if (rating == 0) {
            Toast.makeText(this, "Please rate your experience", Toast.LENGTH_SHORT).show();
        } else if (comments.isEmpty()) {
            Toast.makeText(this, "Please provide comments", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("FeedbackPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("rating", rating);
            editor.putString("comments", comments);
            editor.apply();
            Toast.makeText(this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}