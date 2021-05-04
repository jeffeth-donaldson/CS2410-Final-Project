package com.jeffethdonaldson.cs2410_final_project.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Profile {


    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

//    @ColumnInfo(name = "color")
//    public int color;

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
