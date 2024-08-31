package com.multiscreen.final_lock;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userId;
    TextView userName;
    TextView email;
    TextView phone;
    ShapeableImageView userImage;
    StorageReference storageReference;
    Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // firebase
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference("profile_images/" + userId + ".jpg");

        // data fields
        userName = view.findViewById(R.id.Username);
        email = view.findViewById(R.id.Email);
        phone = view.findViewById(R.id.Phone);
        userImage = view.findViewById(R.id.userImage);
        logout=view.findViewById(R.id.logOut);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getActivity(),SIGNUP.class);
                startActivity(intent);

            }
        });

        DocumentReference documentReference = fstore.collection("users").document(userId);

        // using snapshot listener for data
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("ProfileFragment", "Listen failed.", error);
                    return;
                }

                if (value != null && value.exists()) {
                    // Retrieve data
                    String fullName = value.getString("FullName");
                    String Email = value.getString("Email");
                    Object mobileNumberObject = value.get("MobileNumber"); // Retrieve as Object first

                    // Handle MobileNumber correctly
                    long mobileNumber = 0; // Initialize with default value
                    if (mobileNumberObject instanceof Long) {
                        mobileNumber = (Long) mobileNumberObject;
                    } else if (mobileNumberObject instanceof Integer) {
                        mobileNumber = ((Integer) mobileNumberObject).longValue();
                    } else {
                        Log.d("ProfileFragment", "MobileNumber is not a valid numeric type.");
                    }

                    // Update UI elements
                    userName.setText(fullName);
                    email.setText(Email);
                    phone.setText(String.valueOf(mobileNumber)); // Convert to String if needed
                } else {
                    Log.d("ProfileFragment", "No such document");
                }
            }
        });

        // for image
        final long ONE_MEGABYTE = 1024 * 1024;
        // max size
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                if (isAdded()) {
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    userImage.setImageBitmap(bm);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ProfileFragment", "Error fetching image", e);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
