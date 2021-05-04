package com.jeffethdonaldson.cs2410_final_project.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
<<<<<<< HEAD
public class Profile {


=======
public class Profile implements Serializable {
>>>>>>> b97b20c0af8979607b26a5c5ad2b1e882d35e141
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
