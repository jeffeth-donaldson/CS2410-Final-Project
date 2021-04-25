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
        super(R.layout.fragment_add_profile);}
    ProfileViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        TextInputEditText titleInput = view.findViewById(R.id.profile_title_input);
        view.findViewById(R.id.profile_save_button).setOnClickListener(button ->{
            viewModel.saveProfile(titleInput.getText().toString());
        });
    }
}