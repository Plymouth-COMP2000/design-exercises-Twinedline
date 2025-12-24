package com.example.resturantmanagmentapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EditMenuItemActivity extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_item_popup);

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

    private void setupRecyclerView(RecyclerView rv, List<MenuItem> items) {
        if (rv == null) return;
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        MenuAdapter adapter = new MenuAdapter(items);


        adapter.setOnItemClickListener(item -> showEditDialog(item));

        rv.setAdapter(adapter);
    }

    private void showEditDialog(MenuItem item) {
        View view = getLayoutInflater().inflate(R.layout.admin_edit_item_popup, null);
        EditText nameInp = view.findViewById(R.id.editName);
        EditText descInp = view.findViewById(R.id.editDesc);
        EditText priceInp = view.findViewById(R.id.editPrice);
        EditText urlInp = view.findViewById(R.id.editImageUrl);


        nameInp.setText(item.getName());
        descInp.setText(item.getDescription());
        priceInp.setText(String.valueOf(item.getPrice()));
        urlInp.setText(item.getImageUrl());

        new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("Update", (dialog, which) -> {

                    item.setName(nameInp.getText().toString());
                    item.setDescription(descInp.getText().toString());
                    item.setPrice(Double.parseDouble(priceInp.getText().toString()));
                    item.setImageUrl(urlInp.getText().toString());

                    updateItemInDb(item);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void updateItemInDb(MenuItem item) {
        new Thread(() -> {
            db.menuItemDao().update(item);



            runOnUiThread(() -> {
                Toast.makeText(this, "Item Updated!", Toast.LENGTH_SHORT).show();
                loadMenuData();
            });
        }).start();
    }
}