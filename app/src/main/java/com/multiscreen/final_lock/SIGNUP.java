package com.multiscreen.final_lock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SIGNUP extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), USERLANDING.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);

        // Adjust padding to accommodate system insets (like status bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);

        MaterialButton prevUser = findViewById(R.id.prev_user);
        prevUser.setOnClickListener(v -> {
            startActivity(new Intent(SIGNUP.this, LOGIN.class));
            finish();
        });

        MaterialButton register = findViewById(R.id.signupbutton);
        register.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            EditText usernameId = findViewById(R.id.username);
            EditText emailId = findViewById(R.id.Email);
            EditText passwordId = findViewById(R.id.password);
            EditText reenterPasswordId = findViewById(R.id.reenterpassword);

            String username = usernameId.getText().toString();
            String email = emailId.getText().toString().trim();
            String password = passwordId.getText().toString();
            String reenterPassword = reenterPasswordId.getText().toString();

            // Validate email format
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(SIGNUP.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Validate password length or complexity as needed
            if (password.length() < 6) {
                Toast.makeText(SIGNUP.this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Check if passwords match
            if (!password.equals(reenterPassword)) {
                Toast.makeText(SIGNUP.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Create user with email and password
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(SIGNUP.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SIGNUP.this, USERLANDING.class));
                            finish();
                        } else {
                            Log.w("", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SIGNUP.this, "Authentication failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });


    }
}
