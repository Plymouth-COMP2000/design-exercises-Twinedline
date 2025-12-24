package com.example.resturantmanagmentapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import java.util.List;

public class AdminViewReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationsAdapter adapter;
    private List<Reservation> reservationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_reservations);


        TextView btnBackToResMenu = findViewById(R.id.reservations_button);


        btnBackToResMenu.setOnClickListener(v -> {

            finish();
        });


        recyclerView = findViewById(R.id.recycler_view_reservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadReservations();
    }

    private void loadReservations() {
        new Thread(() -> {
            reservationList = AppDatabase.getInstance(this).reservationDao().getAll();

            runOnUiThread(() -> {
                adapter = new ReservationsAdapter(reservationList, (reservation, position) -> {
                    deleteReservation(reservation, position);
                });
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

    private void deleteReservation(Reservation res, int position) {
        new Thread(() -> {
            AppDatabase.getInstance(this).reservationDao().delete(res);
            runOnUiThread(() -> {
                reservationList.remove(position);
                adapter.notifyItemRemoved(position);
            });
        }).start();
    }
}