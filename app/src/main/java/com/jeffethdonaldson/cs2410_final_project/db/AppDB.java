package com.jeffethdonaldson.cs2410_final_project.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jeffethdonaldson.cs2410_final_project.db.daos.ProfileDao;
import com.jeffethdonaldson.cs2410_final_project.db.daos.RoomDao;
import com.jeffethdonaldson.cs2410_final_project.db.daos.TaskDao;
import com.jeffethdonaldson.cs2410_final_project.models.Profile;
import com.jeffethdonaldson.cs2410_final_project.models.Room;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

@Database(entities = {Profile.class, Room.class, Task.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract ProfileDao getJournalEntryDao();
    public abstract RoomDao getRoomDao();
    public abstract TaskDao getTaskDao();
}
