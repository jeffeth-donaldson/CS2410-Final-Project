package com.jeffethdonaldson.cs2410_final_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.db.AppDB;
import com.jeffethdonaldson.cs2410_final_project.models.Profile;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

public class CalendarByProfileViewModel extends AndroidViewModel {
    ObservableArrayList<Task> tasks = new ObservableArrayList<>();
    AppDB db;

    public CalendarByProfileViewModel(@NonNull Application application) {
        super(application);
        db = Room.databaseBuilder(application, AppDB.class, application.getString(R.string.db_name)).fallbackToDestructiveMigration().build();
    }

    public ObservableArrayList<Task> getTasks(Profile user) {
        new Thread(() -> {
            tasks.addAll(db.getTaskDao().findByUser(user.name));
        }).start();
        return tasks;
    }

    public void updateTask(Task task){
        new Thread(()->{
            db.getTaskDao().update(task);
        });
    }

}
