package com.example.resturantmanagmentapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface MenuItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MenuItem item);

    @Query("SELECT * FROM menu_items")
    List<MenuItem> getAll();

    @Query("DELETE FROM menu_items")
    void deleteAll();

    @Delete
    void delete(MenuItem item);
}