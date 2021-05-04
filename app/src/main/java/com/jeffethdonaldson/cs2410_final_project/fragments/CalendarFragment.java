package com.jeffethdonaldson.cs2410_final_project.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cruxlab.sectionedrecyclerview.lib.SectionDataManager;
import com.cruxlab.sectionedrecyclerview.lib.SectionHeaderLayout;
import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.fragments.adapters.CalendarAdapter;
import com.jeffethdonaldson.cs2410_final_project.models.Profile;
import com.jeffethdonaldson.cs2410_final_project.models.Task;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.CalendarByProfileViewModel;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.CalendarViewModel;
import com.jeffethdonaldson.cs2410_final_project.viewmodels.TaskViewModel;

import java.sql.Array;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.PriorityQueue;

import static android.content.ContentValues.TAG;

public class CalendarFragment extends Fragment {
    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    TaskViewModel viewModel;
    CalendarByProfileViewModel byProfileViewModel;
    private Profile currentProfile;
    ObservableArrayList<Task> tasks;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);
        //ObservableArrayList<Task> tasks = viewModel.getTasks();
        //-----------------------------
        byProfileViewModel = new ViewModelProvider(getActivity()).get(CalendarByProfileViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null){
            currentProfile = (Profile) bundle.get("profile");
            tasks = byProfileViewModel.getTasks(currentProfile);
        }
        else{
            tasks = viewModel.getTasks();
        }
        //--------------------------------

        RecyclerView recyclerView = view.findViewById(R.id.calendar_fragment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SectionDataManager sectionDataManager = new SectionDataManager();
        RecyclerView.Adapter adapter = sectionDataManager.getAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.updateTasks(makeCalendar(tasks));

        ObservableArrayList<Task>[] days = new ObservableArrayList[30];
        //Date currentDay = roundToDay(new Date());
        LocalDate currentDay = LocalDate.now();

        for (int i = 0; i < days.length; i++) {
            days[i] = getDayTasks(tasks, currentDay);
            sectionDataManager.addSection(new CalendarAdapter(days[i], true, false, currentDay), (short)1);
            currentDay = getTomorrow(currentDay);
        }




        tasks.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Task>>() {
            @Override
            public void onChanged(ObservableList<Task> sender) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        makeCalendar(tasks);
                        refreshDays(days, tasks, sectionDataManager);
                    });
                }
            }

            @Override
            public void onItemRangeChanged(ObservableList<Task> sender, int positionStart, int itemCount) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        makeCalendar(tasks);
                        refreshDays(days, tasks, sectionDataManager);
                    });
                }
            }

            @Override
            public void onItemRangeInserted(ObservableList<Task> sender, int positionStart, int itemCount) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        makeCalendar(tasks);
                        refreshDays(days, tasks, sectionDataManager);
                    });
                }
            }

            @Override
            public void onItemRangeMoved(ObservableList<Task> sender, int fromPosition, int toPosition, int itemCount) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        makeCalendar(tasks);
                        refreshDays(days, tasks, sectionDataManager);
                    });
                }
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Task> sender, int positionStart, int itemCount) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        makeCalendar(tasks);
                        refreshDays(days, tasks, sectionDataManager);
                    });
                }
            }
        });
    }

    private void refreshDays(ObservableArrayList<Task>[] days, ObservableArrayList<Task> tasks, SectionDataManager manager){
        LocalDate currentDay = LocalDate.now();
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
    private ArrayList<Task> makeCalendar(ArrayList<Task> tasks) {
        ArrayList<Task> toUpdate = new ArrayList<>();
        int tasksPerDay = calculateTasksPerDay(tasks);
        PriorityQueue<Task> toSchedule = new PriorityQueue<>();
        LocalDate currentDay = LocalDate.now();
        if (tasks != null) {
            for (Task task : tasks) {
                if (task.daysScheduled != null) {
                    for (LocalDate scheduledDate : task.daysScheduled) {
                        if (scheduledDate.isBefore(currentDay)) {
                            task.daysScheduled.remove(scheduledDate);
                            if (!toUpdate.contains(task)) {
                                toUpdate.add(task);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 30; i++) {
            int scheduledTasks = 0;
            for (Task task : tasks) {
                if (task.daysScheduled != null) {
                    if (task.daysScheduled.contains(currentDay)) {
                        scheduledTasks++;
                    }
                    if (currentDay.isAfter(getNextDayToSchedule(task)) && !toSchedule.contains(task)) {
                        toSchedule.add(task);
                    }
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
            currentDay = currentDay.plusDays(1);
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

    private Date roundToDay(Date date){
//        final long LENGTH_DAY = 86400000;
//        long rounded = date.getTime() - (date.getTime() % LENGTH_DAY);
//        date.setTime(rounded);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }

    private LocalDate getNextDayToSchedule(Task task){
        final long LENGTH_DAY = 86400000;
        // -1 below because we will use Date.after() to determine if we can schedule
        long daysBetweenSchedule = 30/task.frequency -1;
        if (task.lastAdded == null){
            return LocalDate.of(0,1,1);
        }
        LocalDate nextDayToSchedule = task.lastAdded.plusDays(daysBetweenSchedule);
        return nextDayToSchedule;
    }

    private LocalDate getTomorrow(LocalDate date){
        LocalDate newDate = date.plusDays(1);
        return newDate;
    }

    private ObservableArrayList<Task> getDayTasks(ArrayList<Task> tasks, LocalDate date) {
         ObservableArrayList<Task> result = new ObservableArrayList<>();
        for (Task task : tasks) {
            if (task.daysScheduled != null && task.daysScheduled.contains(date)) {
                result.add(task);
            }
        }
        return result;
    }


}
