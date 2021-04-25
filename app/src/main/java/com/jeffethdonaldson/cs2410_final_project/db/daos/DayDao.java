package com.jeffethdonaldson.cs2410_final_project.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jeffethdonaldson.cs2410_final_project.models.Day;
import com.jeffethdonaldson.cs2410_final_project.models.Task;

import java.util.Date;
import java.util.List;

@Dao
public interface DayDao {

    @Query("SELECT * FROM day")
    List<Day> getAll();

    @Query("SELECT * FROM day WHERE id = :id LIMIT 1")
    Day findById(long id);

    @Query("SELECT * FROM day WHERE date = :date")
    List<Day> findByDate(Date date);

    @Insert
    long insert(Day day);

    @Update
    void update(Day day);

    @Delete
    void delete(Day day);

}
