package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.TaskViewModel;

public class AddTaskFragment extends Fragment {

    public AddTaskFragment() {
        super(R.layout.fragment_add_task);}
        TaskViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);
        TextInputEditText titleInput = view.findViewById(R.id.task_title_input);
        TextInputEditText descriptionInput  = view.findViewById(R.id.task_description_input);
        TextInputEditText frequencyInput = view.findViewById(R.id.task_frequency_input);
        TextInputEditText userInput = view.findViewById(R.id.task_user_input);
        TextInputEditText roomInput = view.findViewById(R.id.task_room_input);

        view.findViewById(R.id.task_save_button).setOnClickListener(button ->{
            viewModel.saveTask(titleInput.getText().toString(), descriptionInput.getText().toString(), Integer.parseInt(frequencyInput.getText().toString()), userInput.getText().toString(), roomInput.getText().toString() );
        });
    }
}