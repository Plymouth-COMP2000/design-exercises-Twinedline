package com.example.resturantmanagmentapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class EditMenuActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_edit_item_menu);

        TextView menuBtn = findViewById(R.id.menu_button);
        if (menuBtn != null) {
            menuBtn.setOnClickListener(v -> finish());
        }

        logoututils.setupLogout(this);

        db = AppDatabase.getInstance(this);
        loadMenuData();
    }

    private void loadMenuData() {
        new Thread(() -> {
            List<MenuItem> allItems = db.menuItemDao().getAll();

            List<MenuItem> pizzas = new ArrayList<>();
            List<MenuItem> sides = new ArrayList<>();
            List<MenuItem> drinks = new ArrayList<>();

            for (MenuItem item : allItems) {
                if (item.getCategory() == null) continue;
                String cat = item.getCategory().toLowerCase();
                if (cat.contains("pizza")) pizzas.add(item);
                else if (cat.contains("side")) sides.add(item);
                else if (cat.contains("drink")) drinks.add(item);
            }

            runOnUiThread(() -> {
                setupRecyclerView(findViewById(R.id.recyclerPizzas), pizzas);
                setupRecyclerView(findViewById(R.id.recyclerSides), sides);
                setupRecyclerView(findViewById(R.id.recyclerDrinks), drinks);
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncWithApi();
    }

    private void syncWithApi() {
        Api.getAllMenuItems(this, new Api.MenuCallback() {
            @Override
            public void onSuccess(List<MenuItem> items) {
                new Thread(() -> {
                    db.menuItemDao().deleteAll();
                    for (MenuItem item : items) {
                        db.menuItemDao().insert(item);
                    }
                    runOnUiThread(() -> loadMenuData());
                }).start();
            }
        });
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<MenuItem> items) {
        if (recyclerView == null) return;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        MenuAdapter adapter = new MenuAdapter(items);

        adapter.setOnItemClickListener(this::showEditDialog);

        recyclerView.setAdapter(adapter);
    }

    private void showEditDialog(MenuItem item) {
        View view = getLayoutInflater().inflate(R.layout.admin_edit_item_popup, null);

        EditText nameInput = view.findViewById(R.id.editName);
        EditText descInput = view.findViewById(R.id.editDesc);
        EditText priceInput = view.findViewById(R.id.editPrice);
        EditText urlInput = view.findViewById(R.id.editImageUrl);

        nameInput.setText(item.getName());
        descInput.setText(item.getDescription());
        priceInput.setText(String.valueOf(item.getPrice()));
        urlInput.setText(item.getImageUrl());

        new AlertDialog.Builder(this)
                .setTitle("Edit " + item.getName())
                .setView(view)
                .setPositiveButton("Save", (dialog, which) -> {
                    try {
                        item.setName(nameInput.getText().toString());
                        item.setDescription(descInput.getText().toString());
                        item.setPrice(Double.parseDouble(priceInput.getText().toString()));
                        item.setImageUrl(urlInput.getText().toString());
                        saveUpdate(item);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveUpdate(MenuItem item) {
        new Thread(() -> {
            db.menuItemDao().update(item);
            runOnUiThread(() -> {
                Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
                loadMenuData();
            });
        }).start();
    }
}