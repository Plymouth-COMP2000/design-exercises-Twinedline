package com.example.resturantmanagmentapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class DeleteMenuItemActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_delete_item_menu);

        TextView menuBtn = findViewById(R.id.menu_button);
        menuBtn.setOnClickListener(v -> finish());

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
                if (cat.contains("pizza")) {
                    pizzas.add(item);
                } else if (cat.contains("side")) {
                    sides.add(item);
                } else if (cat.contains("drink")) {
                    drinks.add(item);
                }
            }

            runOnUiThread(() -> {

                setupRecyclerView(findViewById(R.id.recyclerPizzas), pizzas);
                setupRecyclerView(findViewById(R.id.recyclerSides), sides);
                setupRecyclerView(findViewById(R.id.recyclerDrinks), drinks);
            });
        }).start();
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<MenuItem> items) {
        if (recyclerView == null) return;

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        MenuAdapter adapter = new MenuAdapter(items);


        adapter.setOnItemClickListener(item -> {
            showConfirmDeleteDialog(item);
        });

        recyclerView.setAdapter(adapter);
    }

    private void showConfirmDeleteDialog(MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Would you like to delete " + item.getName() + " off the menu?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteItem(item);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteItem(MenuItem item) {
        new Thread(() -> {

            db.menuItemDao().delete(item);


            Api.deleteMenuItem(this, item.getId());

            runOnUiThread(() -> {
                Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
                loadMenuData();
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
}