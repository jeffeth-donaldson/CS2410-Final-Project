package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jeffethdonaldson.cs2410_final_project.R;

public class ProfilesFragment extends Fragment {
    public ProfilesFragment() {
        // Required empty public constructor
        super(R.layout.fragment_profiles);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.fab_profile).setOnClickListener((button) -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, AddProfileFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
