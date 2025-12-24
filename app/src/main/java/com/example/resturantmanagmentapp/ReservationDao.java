package com.example.resturantmanagmentapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ReservationDao {

    @Insert
    void insert(Reservation reservation);

    @Query("SELECT * FROM reservations")
    List<Reservation> getAll();

    @Delete
    void delete(Reservation reservation);
}

