package com.jeffethdonaldson.cs2410_final_project.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jeffethdonaldson.cs2410_final_project.models.Profile;

import java.util.List;

@Dao
public interface ProfileDao {
    @Query("SELECT * FROM Profile")
    List<Profile> getAll();

    @Query("SELECT * FROM Profile WHERE id = :id LIMIT 1")
    Profile findById(long id);

    @Insert
    long insert(Profile profile);

    @Update
    void update(Profile profile);

    @Delete
    void delete(Profile profile);
}

