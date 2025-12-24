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
        Button btnEdit = findViewById(R.id.buttonEditItem);
        TextView resButton = findViewById(R.id.reservations_button);


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


        btnEdit.setOnClickListener(v -> {

            Intent intent = new Intent(AdminActivity.this, EditMenuActivity.class);
            startActivity(intent);
        });


        resButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminReservationsMenuActivity.class);
            startActivity(intent);
        });




    }
}