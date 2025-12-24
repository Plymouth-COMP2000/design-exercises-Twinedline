package com.example.resturantmanagmentapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ReservationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_book_reservation);

        Spinner spinnerLocation = findViewById(R.id.spinnerCategory);


        String[] locations = {"Outside: Garden", "Inside: Main Area", "Inside: Bar Stools"};


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, locations);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerLocation.setAdapter(adapter);


        TextView btnBackToMenu = findViewById(R.id.menu_button);
        EditText editPeople = findViewById(R.id.edit_text_people);
        EditText editDate = findViewById(R.id.edit_text_date);
        EditText editTime = findViewById(R.id.edit_text_time);
        EditText editLocation = findViewById(R.id.edit_text_location);
        Button btnSubmit = findViewById(R.id.btn_submit_reservation);


        btnBackToMenu.setOnClickListener(v -> finish());


        editDate.setFocusable(false);
        editDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> {
                editDate.setText(day + "/" + (month + 1) + "/" + year);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });


        editTime.setFocusable(false);
        editTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hour, minute) -> {
                editTime.setText(String.format("%02d:%02d", hour, minute));
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
        });


        btnSubmit.setOnClickListener(v -> {
            saveReservation(
                    editPeople.getText().toString(),
                    editDate.getText().toString(),
                    editTime.getText().toString(),
                    editLocation.getText().toString()
            );
        });
    }

    private void saveReservation(String people, String date, String time, String location) {
        if (people.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Reservation res = new Reservation();
        res.customerName = "Guest User";
        res.numberOfPeople = people;
        res.date = date;
        res.time = time;
        res.location = location;

        new Thread(() -> {
            AppDatabase.getInstance(this).reservationDao().insert(res);
            runOnUiThread(() -> {
                Toast.makeText(this, "Reservation Sent!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}