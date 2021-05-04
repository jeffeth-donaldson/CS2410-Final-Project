package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

public class TaskFragment extends Fragment {


    public TaskFragment() {
        // Required empty public constructor
        super(R.layout.fragment_task);
    }
    private Task currentTask;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        currentTask = (Task) bundle.get("task");
        setTaskDisplayText();

    }
    public void setTaskDisplayText(){
        TextView taskName = (TextView) getView().findViewById(R.id.task_display_name);
        taskName.setText(currentTask.name);
        TextView taskDescription = (TextView) getView().findViewById(R.id.task_display_description);
        taskDescription.setText(currentTask.description);
        TextView taskUser = (TextView) getView().findViewById(R.id.task_display_user);
        taskUser.setText(currentTask.user);
        TextView taskFreq = (TextView) getView().findViewById(R.id.task_display_frequency);
        taskFreq.setText(currentTask.frequency + "");
        TextView taskRoom = (TextView) getView().findViewById(R.id.task_display_room);
        taskRoom.setText(currentTask.room);
    }
}