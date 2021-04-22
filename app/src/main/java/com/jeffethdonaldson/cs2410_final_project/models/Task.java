package com.jeffethdonaldson.cs2410_final_project.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "title")
    public String name;

    @ColumnInfo(name = "description")
    public String description;
    //daily, weekly, biweekly, monthly, yearly

    @ColumnInfo(name = "frequency")
    public String frequency;

    @ColumnInfo(name = "user")
    public User user;

}
