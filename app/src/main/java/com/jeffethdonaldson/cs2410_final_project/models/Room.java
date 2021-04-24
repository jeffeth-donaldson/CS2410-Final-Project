package com.jeffethdonaldson.cs2410_final_project.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Room {
    @PrimaryKey(autoGenerate = true)
    public long id;
}
