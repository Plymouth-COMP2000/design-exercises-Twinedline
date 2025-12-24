package com.example.resturantmanagmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddMenuItemActivity extends AppCompatActivity {

    private EditText editTextName, editTextDesc, editTextPrice, editTextImageUrl;
    private Spinner spinnerCategory;
    private Button buttonSubmit;
    private TextView menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_add_item);


        editTextName = findViewById(R.id.editTextName);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextPrice = findViewById(R.id.editTextPrice);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        editTextImageUrl = findViewById(R.id.editTextImage_URL);
        buttonSubmit = findViewById(R.id.buttonSubmitItem);
        menuButton = findViewById(R.id.menu_button);


        logoututils.setupLogout(this);


        String[] categories = {"Pizza", "Side", "Drink"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);

        buttonSubmit.setOnClickListener(v -> submitMenuItem());

        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddMenuItemActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void submitMenuItem() {
        String name = editTextName.getText().toString().trim();
        String description = editTextDesc.getText().toString().trim();
        String priceText = editTextPrice.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String imageUrl = editTextImageUrl.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || priceText.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            MenuItem newItem = new MenuItem(name, description, price, category, imageUrl);

            Api.addMenuItem(this, newItem, new Api.AddMenuItemCallback() {
                @Override
                public void onSaved(MenuItem savedItemFromServer) {
                    new Thread(() -> {
                        AppDatabase.getInstance(AddMenuItemActivity.this).menuItemDao().insert(savedItemFromServer);
                        runOnUiThread(() -> {
                            Toast.makeText(AddMenuItemActivity.this, "Added to Server & Local DB", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }).start();
                }
            });

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number for price", Toast.LENGTH_SHORT).show();
        }
    }
}