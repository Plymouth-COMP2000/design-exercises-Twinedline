package com.example.resturantmanagmentapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditMenuActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText priceEditText;
    private EditText descriptionEditText;
    private EditText categoryEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_menu); // Your XML layout

        // Bind UI elements
        nameEditText = findViewById(R.id.editTextName);
        priceEditText = findViewById(R.id.editTextPrice);
        descriptionEditText = findViewById(R.id.editTextDescription);
        categoryEditText = findViewById(R.id.editTextCategory);
        addButton = findViewById(R.id.buttonAdd);

        // Set button click listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMenuItem();
            }
        });
    }

    private void addMenuItem() {
        String name = nameEditText.getText().toString().trim();
        String priceText = priceEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String category = categoryEditText.getText().toString().trim();

        // Basic validation
        if (name.isEmpty() || priceText.isEmpty() || description.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create MenuItem object
        MenuItem newItem = new MenuItem(name, price, description, category);

        // Call API
        Api.addMenuItem(this, newItem);

        // Optional: show success and clear fields
        Toast.makeText(this, "Menu item added", Toast.LENGTH_SHORT).show();
        nameEditText.setText("");
        priceEditText.setText("");
        descriptionEditText.setText("");
        categoryEditText.setText("");
    }
}
