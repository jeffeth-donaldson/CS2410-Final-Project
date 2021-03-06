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

import java.util.ArrayList;

public class RoomViewModel extends AndroidViewModel {
    ObservableArrayList<HouseRoom> houseRooms = new ObservableArrayList<>();
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    MutableLiveData<HouseRoom> currentRoom = new MutableLiveData<>();
    AppDB db;
    public RoomViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        db = Room.databaseBuilder(application, AppDB.class, application.getString(R.string.db_name)).fallbackToDestructiveMigration().build();
        new Thread(() -> {
            houseRooms.addAll(db.getRoomDao().getAll());
        }).start();
    }

    public ObservableArrayList<HouseRoom> getHouseRooms(){
        return houseRooms;
    }
    public MutableLiveData<Boolean> getSaving(){
        return saving;
    }
    public MutableLiveData<HouseRoom> getCurrentRoom(){
        return currentRoom;
    }

    public void setCurrentRoom(HouseRoom currentRoom) {
        this.currentRoom.postValue(currentRoom);
    }

    public void saveRoom(String title){
        saving.setValue(true);
        new Thread(()->{
            if(currentRoom.getValue() != null){
                HouseRoom current = currentRoom.getValue();
                current.name = title;
                db.getRoomDao().update(current);
                int index = houseRooms.indexOf(current);
                houseRooms.set(index, current);
            }
            else{
                HouseRoom newHouseRoom = new HouseRoom();
                newHouseRoom.name = title;
                newHouseRoom.id = db.getRoomDao().insert(newHouseRoom);
                houseRooms.add(newHouseRoom);
            }
            saving.postValue(false);
        }).start();
    }
    public void delete(HouseRoom room){
        new Thread(() -> {
            db.getRoomDao().delete(room);
            houseRooms.remove(room);
        }).start();
    }
}

