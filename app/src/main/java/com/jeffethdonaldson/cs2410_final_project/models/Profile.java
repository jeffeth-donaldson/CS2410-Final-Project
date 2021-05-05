package com.jeffethdonaldson.cs2410_final_project.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
<<<<<<< HEAD
public class Profile {
<<<<<<< HEAD


=======
public class Profile implements Serializable {
>>>>>>> b97b20c0af8979607b26a5c5ad2b1e882d35e141
=======
>>>>>>> parent of 706813b... added color based on freq
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
