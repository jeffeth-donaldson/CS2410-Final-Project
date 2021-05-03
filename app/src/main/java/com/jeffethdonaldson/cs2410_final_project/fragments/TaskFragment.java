package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeffethdonaldson.cs2410_final_project.R;

public class TaskFragment extends Fragment {


    public TaskFragment() {
        // Required empty public constructor
        super(R.layout.fragment_task);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        view.findViewById(R.id.fab_task).setOnClickListener((button) -> {
//
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container_view, AddTaskFragment.class, null)
//                    .setReorderingAllowed(true)
//                    .addToBackStack(null)
//                    .commit();
//        });
    }
}