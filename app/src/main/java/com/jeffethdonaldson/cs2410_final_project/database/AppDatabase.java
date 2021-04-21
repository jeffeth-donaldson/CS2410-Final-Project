package com.jeffethdonaldson.cs2410_final_project.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jeffethdonaldson.cs2410_final_project.models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao getTaskDao();
}
