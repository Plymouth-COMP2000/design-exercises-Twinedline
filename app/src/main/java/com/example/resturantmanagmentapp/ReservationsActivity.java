package com.example.resturantmanagmentapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context; // Added
import android.content.SharedPreferences; // Added
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class ReservationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_book_reservation);

        Spinner spinnerLocation = findViewById(R.id.spinnerCategory);
        String[] locations = {"Outside: Garden", "Inside: Main Area", "Inside: Bar Stools"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);

        TextView btnBackToMenu = findViewById(R.id.menu_button);
        EditText editName = findViewById(R.id.edit_text_name);
        EditText editPeople = findViewById(R.id.edit_text_people);
        EditText editDate = findViewById(R.id.edit_text_date);
        EditText editTime = findViewById(R.id.edit_text_time);
        Button btnSubmit = findViewById(R.id.btn_submit_reservation);

        SharedPreferences sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String fullEmail = sharedPref.getString("current_username", "");

        if (!fullEmail.isEmpty()) {
            String nameOnly = fullEmail;
            if (fullEmail.contains("@")) {
                nameOnly = fullEmail.split("@")[0];
            }
            editName.setText(nameOnly);
        }

        editDate.setFocusable(false);
        editDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> {
                String formattedDate = String.format("%04d-%02d-%02d", year, month + 1, day);
                editDate.setText(formattedDate);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();


        });

        editTime.setFocusable(false);
        editTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hour, minute) -> {
                editTime.setText(String.format("%02d:%02d", hour, minute));
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
        });

        btnBackToMenu.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String people = editPeople.getText().toString().trim();
            String date = editDate.getText().toString().trim();
            String time = editTime.getText().toString().trim();
            String location = spinnerLocation.getSelectedItem().toString();

            if (name.isEmpty() || people.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            submitReservation(name, people, date, time, location);
        });
    }

    private void submitReservation(String name, String peopleStr, String date, String time, String location) {
        Reservation newRes = new Reservation();
        newRes.customerName = name;
        newRes.numberOfPeople = peopleStr;
        newRes.date = date;
        newRes.time = time;
        newRes.location = location;

        Api.addReservation(this, newRes, new Api.AddReservationCallback() {
            @Override
            public void onSaved(String responseMessage) {
                new Thread(() -> {
                    AppDatabase.getInstance(ReservationsActivity.this).reservationDao().insert(newRes);
                    runOnUiThread(() -> {
                        Toast.makeText(ReservationsActivity.this, "Reservation Saved!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }).start();
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(ReservationsActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}