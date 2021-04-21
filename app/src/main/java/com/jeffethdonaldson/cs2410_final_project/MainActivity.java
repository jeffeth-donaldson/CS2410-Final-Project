package com.jeffethdonaldson.cs2410_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jeffethdonaldson.cs2410_final_project.fragments.TaskFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_view, TaskFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }
    }
}