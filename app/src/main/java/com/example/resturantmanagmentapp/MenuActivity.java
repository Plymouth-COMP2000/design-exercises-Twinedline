package com.example.resturantmanagmentapp;

import android.os.Bundle;
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


        loadLocalData();

    }

    private void loadLocalData() {
        new Thread(() -> {
            List<MenuItem> allItems = db.menuItemDao().getAll();


            android.util.Log.d("MENU_DEBUG", "Total items in DB: " + allItems.size());

            List<MenuItem> pizzas = new ArrayList<>();
            List<MenuItem> sides = new ArrayList<>();
            List<MenuItem> drinks = new ArrayList<>();

            for (MenuItem item : allItems) {
                if (item.getCategory() == null) continue;

                String category = item.getCategory().toLowerCase();


                if (category.contains("pizza")) {
                    pizzas.add(item);
                } else if (category.contains("side")) {
                    sides.add(item);
                } else if (category.contains("drink")) {
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