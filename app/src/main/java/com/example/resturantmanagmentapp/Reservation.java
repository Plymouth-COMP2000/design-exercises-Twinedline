package com.example.resturantmanagmentapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "reservations")
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    public int localId;

    @SerializedName("ReservationID")
    public int id;

    @SerializedName("customer_name")
    public String customerName;

    @SerializedName("number_of_people")
    public String numberOfPeople;

    @SerializedName("reservation_date")
    public String date;

    @SerializedName("reservation_time")
    public String time;

    @SerializedName("location")
    public String location;

    public Reservation() {}
}