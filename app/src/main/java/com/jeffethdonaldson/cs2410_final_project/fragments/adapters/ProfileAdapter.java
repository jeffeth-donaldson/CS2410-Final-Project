package com.jeffethdonaldson.cs2410_final_project.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.models.Profile;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{
    ObservableArrayList<Profile> profiles;
    OnProfileClicked addUpdateListener;
    OnProfileClicked deleteListener;
    public interface OnProfileClicked{
        void onClick(Profile profile);
    }

    public ProfileAdapter(ObservableArrayList<Profile> profiles, OnProfileClicked addUpdateListener, OnProfileClicked deleteListener){
        this.profiles = profiles;
        this.addUpdateListener = addUpdateListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile profile = profiles.get(position);
        TextView name = holder.itemView.findViewById(R.id.profile_item_name);

        ImageButton editButton = holder.itemView.findViewById(R.id.profile_item_edit_button);
        editButton.setOnClickListener((view)->{
            if(addUpdateListener == null) return;
            addUpdateListener.onClick(profile);
        });
        ImageButton deleteButton = holder.itemView.findViewById(R.id.profile_item_delete_button);
        deleteButton.setOnClickListener((view) ->{
            if(deleteListener == null) return;
            deleteListener.onClick(profile);
        });
        name.setText(profile.name);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
