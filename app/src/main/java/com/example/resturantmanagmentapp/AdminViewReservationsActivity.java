package com.example.resturantmanagmentapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminViewReservationsActivity extends AppCompatActivity implements ReservationsAdapter.OnReservationClickListener {

    private RecyclerView recyclerView;
    private ReservationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_reservations);

        TextView adminMenuBtn = findViewById(R.id.admin_menu_button);
        if (adminMenuBtn != null) {
            adminMenuBtn.setOnClickListener(v -> finish());
        }

        recyclerView = findViewById(R.id.recycler_view_reservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadReservations();
    }

    private void loadReservations() {
        Api.getAllReservations(this, new Api.ReservationCallback() {
            @Override
            public void onSuccess(List<Reservation> reservations) {

                adapter = new ReservationsAdapter(reservations, AdminViewReservationsActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onCancelClick(Reservation reservation, int position) {
        Api.deleteReservation(this, reservation.id, new Api.DeleteCallback() {
            @Override
            public void onDeleted() {
                new Thread(() -> {
                    AppDatabase.getInstance(AdminViewReservationsActivity.this)
                            .reservationDao()
                            .delete(reservation);

                    runOnUiThread(() -> {
                        if (adapter != null) {
                            adapter.removeAt(position);
                            Toast.makeText(AdminViewReservationsActivity.this, "Reservation Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(AdminViewReservationsActivity.this, "Error deleting: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}