package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.models.Task;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.CalendarViewModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public class CalendarFragment extends Fragment {
    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    CalendarViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(CalendarViewModel.class);
        ObservableArrayList<Task> tasks = viewModel.getTasks();
        tasks.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Task>>() {
            @Override
            public void onChanged(ObservableList<Task> sender) {
                getActivity().runOnUiThread(() ->{
                    //adapter.notifyDataSetChanged();
                    makeCalendar(tasks);
                });
            }

            @Override
            public void onItemRangeChanged(ObservableList<Task> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<Task> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeMoved(ObservableList<Task> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Task> sender, int positionStart, int itemCount) {

            }
        });
    }

    /**
     * Generates Schedule to fulfill all tasks for one month (30 days)
     * @param tasks
     * @return Arraylist<Task> of all tasks that need to be updated.
     */
    private ArrayList<Task> makeCalendar(ArrayList<Task> tasks){
        ArrayList<Task> toUpdate = new ArrayList<>();
        int tasksPerDay = calculateTasksPerDay(tasks);
        PriorityQueue<Task> toSchedule = new PriorityQueue<>();
        Date currentDay = new Date();
        roundToDay(currentDay);
        for (Task task : tasks) {
            for (Date scheduledDate : task.daysScheduled) {
                if (scheduledDate.before(currentDay)){
                    task.daysScheduled.remove(scheduledDate);
                    if (!toUpdate.contains(task)){
                        toUpdate.add(task);
                    }
                }
            }
        }
        for (int i = 0; i < 30; i++) {
            int scheduledTasks = 0;
            for (Task task : tasks) {
                if (task.daysScheduled.contains(currentDay)){
                    scheduledTasks++;
                }
                if (currentDay.after(getNextDayToSchedule(task)) && !toSchedule.contains(task)){
                    toSchedule.add(task);
                }
            }
            while (scheduledTasks < tasksPerDay && !toSchedule.isEmpty()){
                Task task = toSchedule.poll();
                task.lastAdded = currentDay;
                task.daysScheduled.add(currentDay);
                if(!toUpdate.contains(task)) {
                    toUpdate.add(task);
                }
            }
        }
        return toUpdate;
    }

    /**
     * Calculates the amount of task slots needed to be done,
     * per day to meet all task frequency requirements. Rounds up.
     * @param tasks Arraylist of all tasks
     * @return tasks per day
     */
    private int calculateTasksPerDay(ArrayList<Task> tasks){
        int totalTasks=0;
        int tasksPerDay;

        for (Task task : tasks) {
            totalTasks += task.frequency;
        }

        tasksPerDay = totalTasks / 30;
        if (totalTasks % 30 > 0){
            tasksPerDay ++;
        }

        return tasksPerDay;
    }

    private void roundToDay(Date date){
        final long LENGTH_DAY = 86400000;
        long rounded = date.getTime() - (date.getTime() % LENGTH_DAY);
        date.setTime(rounded);
    }

    private Date getNextDayToSchedule(Task task){
        final long LENGTH_DAY = 86400000;
        // -1 below because we will use Date.after() to determine if we can schedule
        long daysBetweenSchedule = task.frequency * 30-1;
        Date nextDayToSchedule = new Date(task.lastAdded.getTime() + (daysBetweenSchedule * LENGTH_DAY));
        return nextDayToSchedule;
    }
}
