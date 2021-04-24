package com.jeffethdonaldson.cs2410_final_project.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jeffethdonaldson.cs2410_final_project.models.Room;
import com.jeffethdonaldson.cs2410_final_project.models.Task;
import com.jeffethdonaldson.cs2410_final_project.models.User;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE id = :id LIMIT 1")
    Task findById(long id);

    @Query("SELECT * FROM task WHERE room_name = :roomName")
    List<Task> findByRoom(String roomName);

    @Query("SELECT * FROM task WHERE user_name = :userName")
    List<Task> findByUser(String userName);

    @Insert
    long insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

}
