package com.jeffethdonaldson.cs2410_final_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.room.RoomDatabase;

import com.jeffethdonaldson.cs2410_final_project.db.AppDB;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

import java.util.ArrayList;

public class TaskViewModel extends AndroidViewModel {
    ArrayList<Task> tasks = new ArrayList<>();

    public TaskViewModel(@NonNull Application application) {
        super(application);
    }

    public ArrayList<Task> getTasks(){
        return tasks;
    }
    public void saveTask(String title, String description, int frequency, String userName, String roomName){
        new Thread(()->{
            Task newTask = new Task();
            newTask.name = title;
            newTask.description = description;
            newTask.frequency = frequency;
            newTask.user = userName;
            newTask.room = roomName;
            //newTask.id = db.getTaskDao().insert(newTask);
            tasks.add(newTask);
        }).start();
    }
}
