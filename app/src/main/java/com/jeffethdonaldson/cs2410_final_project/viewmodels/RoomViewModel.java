package com.jeffethdonaldson.cs2410_final_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.jeffethdonaldson.cs2410_final_project.db.AppDB;
import com.jeffethdonaldson.cs2410_final_project.models.HouseRoom;

import java.util.ArrayList;

public class RoomViewModel extends AndroidViewModel {
    ObservableArrayList<HouseRoom> houseRooms = new ObservableArrayList<>();
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    AppDB db;
    public RoomViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        db = Room.databaseBuilder(application, AppDB.class, "houseroom-db").build();
        new Thread(() -> {
            houseRooms.addAll(db.getRoomDao().getAll());
        });
    }

    public ArrayList<HouseRoom> getHouseRooms(){
        return houseRooms;
    }
    public void saveRoom(String title){
        new Thread(()->{
            HouseRoom newHouseRoom = new HouseRoom();
            newHouseRoom.name = title;
            newHouseRoom.id = db.getRoomDao().insert(newHouseRoom);
            houseRooms.add(newHouseRoom);
        }).start();
    }
}

