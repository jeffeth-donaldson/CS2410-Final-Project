package com.jeffethdonaldson.cs2410_final_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.db.AppDB;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

public class CalendarViewModel extends AndroidViewModel {
    ObservableArrayList<Task> tasks = new ObservableArrayList<>();
    AppDB db;

    public CalendarViewModel(@NonNull Application application) {
        super(application);
        db = Room.databaseBuilder(application, AppDB.class, application.getString(R.string.db_name)).fallbackToDestructiveMigration().build();
        new Thread(() -> {
            tasks.addAll(db.getTaskDao().getAll());
        }).start();
    }

    public ObservableArrayList<Task> getTasks() {
        return tasks;
    }

    public void updateTask(Task task){
        new Thread(()->{
            db.getTaskDao().update(task);
        });
    }

}
