package com.multiscreen.final_lock;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class APPLANDING extends AppCompatActivity {
    HomeFragment homeFragment=new HomeFragment();
    NotificationsFragment notificationsFragment=new NotificationsFragment();
    SearchFragment searchFragment=new SearchFragment();
    ProfileFragment profileFragment=new ProfileFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_applanding);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnav);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selected;
                if(R.id.home==menuItem.getItemId())
                {
                    selected=homeFragment;
                }
                else if(R.id.search==menuItem.getItemId())
                {
                    selected=searchFragment;
                }
                else if(R.id.notifications==menuItem.getItemId())
                {
                    selected=notificationsFragment;
                }
                else
                {
                    selected=profileFragment;
                }
                if(selected==null)
                    return false;
                else
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
                    return true;
                }
            }
        });
    }
}