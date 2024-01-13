package com.hackstudio.passengerguide.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackstudio.passengerguide.Models.User;
import com.hackstudio.passengerguide.PayAndPark.PayAndPark;
import com.hackstudio.passengerguide.R;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText etEmail, etPassword, etPhoneNumber;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.et_register_email);
        etPassword = findViewById(R.id.et_register_password);
        etPhoneNumber = findViewById(R.id.et_register_phone);
        btnRegister = findViewById(R.id.btn_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();

                            // Create a User object
                            User user = new User(userId, email, phoneNumber);

                            // Store user data in Firestore
                            db.collection("users")
                                    .document(userId)
                                    .set(user)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        // Navigate to dashboard or login screen
                                        startActivity(new Intent(Register.this, PayAndPark.class));
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(Register.this, "Error storing user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(Register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
