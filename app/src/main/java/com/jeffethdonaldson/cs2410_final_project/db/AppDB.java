package com.jeffethdonaldson.cs2410_final_project.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jeffethdonaldson.cs2410_final_project.db.daos.DayDao;
import com.jeffethdonaldson.cs2410_final_project.db.daos.ProfileDao;
import com.jeffethdonaldson.cs2410_final_project.db.daos.RoomDao;
import com.jeffethdonaldson.cs2410_final_project.db.daos.TaskDao;
import com.jeffethdonaldson.cs2410_final_project.models.Day;
import com.jeffethdonaldson.cs2410_final_project.models.Profile;
import com.jeffethdonaldson.cs2410_final_project.models.Room;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

@Database(entities = {Profile.class, Room.class, Task.class, Day.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDB extends RoomDatabase {
    public abstract ProfileDao getJournalEntryDao();
    public abstract RoomDao getRoomDao();
    public abstract TaskDao getTaskDao();
    public abstract DayDao getDayDao();
}
