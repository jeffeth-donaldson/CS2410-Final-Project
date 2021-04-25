package com.jeffethdonaldson.cs2410_final_project.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class Day {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name="date")
    public Date date;

    @ColumnInfo(name="tasks")
    public ArrayList<Long> tasks;
}
