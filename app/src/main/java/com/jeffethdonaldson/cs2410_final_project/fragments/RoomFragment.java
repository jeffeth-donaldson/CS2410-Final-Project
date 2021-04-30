package com.jeffethdonaldson.cs2410_final_project.fragments;

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
import com.jeffethdonaldson.cs2410_final_project.viewmodels.RoomViewModel;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.TaskByRoomViewModel;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.TaskViewModel;

public class RoomFragment extends Fragment {
    public RoomFragment() {
        super(R.layout.fragment_room);
    }
    TaskByRoomViewModel taskViewModel;
    RoomViewModel roomViewModel;
    private HouseRoom currentRoom;
    private ObservableArrayList<Task> tasks;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        currentRoom = (HouseRoom) bundle.get("houseRoom");

        // Get current room, if no current room leave fragment (shouldn't ever happen)
        roomViewModel = new ViewModelProvider(getActivity()).get(RoomViewModel.class);
        roomViewModel.getCurrentRoom().observe(getViewLifecycleOwner(), houseRoom -> {
            if(houseRoom!=null){
                this.currentRoom = houseRoom;
            } else {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });



        taskViewModel = new ViewModelProvider(getActivity()).get(TaskByRoomViewModel.class);
        tasks = taskViewModel.getTasks(currentRoom);

        TaskAdapter adapter = new TaskAdapter(
                tasks,
                (task) -> {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, AddTaskFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                },
                (task)->{
                    //viewModel.delete(task);
                }
        );

        tasks.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Task>>() {
            @Override
            public void onChanged(ObservableList<Task> sender) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeChanged(ObservableList<Task> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyItemRangeChanged(positionStart, itemCount);
                });
            }

            @Override
            public void onItemRangeInserted(ObservableList<Task> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyItemRangeInserted(positionStart, itemCount);
                });
            }

            @Override
            public void onItemRangeMoved(ObservableList<Task> sender, int fromPosition, int toPosition, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    for (int i = 0; i < itemCount; i++) {
                        adapter.notifyItemMoved(fromPosition+i, toPosition+i);
                    }
                });
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Task> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyItemRangeRemoved(positionStart, itemCount);
                });
            }
        });

        view.findViewById(R.id.fab_room).setOnClickListener((button) -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, AddTaskFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

        RecyclerView recyclerView = view.findViewById(R.id.room_fragment_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
