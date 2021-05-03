package com.jeffethdonaldson.cs2410_final_project.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.ObservableArrayList;

import com.cruxlab.sectionedrecyclerview.lib.BaseSectionAdapter;
import com.cruxlab.sectionedrecyclerview.lib.SimpleSectionAdapter;
import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

public class CalendarAdapter extends SimpleSectionAdapter<CalendarAdapter.MyViewHolder> {
    ObservableArrayList<Task> tasks;

    public CalendarAdapter(ObservableArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public MyViewHolder onCreateItemViewHolder(ViewGroup parent, short type) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(MyViewHolder holder, int position) {
        TextView taskName = holder.itemView.findViewById(R.id.task_item_name);
        taskName.setText(tasks.get(position).name);

    }

    public class MyViewHolder extends BaseSectionAdapter.ItemViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
