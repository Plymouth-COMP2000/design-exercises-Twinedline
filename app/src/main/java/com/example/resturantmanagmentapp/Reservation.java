package com.example.resturantmanagmentapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reservations")
public class Reservation {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String customerName;
    public String date;
    public String time;
    public String numberOfPeople;
    public String location;
    public Reservation() {
    }
}