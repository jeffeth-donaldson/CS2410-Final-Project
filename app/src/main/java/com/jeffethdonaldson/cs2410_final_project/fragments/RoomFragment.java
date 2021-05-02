package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.fragments.adapters.TaskAdapter;
import com.jeffethdonaldson.cs2410_final_project.models.HouseRoom;
import com.jeffethdonaldson.cs2410_final_project.models.Task;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.TaskByRoomViewModel;

public class RoomFragment extends Fragment {
    public RoomFragment() {
        super(R.layout.fragment_room);
    }
    TaskByRoomViewModel viewModel;
    private HouseRoom currentRoom;
    private ObservableArrayList<Task> tasks;
    private Activity activity = getActivity();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        currentRoom = (HouseRoom) bundle.get("houseRoom");

        viewModel = new ViewModelProvider(getActivity()).get(TaskByRoomViewModel.class);
        tasks = viewModel.getTasks(currentRoom);

        TaskAdapter adapter = new TaskAdapter(
                tasks,
                //edit
                (task) -> {
                    Bundle args = new Bundle();
                    args.putSerializable("task", task);
                    args.putSerializable("houseroom", currentRoom);
                  //  args.putSerializable("user", task.user);
                    viewModel.setCurrentTask(task);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, AddTaskFragment.class, args)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                },
                (task)->{
                    viewModel.delete(task);
                },
                //view
                (task) ->{
                    Bundle args = new Bundle();
                    args.putSerializable("task", task);
                    args.putSerializable("houseroom", currentRoom);
                    viewModel.setCurrentTask(task);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, AddTaskFragment.class, args)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }

        );

        tasks.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Task>>() {
            @Override
            public void onChanged(ObservableList<Task> sender) {
                if(getActivity()!=null){
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
                }
            }

            @Override
            public void onItemRangeChanged(ObservableList<Task> sender, int positionStart, int itemCount) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                }
            }

            @Override
            public void onItemRangeInserted(ObservableList<Task> sender, int positionStart, int itemCount) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                }
            }

            @Override
            public void onItemRangeMoved(ObservableList<Task> sender, int fromPosition, int toPosition, int itemCount) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                }
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Task> sender, int positionStart, int itemCount) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                }
            }
        });

        view.findViewById(R.id.fab_room).setOnClickListener((button) -> {
            Bundle args = new Bundle();
            args.putSerializable("houseRoom", currentRoom);
            viewModel.setCurrentTask(null);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, AddTaskFragment.class, args)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

        RecyclerView recyclerView = view.findViewById(R.id.room_fragment_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
