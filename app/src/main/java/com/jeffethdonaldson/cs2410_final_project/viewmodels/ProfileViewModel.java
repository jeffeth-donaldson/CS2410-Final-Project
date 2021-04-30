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
import com.jeffethdonaldson.cs2410_final_project.models.Profile;

import java.io.Serializable;
import java.util.ArrayList;


public class ProfileViewModel extends AndroidViewModel {
    ObservableArrayList<Profile> profiles = new ObservableArrayList<>();
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    AppDB db;
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        db = Room.databaseBuilder(application, AppDB.class, application.getString(R.string.db_name)).build();
        new Thread(() -> {
           profiles.addAll(db.getProfileDao().getAll());
        }).start();
    }

    public ObservableArrayList<Profile> getProfiles(){
        return profiles;
    }

    public MutableLiveData<Boolean> getSaving() {
        return saving;
    }

    public void saveProfile(String title){
        new Thread(()->{
            saving.postValue(true);
            Profile newProfile = new Profile();
            newProfile.name = title;
            newProfile.id = db.getProfileDao().insert(newProfile);
            profiles.add(newProfile);
            saving.postValue(false);
        }).start();
    }

}
