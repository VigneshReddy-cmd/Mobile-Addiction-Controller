package com.multiscreen.final_lock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;

public class USERLANDING extends AppCompatActivity {
    private final int GALLERY_REQ_CODE = 1000;
    private ShapeableImageView userImage;
    private String fullname;
    private String email;
    private long mobileNumber;
    private String password;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private StorageReference firebaseStorage;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance().getReference();

        // Check if user is authenticated
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if (currentUser == null) {
            // User not logged in, redirect to SIGNUP
            startActivity(new Intent(USERLANDING.this, SIGNUP.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
            return;
        }

        // Check if user is new (first time login)
        DocumentReference userRef = fstore.collection("users").document(currentUser.getUid());
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    // Existing user, redirect to APPLANDING
                    startActivity(new Intent(USERLANDING.this, APPLANDING.class));
                    finish();
                } else {
                    // New user, continue with USERLANDING setup
                    setupUserLanding();
                }
            } else {
                Log.d("USERLANDING", "Failed to get user document", task.getException());
                // Handle error scenario as needed
            }
        });
    }

    // Method to setup USERLANDING view
    private void setupUserLanding() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userlanding);

        // Adjust padding to accommodate system insets (like status bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Sign out button
        Button signout = findViewById(R.id.sign_out);
        signout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(USERLANDING.this, SIGNUP.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        });

        // Upload image from gallery
        ImageButton uploadFromGallery = findViewById(R.id.uploadImage);
        userImage = findViewById(R.id.userImage);
        uploadFromGallery.setOnClickListener(v -> {
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_REQ_CODE);
            uploadFromGallery.setVisibility(View.INVISIBLE);
        });

        // Update button
        Button update = findViewById(R.id.update);
        update.setOnClickListener(v -> {
            takeInput();
            updateData();
            updateImage();

            // Example: Setting display name and description
            TextView displayName = findViewById(R.id.displayName);
            TextView desc = findViewById(R.id.desc);
            displayName.setText(fullname);
            desc.setText(R.string.student);

            // Start APPLANDING activity
            startActivity(new Intent(USERLANDING.this, APPLANDING.class));
            finish(); // Finish USERLANDING activity
        });

        // Next button (example to go to another activity)
        Button next = findViewById(R.id.next);
        next.setOnClickListener(v -> {
            startActivity(new Intent(USERLANDING.this, APPLANDING.class));
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQ_CODE) {
            // Handle gallery image selection
            if (data != null) {
                imageUri = data.getData();
                if (imageUri != null) {
                    userImage.setImageURI(imageUri);
                }
            }
        }
    }

    // Method to fetch user input from EditText fields
    private void takeInput() {
        TextInputEditText fullnameId = findViewById(R.id.Username);
        TextInputEditText emailId = findViewById(R.id.Email);
        TextInputEditText mobileNumberId = findViewById(R.id.Phone);
        TextInputEditText passwordId = findViewById(R.id.password);

        // Fetching input values
        try {
            fullname = Objects.requireNonNull(fullnameId.getText()).toString();
            email = Objects.requireNonNull(emailId.getText()).toString();
            mobileNumber = Long.parseLong(Objects.requireNonNull(mobileNumberId.getText()).toString());
            password = Objects.requireNonNull(passwordId.getText()).toString();
        } catch (Exception e) {
            Log.d("Exception", "Failed to fetch input values");
        }
    }

    // Method to update user data in Firestore
    private void updateData() {
        String userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fstore.collection("users").document(userId);

        HashMap<String, Object> user = new HashMap<>();
        user.put("FullName", fullname);
        user.put("Email", email);
        user.put("MobileNumber", mobileNumber);
        user.put("Password", password);

        // Update user data
        documentReference.set(user)
                .addOnSuccessListener(aVoid -> Log.d("USERDATA Update", "User data updated successfully"))
                .addOnFailureListener(e -> Log.w("USERDATA Update", "Error updating user data", e));
    }

    // Method to upload user image to Firebase Storage
    private void updateImage() {
        String userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        StorageReference fileRef = firebaseStorage.child("profile_images/" + userId + ".jpg");

        if (imageUri != null) {
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> Toast.makeText(USERLANDING.this, "Image Uploaded", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(USERLANDING.this, "Failed to upload image", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(USERLANDING.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
}
