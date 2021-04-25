package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jeffethdonaldson.cs2410_final_project.R;

public class RoomFragment extends Fragment {
    public RoomFragment() {
        // Required empty public constructor
        super(R.layout.fragment_room);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.fab_room).setOnClickListener((button) -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, AddRoomFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
