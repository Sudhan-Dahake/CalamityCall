package com.example.calamitycall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        etUsername = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.btnLogin);

        // Set up the login button click listener
        loginButton.setOnClickListener(v -> {
            if (authenticateUser()) {
                // If authenticated, redirect to MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Show error message for failed authentication
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the "Register now!" clickable span
        TextView registerNowText = findViewById(R.id.registerNowText);
        String text = "New User? Register now!";
        SpannableString spannableString = new SpannableString(text);

        // Define the clickable span for "Register now!"
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Start RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        };

        // Apply the clickable span and color to "Register now!"
        int startIndex = text.indexOf("Register now!");
        int endIndex = startIndex + "Register now!".length();
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        registerNowText.setText(spannableString);
        registerNowText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private boolean authenticateUser() {
        // Retrieve input from the EditText fields
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Check if username and password match the specified values
        return username.equals("hang") && password.equals("12345");
    }
}
