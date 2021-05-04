package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.models.HouseRoom;
import com.jeffethdonaldson.cs2410_final_project.models.Profile;
import com.jeffethdonaldson.cs2410_final_project.models.Task;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.ProfileViewModel;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.TaskByRoomViewModel;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.TaskViewModel;

import java.util.ArrayList;

public class AddTaskFragment extends Fragment{


    private Boolean saving = false;

    public AddTaskFragment() {
        super(R.layout.fragment_add_task);}
    TaskViewModel viewModel;
    TaskByRoomViewModel editViewModel;
    ProfileViewModel profileViewModel;
    Profile assignee;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);
        editViewModel = new ViewModelProvider(getActivity()).get(TaskByRoomViewModel.class);
        profileViewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        TextInputEditText titleInput = view.findViewById(R.id.task_title_input);
        TextInputEditText descriptionInput  = view.findViewById(R.id.task_description_input);
        TextInputEditText frequencyInput = view.findViewById(R.id.task_frequency_input);


        Spinner userSpinner = view.findViewById(R.id.assigned_user_spinner);
        ObservableArrayList<Profile> profiles = profileViewModel.getProfiles();
        ArrayAdapter<Profile> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, profiles);
        profiles.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Profile>>() {
            @Override
            public void onChanged(ObservableList<Profile> sender) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeChanged(ObservableList<Profile> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeInserted(ObservableList<Profile> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeMoved(ObservableList<Profile> sender, int fromPosition, int toPosition, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Profile> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }
        });
        userSpinner.setAdapter(adapter);
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                assignee = (Profile) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Bundle bundle = getArguments();
        HouseRoom houseRoom = (HouseRoom) bundle.get("houseRoom");
        Task task = (Task) bundle.get("task");
        //Profile user = (Profile) bundle.get("user");





        //Problem----------------------------------------------------------

        if(task != null){
            editViewModel.getCurrentTask().observe(getViewLifecycleOwner(), task1 ->{
                TextInputEditText editTaskTitleInput = view.findViewById(R.id.task_title_input);
                editTaskTitleInput.setText(task.name);
                TextInputEditText editTaskDescriptionInput = view.findViewById(R.id.task_description_input);
                editTaskDescriptionInput.setText(task.description);
                TextInputEditText editTaskFreqInput = view.findViewById(R.id.task_frequency_input);
                editTaskFreqInput.setText(task.frequency + "");

//                Spinner editTaskUserSpinner = view.findViewById(R.id.assigned_user_spinner);
//                editTaskUserSpinner.
                }
            );

            editViewModel.getSaving().observe(getViewLifecycleOwner(), saving ->{
                if(this.saving && !saving) {
                    // Save finish, can exit
                    getActivity().runOnUiThread(() -> {
                        getActivity().getSupportFragmentManager().popBackStack();
                    });
                } else if (!this.saving && saving) {
                    // Started save, don't do it twice
                    view.findViewById(R.id.task_save_button).setEnabled(false);
                }
                this.saving = saving;
            });

            TextInputEditText editTaskTitleInput = view.findViewById(R.id.task_title_input);
            TextInputEditText editTaskDescriptionInput = view.findViewById(R.id.task_description_input);
            TextInputEditText editTaskFreqInput = view.findViewById(R.id.task_frequency_input);

            view.findViewById(R.id.task_save_button).setOnClickListener(button ->{
                editViewModel.saveTask(
                        editTaskTitleInput.getText().toString(),
                        editTaskDescriptionInput.getText().toString(),
                        Integer.parseInt(editTaskFreqInput.getText().toString()),
                        "test",
                        houseRoom.name

                );

            });

        }
        else{
            viewModel.getSaving().observe(getViewLifecycleOwner(), saving ->{
                if(this.saving && !saving) {
                    // Save finish, can exit
                    getActivity().runOnUiThread(() -> {
                        getActivity().getSupportFragmentManager().popBackStack();
                    });
                } else if (!this.saving && saving) {
                    // Started save, don't do it twice
                    view.findViewById(R.id.task_save_button).setEnabled(false);
                }
                this.saving = saving;
            });

            view.findViewById(R.id.task_save_button).setOnClickListener(button ->{
                viewModel.saveTask(
                        titleInput.getText().toString(),
                        descriptionInput.getText().toString(),
                        Integer.parseInt(frequencyInput.getText().toString()),
                        assignee.name,
                        houseRoom.name
                );

            });
        }

        //--------------------------------------------------------------------


    }
}
