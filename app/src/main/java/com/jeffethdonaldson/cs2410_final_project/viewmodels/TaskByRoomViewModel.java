package com.jeffethdonaldson.cs2410_final_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.jeffethdonaldson.cs2410_final_project.R;
import com.jeffethdonaldson.cs2410_final_project.db.AppDB;
import com.jeffethdonaldson.cs2410_final_project.models.HouseRoom;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

public class TaskByRoomViewModel extends AndroidViewModel {
    ObservableArrayList<Task> tasks = new ObservableArrayList<>();
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    MutableLiveData<Task> currentTask = new MutableLiveData<>();
    AppDB db;

    public TaskByRoomViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        db = Room.databaseBuilder(application, AppDB.class,  application.getString(R.string.db_name)).fallbackToDestructiveMigration().build();
    }

    public ObservableArrayList<Task> getTasks(HouseRoom houseRoom){
        tasks.clear();
        System.out.println(houseRoom);
        new Thread(() -> {
            tasks.addAll(db.getTaskDao().findByRoom(houseRoom.name));
        }).start();
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
        new Thread(()->{
            Task newTask = new Task();
            newTask.name = title;
            newTask.description = description;
            newTask.frequency = frequency;
            newTask.user = userName;
            newTask.room = roomName;
            newTask.id = db.getTaskDao().insert(newTask);
            tasks.add(newTask);
        }).start();
    }
}
