package com.jeffethdonaldson.cs2410_final_project.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.ObservableArrayList;

import com.cruxlab.sectionedrecyclerview.lib.BaseSectionAdapter;
import com.cruxlab.sectionedrecyclerview.lib.SectionAdapter;
import com.cruxlab.sectionedrecyclerview.lib.SimpleSectionAdapter;
import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

import java.util.Date;

public class CalendarAdapter extends SectionAdapter<CalendarAdapter.MyViewHolder, CalendarAdapter.MyHeaderViewHolder> {
    ObservableArrayList<Task> tasks;
    Date headerText;


    public CalendarAdapter(ObservableArrayList<Task> tasks, boolean isHeaderVisible, boolean isHeaderPinned, Date headerText) {
        super(isHeaderVisible, isHeaderPinned);
        this.tasks = tasks;
        this.headerText = headerText;
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

    @Override
    public MyHeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_calendar, parent, false);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(MyHeaderViewHolder holder) {
        TextView headerName = holder.itemView.findViewById(R.id.calendar_header_text);
        headerName.setText(headerText.toString());
    }

    public class MyViewHolder extends BaseSectionAdapter.ItemViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MyHeaderViewHolder extends BaseSectionAdapter.HeaderViewHolder {

        public MyHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
