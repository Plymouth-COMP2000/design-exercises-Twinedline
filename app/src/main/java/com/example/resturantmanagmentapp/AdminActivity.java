package com.example.resturantmanagmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu);


        Button buttonAdd = findViewById(R.id.buttonAddItem);
        Button buttonEdit = findViewById(R.id.buttonEditItem);
        Button buttonDelete = findViewById(R.id.buttonDeleteItem);
        TextView reservationsBtn = findViewById(R.id.reservations_button);
        TextView logoutBtn = findViewById(R.id.logout_button);


        if (buttonAdd != null) {
            buttonAdd.setOnClickListener(v -> {
                Intent intent = new Intent(AdminActivity.this, AddMenuItemActivity.class);
                startActivity(intent);
            });
        }


        if (buttonDelete != null) {
            buttonDelete.setOnClickListener(v -> {
                Intent intent = new Intent(AdminActivity.this, DeleteMenuItemActivity.class);
                startActivity(intent);
            });
        }


        if (buttonEdit != null) {
            buttonEdit.setOnClickListener(v -> {
                Intent intent = new Intent(AdminActivity.this, EditMenuActivity.class);
                startActivity(intent);
            });
        }


        if (reservationsBtn != null) {
            reservationsBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, AdminViewReservationsActivity.class);
                startActivity(intent);
            });
        }


        if (logoutBtn != null) {
            logoutBtn.setOnClickListener(v -> {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }
}