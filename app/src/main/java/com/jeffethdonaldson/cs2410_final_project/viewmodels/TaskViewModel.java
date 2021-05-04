package com.jeffethdonaldson.cs2410_final_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.db.AppDB;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

import java.util.ArrayList;

public class TaskViewModel extends AndroidViewModel {
    ObservableArrayList<Task> tasks = new ObservableArrayList<>();
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    MutableLiveData<Task> currentTask = new MutableLiveData<>();
    AppDB db;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        db = Room.databaseBuilder(application, AppDB.class,  application.getString(R.string.db_name)).fallbackToDestructiveMigration().build();
        new Thread(() -> {
            tasks.addAll(db.getTaskDao().getAll());
        });
    }

    public ObservableArrayList<Task> getTasks(){
        return tasks;
    }
    public MutableLiveData<Boolean> getSaving(){
        return saving;
    }
    public MutableLiveData<Task> getCurrentTask(){
        return currentTask;
    }
    public void setCurrentTask(Task currentTask){
        this.currentTask.postValue(currentTask);
    }
    public void saveTask(String title, String description, int frequency, String userName, String roomName){
        saving.postValue(true);
        new Thread(()->{
                Task newTask = new Task();
                newTask.name = title;
                newTask.description = description;
                newTask.frequency = frequency;
                newTask.user = userName;
                newTask.room = roomName;
                newTask.id = db.getTaskDao().insert(newTask);
                tasks.add(newTask);
            saving.postValue(false);
        }).start();
    }
    public void updateTask(Task task){
        saving.postValue(true);
        System.out.println("We Wildin");
        new Thread(()->{
            db.getTaskDao().update(task);
            System.out.println("We updated");
            saving.postValue(false);
            System.out.println("We done");
        }).start();

    }
}
