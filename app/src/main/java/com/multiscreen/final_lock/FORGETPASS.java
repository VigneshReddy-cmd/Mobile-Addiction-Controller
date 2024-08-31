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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FORGETPASS extends AppCompatActivity {
   FirebaseAuth mAuth;
   private String email;
   FirebaseUser user;
   ProgressBar progressBar;
    MaterialButton verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgetpass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         verify = findViewById(R.id.verify_btn);
        EditText emailID=findViewById(R.id.forget_email);
        progressBar=findViewById(R.id.progressBar);
        MaterialButton back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FORGETPASS.this,LOGIN.class);
                startActivity(intent);
                finish();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailID.getText().toString();

                if(Validity.isValidEmail(email))
                {

                    resetPassword();
                }
                else {
                    Toast.makeText(FORGETPASS.this, "AUTHentication failed", Toast.LENGTH_SHORT).show();
                    emailID.setError("Enter Valid Email");
                }
            }
        });

    }
    private void resetPassword()
    {
        progressBar.setVisibility(View.VISIBLE);
        verify.setVisibility(View.INVISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(FORGETPASS.this, "reset password link send to email", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(FORGETPASS.this,LOGIN.class);
                startActivity(intent);
                finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FORGETPASS.this, "Error", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        verify.setVisibility(View.VISIBLE);
                    }
                });
    }
}
