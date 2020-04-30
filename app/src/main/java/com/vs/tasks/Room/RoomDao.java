package com.vs.tasks.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomDao {
    @Insert
    void insert(RoomEntity note);

    @Update
    void update(RoomEntity note);

    @Delete
    void delete(RoomEntity note);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Query("SELECT * from notes ORDER BY ID DESC")
    LiveData<List<RoomEntity>> getNotes();
}
