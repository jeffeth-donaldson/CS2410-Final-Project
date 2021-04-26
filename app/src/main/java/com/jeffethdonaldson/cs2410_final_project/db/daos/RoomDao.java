package com.jeffethdonaldson.cs2410_final_project.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jeffethdonaldson.cs2410_final_project.models.HouseRoom;

import java.util.List;

@Dao
public interface RoomDao {

    @Query("SELECT * FROM HouseRoom")
    List<HouseRoom> getAll();

    @Query("SELECT * FROM HouseRoom WHERE id = :id LIMIT 1")
    HouseRoom findById(long id);

    @Insert
    long insert(HouseRoom houseRoom);

    @Update
    void update(HouseRoom houseRoom);

    @Delete
    void delete(HouseRoom houseRoom);
}
