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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cruxlab.sectionedrecyclerview.lib.SectionDataManager;
import com.cruxlab.sectionedrecyclerview.lib.SectionHeaderLayout;
import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.fragments.adapters.CalendarAdapter;
import com.jeffethdonaldson.cs2410_final_project.models.Task;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.CalendarViewModel;

import java.sql.Array;
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

        RecyclerView recyclerView = view.findViewById(R.id.calendar_fragment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SectionDataManager sectionDataManager = new SectionDataManager();
        RecyclerView.Adapter adapter = sectionDataManager.getAdapter();
        recyclerView.setAdapter(adapter);

        updateTasks(makeCalendar(tasks), viewModel);

        ObservableArrayList<Task>[] days = new ObservableArrayList[30];
        Date currentDay = new Date();
        roundToDay(currentDay);

        for (int i = 0; i < days.length; i++) {
            days[i] = getDayTasks(tasks, currentDay);
            sectionDataManager.addSection(new CalendarAdapter(days[i], true, false, currentDay), (short)1);
            currentDay = getTomorrow(currentDay);
        }



        tasks.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Task>>() {
            @Override
            public void onChanged(ObservableList<Task> sender) {
                getActivity().runOnUiThread(() ->{
                    makeCalendar(tasks);
                    refreshDays(days, tasks, sectionDataManager);
                });
            }

            @Override
            public void onItemRangeChanged(ObservableList<Task> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() ->{
                    makeCalendar(tasks);
                    refreshDays(days, tasks, sectionDataManager);
                });
            }

            @Override
            public void onItemRangeInserted(ObservableList<Task> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() ->{
                    makeCalendar(tasks);
                    refreshDays(days, tasks, sectionDataManager);
                });
            }

            @Override
            public void onItemRangeMoved(ObservableList<Task> sender, int fromPosition, int toPosition, int itemCount) {
                getActivity().runOnUiThread(() ->{
                    makeCalendar(tasks);
                    refreshDays(days, tasks, sectionDataManager);
                });
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Task> sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() ->{
                    makeCalendar(tasks);
                    refreshDays(days, tasks, sectionDataManager);
                });
            }
        });





    }

    private void refreshDays(ObservableArrayList<Task>[] days, ObservableArrayList<Task> tasks, SectionDataManager manager){
        Date currentDay = new Date();
        roundToDay(currentDay);
        for (int i = 0; i < days.length; i++) {
            days[i].clear();
            days[i].addAll(getDayTasks(tasks, currentDay));
            currentDay = getTomorrow(currentDay);
            manager.updateSection(i);
        }
    }

    private void updateTasks(ArrayList<Task> toUpdate, CalendarViewModel viewModel){
        for (Task task : toUpdate) {
            viewModel.updateTask(task);
        }
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
                scheduledTasks++;
                if(!toUpdate.contains(task)) {
                    toUpdate.add(task);
                }
            }
            currentDay = getTomorrow(currentDay);
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
        long daysBetweenSchedule = 30/task.frequency -1;
        if (task.lastAdded == null){
            return new Date(0);
        }
        Date nextDayToSchedule = new Date(task.lastAdded.getTime() + (daysBetweenSchedule * LENGTH_DAY));
        return nextDayToSchedule;
    }

    private Date getTomorrow(Date date){
        final long LENGTH_DAY = 86400000;
        return new Date(date.getTime() + LENGTH_DAY);
    }

    private ObservableArrayList<Task> getDayTasks(ArrayList<Task> tasks, Date date) {
         ObservableArrayList<Task> result = new ObservableArrayList<>();
        for (Task task : tasks) {
            if (task.daysScheduled.contains(date)) {
                result.add(task);
            }
        }
        return result;
    }


}
