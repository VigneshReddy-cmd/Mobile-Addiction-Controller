package com.multiscreen.final_lock;

import android.widget.Spinner;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SelectApp extends AppCompatActivity {

    private ImageView appIconImageView;
    private TextView appLabelTextView;
    private Spinner endAmPmSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_app);

        appIconImageView = findViewById(R.id.appIcon);
        appLabelTextView = findViewById(R.id.appName);
        endAmPmSpinner = findViewById(R.id.endAmPmSpinner);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            AppDetail appDetail = extras.getParcelable("AppDetail");

            // Set app icon and label
            if (appDetail != null) {
                Drawable icon = appDetail.getIcon();
                CharSequence label = appDetail.getLabel();

                if (icon != null) {
                    appIconImageView.setImageDrawable(icon);
                }

                if (label != null) {
                    appLabelTextView.setText(label);
                }
            }
        }

        // Handle AM/PM spinner selection
        endAmPmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAmPm = (String) parent.getItemAtPosition(position);
                // Example: Handle selected AM/PM value (e.g., update UI or perform logic)
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle situation where nothing is selected (if needed)
            }
        });

    }
}
