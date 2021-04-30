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
import com.jeffethdonaldson.cs2410_final_project.models.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    ObservableArrayList<Task> tasks;
    OnTaskClicked addUpdateListener;
    OnTaskClicked deleteListener;
    public interface OnTaskClicked{
        void onClick(Task task);
    }

    public TaskAdapter(ObservableArrayList<Task> tasks, OnTaskClicked addUpdateListener, OnTaskClicked deleteListener){
        this.tasks = tasks;
        this.addUpdateListener = addUpdateListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        TextView name = holder.itemView.findViewById(R.id.task_item_name);
        name.setText(task.name);

        ImageButton editButton = holder.itemView.findViewById(R.id.task_item_edit_button);
        editButton.setOnClickListener((view) ->{
            addUpdateListener.onClick(task);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
