package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.fragments.adapters.HouseRoomAdapter;
import com.jeffethdonaldson.cs2410_final_project.models.HouseRoom;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.RoomViewModel;

import java.io.Serializable;

public class HouseFragment extends Fragment {
    public HouseFragment() {
        // Required empty public constructor
        super(R.layout.fragment_house);
    }

    RoomViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(RoomViewModel.class);

        HouseRoomAdapter adapter = new HouseRoomAdapter(
                viewModel.getHouseRooms(),
                (room) -> {
                    viewModel.setCurrentRoom(room);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, AddRoomFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                },
                (room) ->{
                    viewModel.delete(room);
                },
                (room) ->{
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("houseRoom", room);
                    System.out.println(room);
                    viewModel.setCurrentRoom(room);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, RoomFragment.class, bundle)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
        );

        viewModel.getHouseRooms().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<HouseRoom>>() {
            @Override
            public void onChanged(ObservableList<HouseRoom> sender) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeChanged(ObservableList<HouseRoom> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeInserted(ObservableList<HouseRoom> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeMoved(ObservableList<HouseRoom> sender, int fromPosition, int toPosition, int itemCount) {
                getActivity().runOnUiThread(() ->{
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeRemoved(ObservableList<HouseRoom> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(()->{
                    adapter.notifyDataSetChanged();
                });
            }
        });

        view.findViewById(R.id.fab_house).setOnClickListener((button) -> {
            viewModel.setCurrentRoom(null);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, AddRoomFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

        RecyclerView recyclerView = view.findViewById(R.id.house_fragment_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
