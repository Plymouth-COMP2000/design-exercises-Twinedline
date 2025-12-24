package com.example.resturantmanagmentapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_profile_menu);

        TextView menuBtn = findViewById(R.id.menu_button);
        Button viewResBtn = findViewById(R.id.guest_view_reservations);

        menuBtn.setOnClickListener(v -> finish());

        viewResBtn.setOnClickListener(v -> {
            SharedPreferences sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
            String currentEmail = sharedPref.getString("current_username", "");

            if (!currentEmail.isEmpty()) {
                fetchReservationForUser(currentEmail);
            } else {
                Toast.makeText(this, "Please log in again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchReservationForUser(String fullEmail) {
        String nameToSearch = fullEmail;
        if (fullEmail.contains("@")) {
            nameToSearch = fullEmail.split("@")[0];
        }

        final String finalSearchName = nameToSearch;

        Api.getAllReservations(this, new Api.ReservationCallback() {
            @Override
            public void onSuccess(List<Reservation> reservations) {
                boolean found = false;
                for (Reservation res : reservations) {
                    if (res.customerName.equalsIgnoreCase(finalSearchName)) {
                        showPopup(res);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Toast.makeText(ProfileActivity.this, "No reservation for " + finalSearchName, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showPopup(Reservation res) {
        View popupView = getLayoutInflater().inflate(R.layout.guest_reservation_popup, null);

        EditText editPeople = popupView.findViewById(R.id.edit_res_people);
        EditText editDate = popupView.findViewById(R.id.edit_res_date);
        EditText editTime = popupView.findViewById(R.id.edit_res_time);
        Spinner locationSpinner = popupView.findViewById(R.id.edit_res_location_spinner);

        editPeople.setText(res.numberOfPeople);
        editDate.setText(res.date);
        editTime.setText(res.time);

        editDate.setFocusable(false);
        editDate.setOnClickListener(v -> {
            java.util.Calendar c = java.util.Calendar.getInstance();
            new android.app.DatePickerDialog(this, (view, year, month, day) -> {
                editDate.setText(String.format("%02d/%02d/%04d", day, month + 1, year));
            }, c.get(java.util.Calendar.YEAR), c.get(java.util.Calendar.MONTH), c.get(java.util.Calendar.DAY_OF_MONTH)).show();
        });

        editTime.setFocusable(false);
        editTime.setOnClickListener(v -> {
            java.util.Calendar c = java.util.Calendar.getInstance();
            new android.app.TimePickerDialog(this, (view, hour, minute) -> {
                editTime.setText(String.format("%02d:%02d", hour, minute));
            }, c.get(java.util.Calendar.HOUR_OF_DAY), c.get(java.util.Calendar.MINUTE), true).show();
        });

        String[] locations = {"Outside: Garden", "Inside: Main Area", "Inside: Bar Stools"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        if (res.location != null) {
            for (int i = 0; i < locations.length; i++) {
                if (locations[i].equals(res.location)) {
                    locationSpinner.setSelection(i);
                    break;
                }
            }
        }

        new AlertDialog.Builder(this)
                .setTitle("Manage Booking for " + res.customerName)
                .setView(popupView)
                .setPositiveButton("Update", (d, which) -> {
                    res.numberOfPeople = editPeople.getText().toString();
                    res.date = editDate.getText().toString();
                    res.time = editTime.getText().toString();
                    res.location = locationSpinner.getSelectedItem().toString();

                    Api.updateReservation(this, res.id, res, new Api.UpdateCallback() {
                        @Override
                        public void onUpdated() {
                            runOnUiThread(() -> Toast.makeText(ProfileActivity.this, "Updated!", Toast.LENGTH_SHORT).show());
                        }
                        @Override
                        public void onError(String error) {
                            Log.e("API_DEBUG", "Error: " + error);
                        }
                    });
                })
                .setNeutralButton("Delete Booking", (d, which) -> {
                    confirmDelete(res);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void confirmDelete(Reservation res) {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Reservation")
                .setMessage("Are you sure you want to delete this booking?")
                .setPositiveButton("Yes, Delete", (d, w) -> {
                    Api.deleteReservation(this, res.id, new Api.DeleteCallback() {
                        @Override
                        public void onDeleted() {
                            runOnUiThread(() -> Toast.makeText(ProfileActivity.this, "Booking Deleted", Toast.LENGTH_SHORT).show());
                        }
                        @Override
                        public void onError(String error) {
                            runOnUiThread(() -> Toast.makeText(ProfileActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show());
                        }
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }
}