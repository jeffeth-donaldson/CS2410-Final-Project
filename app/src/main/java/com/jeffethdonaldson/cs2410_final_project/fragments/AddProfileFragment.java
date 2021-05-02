package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.ProfileViewModel;

public class AddProfileFragment extends Fragment {
    public AddProfileFragment() {
        super(R.layout.fragment_add_profile);
    }
    ProfileViewModel viewModel;
    private boolean saving = false;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);

        viewModel.getCurrentProfile().observe(getViewLifecycleOwner(), profile -> {
            if(profile != null){
                TextInputEditText titleInput = view.findViewById(R.id.profile_title_input);
                titleInput.setText(profile.name);
            }
        });

        viewModel.getSaving().observe(getViewLifecycleOwner(), saving ->{
            if(this.saving && !saving) {
                // Save finish, can exit
                getActivity().runOnUiThread(() -> {
                    getActivity().getSupportFragmentManager().popBackStack();
                });
            } else if (!this.saving && saving) {
                // Started save, don't do it twice
                view.findViewById(R.id.profile_save_button).setEnabled(false);
            }
            this.saving = saving;
        });


        TextInputEditText titleInput = view.findViewById(R.id.profile_title_input);
        view.findViewById(R.id.profile_save_button).setOnClickListener(button ->{
            viewModel.saveProfile(titleInput.getText().toString());
        });
    }
}