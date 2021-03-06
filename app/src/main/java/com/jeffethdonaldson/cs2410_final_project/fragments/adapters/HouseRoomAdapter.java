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
import com.jeffethdonaldson.cs2410_final_project.models.HouseRoom;

public class  HouseRoomAdapter extends RecyclerView.Adapter<HouseRoomAdapter.ViewHolder> {
    ObservableArrayList<HouseRoom> rooms;
    OnHouseRoomClicked addUpdateListener;
    OnHouseRoomClicked deleteListener;
    OnHouseRoomClicked viewListener;
    public interface OnHouseRoomClicked{
        void onClick(HouseRoom room);
    }

    public HouseRoomAdapter(ObservableArrayList<HouseRoom> rooms, OnHouseRoomClicked addUpdateListener, OnHouseRoomClicked deleteListener, OnHouseRoomClicked viewListener) {
        this.rooms = rooms;
        this.addUpdateListener = addUpdateListener;
        this.deleteListener = deleteListener;
        this.viewListener = viewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HouseRoom room = rooms.get(position);
        TextView name = holder.itemView.findViewById(R.id.room_item_name);
        name.setText(room.name);

        ImageButton editButton = holder.itemView.findViewById(R.id.room_item_edit_button);
        editButton.setOnClickListener((view) ->{
            if (addUpdateListener == null) return;
            addUpdateListener.onClick(room);
        });
        ImageButton deleteButton = holder.itemView.findViewById(R.id.room_item_delete_button);
        deleteButton.setOnClickListener((view) ->{
            if(deleteListener == null) return;
            deleteListener.onClick(room);
        });

        holder.itemView.setOnClickListener((view) -> {
            if(viewListener == null) return;
            viewListener.onClick(room);
        });
    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}


