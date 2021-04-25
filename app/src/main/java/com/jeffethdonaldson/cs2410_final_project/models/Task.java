package com.jeffethdonaldson.cs2410_final_project.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "title")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    // Integer representing the amount of times to do per month
    @ColumnInfo(name = "frequency")
    public int frequency;

    @ColumnInfo(name = "user_name")
    public String user;

    @ColumnInfo(name = "room_name")
    public String room;

    @ColumnInfo(name = "last_added")
    public Date lastAdded;
}
