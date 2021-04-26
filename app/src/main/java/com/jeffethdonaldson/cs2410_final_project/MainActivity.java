package com.jeffethdonaldson.cs2410_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.jeffethdonaldson.cs2410_final_project.fragments.CalendarFragment;
import com.jeffethdonaldson.cs2410_final_project.fragments.HouseFragment;
import com.jeffethdonaldson.cs2410_final_project.fragments.ProfilesFragment;
import com.jeffethdonaldson.cs2410_final_project.fragments.RoomFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        MaterialToolbar topBar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        topBar.setNavigationOnClickListener(view -> {
            drawerLayout.open();
        });

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_view, CalendarFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            drawerLayout.close();
            switch (menuItem.getItemId()){
                case R.id.calendar_item:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, CalendarFragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                    break;
                case R.id.rooms_item:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, HouseFragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                    break;
                case R.id.profiles_item:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, ProfilesFragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                    break;
            }
            return true;
        });
    }
}