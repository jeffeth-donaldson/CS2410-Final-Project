package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.fragments.adapters.ProfileAdapter;
import com.jeffethdonaldson.cs2410_final_project.models.Profile;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.ProfileViewModel;

public class ProfilesFragment extends Fragment {
    public ProfilesFragment() {
        // Required empty public constructor
        super(R.layout.fragment_profiles);
    }

    ProfileViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        ProfileAdapter adapter = new ProfileAdapter(
                viewModel.getProfiles(),
                (profile) -> {
                    //may cause issue
                    viewModel.setCurrentProfile(profile);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, AddProfileFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                },
                (profile) -> {
                    viewModel.delete(profile);
                },
                (profile -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("profile", profile);
                    viewModel.setCurrentProfile(profile);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, CalendarFragment.class, bundle)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                })
        );

        viewModel.getProfiles().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Profile>>() {
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

        view.findViewById(R.id.fab_profile).setOnClickListener((button) -> {
            viewModel.setCurrentProfile(null);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, AddProfileFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

        RecyclerView recyclerView = view.findViewById(R.id.profile_fragment_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //----------------------
        boolean unassignedProfileExist = false;
        for(int i = 0; i< viewModel.getProfiles().size(); i++){
            if(viewModel.getProfiles().get(i).name.equals("unassigned")){
                unassignedProfileExist = true;
            }
        }
        if(!unassignedProfileExist){
            viewModel.saveProfile("unassigned");
        }

         //------------------------
    }
}
