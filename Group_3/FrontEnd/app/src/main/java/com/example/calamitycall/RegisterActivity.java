package com.example.calamitycall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calamitycall.models.signup.SignupRequest;
import com.example.calamitycall.models.signup.SignupResponse;
import com.example.calamitycall.network.auth.SignupService;

public class RegisterActivity extends AppCompatActivity {
    private SignupService signupService;

    private EditText etUsername;
    private EditText etPassword;
    private EditText etAge;
    private EditText etAddress;
    private EditText etPostalCode;
    private EditText etCity;

    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        signupService = new SignupService();

        this.etUsername = findViewById(R.id.editTextUsername);
        this.etPassword = findViewById(R.id.editTextPassword);
        this.etAge = findViewById(R.id.editTextAge);
        this.etAddress = findViewById(R.id.editTextAddress);
        this.etPostalCode = findViewById(R.id.editTextPostalCode);
        this.etCity = findViewById(R.id.editTextCity);
        this.signupButton = findViewById(R.id.btnRegister);

        signupButton.setOnClickListener(v -> handleSignup());

        // Find the "Login now!" TextView
        TextView loginNowText = findViewById(R.id.loginNowText);

        // Set an OnClickListener to go back to LoginActivity
        loginNowText.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void handleSignup() {
        SignupRequest signupRequest = new SignupRequest(
                this.etUsername.getText().toString().trim(),
                this.etPassword.getText().toString().trim(),
                Integer.parseInt(this.etAge.getText().toString().trim()),
                this.etAddress.getText().toString().trim(),
                this.etPostalCode.getText().toString().trim(),
                this.etCity.getText().toString().trim()

        );

        signupService.signup(signupRequest, new SignupService.SignupCallback() {
            @Override
            public void onSuccess(SignupResponse signupResponse) {
                // Handle successful signup, e.g., navigate to another screen or show success message
                Toast.makeText(RegisterActivity.this, "Signup Successful!! Please Login Now", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable throwable) {
                // Handle signup error, e.g., show an error message to the user
                Toast.makeText(RegisterActivity.this, "Signup failed: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
