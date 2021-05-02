package com.jeffethdonaldson.cs2410_final_project.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jeffethdonaldson.cs2410_final_project.db.daos.ProfileDao;
import com.jeffethdonaldson.cs2410_final_project.db.daos.RoomDao;
import com.jeffethdonaldson.cs2410_final_project.db.daos.TaskDao;
import com.jeffethdonaldson.cs2410_final_project.models.Profile;
import com.jeffethdonaldson.cs2410_final_project.models.HouseRoom;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

@Database(entities = {Profile.class, HouseRoom.class, Task.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class AppDB extends RoomDatabase {
    public abstract ProfileDao getProfileDao();
    public abstract RoomDao getRoomDao();
    public abstract TaskDao getTaskDao();
}
