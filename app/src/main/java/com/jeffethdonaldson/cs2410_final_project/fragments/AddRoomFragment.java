package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.RoomViewModel;

public class AddRoomFragment extends Fragment {
    public AddRoomFragment() {
        super(R.layout.fragment_add_room);}
    RoomViewModel viewModel;
    private boolean saving = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(RoomViewModel.class);

        viewModel.getCurrentRoom().observe(getViewLifecycleOwner(), houseRoom -> {
            if(houseRoom != null){
                TextInputEditText titleInput = view.findViewById(R.id.room_title_input);
                titleInput.setText(houseRoom.name);
            }
        });


        viewModel.getSaving().observe(getViewLifecycleOwner(), saving -> {
            if(this.saving && !saving) {
                // Save finish, can exit
                getActivity().runOnUiThread(() -> {
                    getActivity().getSupportFragmentManager().popBackStack();
                });
            } else if (!this.saving && saving) {
                // Started save, don't do it twice
                view.findViewById(R.id.room_save_button).setEnabled(false);
            }
            this.saving = saving;
        });
        TextInputEditText titleInput = view.findViewById(R.id.room_title_input);
        view.findViewById(R.id.room_save_button).setOnClickListener(button ->{
            viewModel.saveRoom(titleInput.getText().toString());
        });
    }
}