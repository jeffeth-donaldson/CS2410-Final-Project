package com.jeffethdonaldson.cs2410_final_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.room.RoomDatabase;

import com.jeffethdonaldson.cs2410_final_project.db.AppDB;
import com.jeffethdonaldson.cs2410_final_project.models.Room;

import java.util.ArrayList;

public class RoomViewModel extends AndroidViewModel {
    ArrayList<Room> rooms = new ArrayList<>();

    public RoomViewModel(@NonNull Application application) {
        super(application);
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }
    public void saveRoom(String title){
        new Thread(()->{
            Room newRoom = new Room();
            newRoom.name = title;
            //newRoom.id = db.getRoomDao().insert(newRoom);
            rooms.add(newRoom);
        }).start();
    }
}

