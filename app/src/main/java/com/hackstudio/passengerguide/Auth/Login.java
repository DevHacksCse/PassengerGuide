package com.hackstudio.passengerguide.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hackstudio.passengerguide.PayAndPark.PayAndPark;
import com.hackstudio.passengerguide.R;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        tvRegister = findViewById(R.id.tv_Register);

        mAuth = FirebaseAuth.getInstance();

        // Check if user is already authenticated
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, redirect to another activity (e.g., Dashboard)
            startActivity(new Intent(Login.this, PayAndPark.class));
            finish(); // Finish the current activity
        }

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                            // Navigate to dashboard or home screen
                            startActivity(new Intent(Login.this, PayAndPark.class));
                            finish(); // Finish the current activity
                        } else {
                            Toast.makeText(Login.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        tvForgotPassword.setOnClickListener(v -> {
            // Implement forgot password functionality
        });

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Register.class));
        });
    }
}
