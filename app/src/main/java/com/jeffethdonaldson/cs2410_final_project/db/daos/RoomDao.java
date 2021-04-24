package com.jeffethdonaldson.cs2410_final_project.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jeffethdonaldson.cs2410_final_project.models.Room;

import java.util.List;

@Dao
public interface RoomDao {

    @Query("SELECT * FROM room")
    List<Room> getAll();

    @Query("SELECT * FROM room WHERE id = :id LIMIT 1")
    Room findById(long id);

    @Insert
    long insert(Room room);

    @Update
    void update(Room room);

    @Delete
    void delete(Room room);
}
