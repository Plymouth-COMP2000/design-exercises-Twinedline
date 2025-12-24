package com.example.resturantmanagmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        db = AppDatabase.getInstance(this);


        TextView bookTableBtn = findViewById(R.id.guest_menu_reservations);
        if (bookTableBtn != null) {
            bookTableBtn.setOnClickListener(v -> {
                Intent intent = new Intent(MenuActivity.this, ReservationsActivity.class);
                startActivity(intent);
            });
        }

        TextView profileBtn = findViewById(R.id.profile_button);
        if (profileBtn != null) {
            profileBtn.setOnClickListener(v -> {
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            });
        }


        loadLocalData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        syncWithApi();
    }

    private void loadLocalData() {
        new Thread(() -> {
            List<MenuItem> allItems = db.menuItemDao().getAll();


            android.util.Log.d("MENU_DEBUG", "Total items found in DB: " + allItems.size());

            List<MenuItem> pizzas = new ArrayList<>();
            List<MenuItem> sides = new ArrayList<>();
            List<MenuItem> drinks = new ArrayList<>();

            for (MenuItem item : allItems) {
                if (item.getCategory() == null) {

                    android.util.Log.e("MENU_DEBUG", "Item " + item.getName() + " has a NULL category");
                    continue;
                }


                String category = item.getCategory().toLowerCase().trim();

                if (category.contains("pizza")) {
                    pizzas.add(item);
                } else if (category.contains("side")) {
                    sides.add(item);
                } else if (category.contains("drink")) {
                    drinks.add(item);
                } else {
                    android.util.Log.w("MENU_DEBUG", "Unknown category found: " + category);
                }
            }

            runOnUiThread(() -> {
                setupRecyclerView(findViewById(R.id.recyclerPizzas), pizzas);
                setupRecyclerView(findViewById(R.id.recyclerSides), sides);
                setupRecyclerView(findViewById(R.id.recyclerDrinks), drinks);
            });
        }).start();
    }

    private void setupRecyclerView(RecyclerView rv, List<MenuItem> items) {
        if (rv != null) {
            rv.setLayoutManager(new GridLayoutManager(this, 3));
            rv.setAdapter(new MenuAdapter(items));
        }
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


                    runOnUiThread(() -> {
                        loadLocalData();
                    });
                }).start();
            }
        });
    }
}