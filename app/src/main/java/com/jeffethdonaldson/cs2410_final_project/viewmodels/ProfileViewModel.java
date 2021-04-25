package com.jeffethdonaldson.cs2410_final_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.room.RoomDatabase;

import com.jeffethdonaldson.cs2410_final_project.db.AppDB;
import com.jeffethdonaldson.cs2410_final_project.models.Profile;

import java.util.ArrayList;


public class ProfileViewModel extends AndroidViewModel {
    ArrayList<Profile> profiles = new ArrayList<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public ArrayList<Profile> getProfiles(){
        return profiles;
    }
    public void saveProfile(String title){
        new Thread(()->{
            Profile newProfile = new Profile();
            newProfile.name = title;
            //newProfile.id = db.getProfileDao().insert(newProfile);
            profiles.add(newProfile);
        }).start();
    }
}
