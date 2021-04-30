package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.fragments.adapters.TaskAdapter;
import com.jeffethdonaldson.cs2410_final_project.models.HouseRoom;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.RoomViewModel;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.TaskViewModel;

public class RoomFragment extends Fragment {
    public RoomFragment() {
        super(R.layout.fragment_room);
    }
    TaskViewModel taskViewModel;
    RoomViewModel roomViewModel;
    private HouseRoom currentRoom;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get current room, if no current room leave fragment (shouldn't ever happen)
        roomViewModel = new ViewModelProvider(getActivity()).get(RoomViewModel.class);
        roomViewModel.getCurrentRoom().observe(getViewLifecycleOwner(), houseRoom -> {
            if(houseRoom!=null){
                this.currentRoom = houseRoom;
            } else {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        taskViewModel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);
        //TaskAdapter adapter = new TaskAdapter(//taskViewModel.get)
        //TODO: Finish this implementation


        view.findViewById(R.id.fab_house).setOnClickListener((button) -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, AddTaskFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
