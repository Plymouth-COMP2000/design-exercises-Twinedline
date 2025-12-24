package com.example.resturantmanagmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AdminReservationsMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_reservations_menu);

        TextView adminMenuBtn = findViewById(R.id.admin_menu_button);
        adminMenuBtn.setOnClickListener(v -> {
            finish();
        });


        Button btnView = findViewById(R.id.buttonViewReservations);
        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminViewReservationsActivity.class);
            startActivity(intent);
        });


        Button btnCancel = findViewById(R.id.buttonCancelReservation);
        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminViewReservationsActivity.class);
            startActivity(intent);
        });
    }
}