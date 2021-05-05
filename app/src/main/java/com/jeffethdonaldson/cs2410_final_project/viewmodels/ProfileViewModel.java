package com.jeffethdonaldson.cs2410_final_project.viewmodels;

import android.app.Application;
import android.util.Log;

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
import java.util.List;


public class ProfileViewModel extends AndroidViewModel {
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> parent of 706813b... added color based on freq
    ObservableArrayList<Profile> profiles = new ObservableArrayList<>();
=======
   ObservableArrayList<Profile> profiles = new ObservableArrayList<>();
>>>>>>> b97b20c0af8979607b26a5c5ad2b1e882d35e141
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    MutableLiveData<Profile> currentProfile = new MutableLiveData<>();
    AppDB db;
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        db = Room.databaseBuilder(application, AppDB.class, application.getString(R.string.db_name)).fallbackToDestructiveMigration().build();
        new Thread(() -> {
            List<Profile> profileList = db.getProfileDao().getAll();
            //----------------------
            boolean unassignedProfileExist = false;
            for(int i = 0; i< profileList.size(); i++){
                if(profileList.get(i).name.equals("unassigned")){
                    unassignedProfileExist = true;
                }
            }
            if(!unassignedProfileExist){
                Profile unassigned = new Profile();
                unassigned.name = "unassigned";
                unassigned.id = db.getProfileDao().insert(unassigned);
            }
            //------------------------
           profiles.addAll(db.getProfileDao().getAll());
        }).start();
    }

    public void setCurrentProfile(Profile profile){
        currentProfile.postValue(profile);
    }

    public MutableLiveData <Profile> getCurrentProfile(){
        return currentProfile;
    }

    public ObservableArrayList<Profile> getProfiles(){
        return profiles;
    }

    public MutableLiveData<Boolean> getSaving() {
        return saving;
    }




    public void saveProfile(String title){
        saving.setValue(true);
        new Thread(()->{
            if(currentProfile.getValue()!=null){
                Profile current = currentProfile.getValue();
                current.name = title;
                db.getProfileDao().update(current);
                int index = profiles.indexOf(current);
                profiles.set(index, current);
            }
            else{
                Profile newProfile = new Profile();
                newProfile.name = title;
                if(newProfile.name != "unassigned"){
                    newProfile.id = db.getProfileDao().insert(newProfile);
                    profiles.add(newProfile);

                }
                else {
                    List<Profile> profiles = db.getProfileDao().getAll();
                    //Can't make two of the same profile
                    boolean profileExists = false;
                    for (int i = 0; i < profiles.size(); i++) {
                        if (profiles.get(i).name.equals(newProfile.name)) {
                            profileExists = true;
                        }
                    }
                    if (!profileExists) {
                        newProfile.id = db.getProfileDao().insert(newProfile);
                        profiles.add(newProfile);
                    }
                    //-----------------------------
                }
            }
            saving.postValue(false);
        }).start();
    }

<<<<<<< HEAD


<<<<<<< HEAD
=======
>>>>>>> b97b20c0af8979607b26a5c5ad2b1e882d35e141
=======







>>>>>>> parent of 706813b... added color based on freq
    public void delete(Profile profile){
        new Thread(() -> {
            db.getProfileDao().delete(profile);
            profiles.remove(profile);
        }).start();
    }

}
