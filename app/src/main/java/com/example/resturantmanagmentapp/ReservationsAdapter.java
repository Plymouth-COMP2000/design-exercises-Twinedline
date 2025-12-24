package com.example.resturantmanagmentapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder> {

    private List<Reservation> reservations;
    private OnReservationClickListener listener;

    public interface OnReservationClickListener {
        void onCancelClick(Reservation reservation, int position);
    }

    public ReservationsAdapter(List<Reservation> reservations, OnReservationClickListener listener) {
        this.reservations = reservations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation res = reservations.get(position);
        holder.textCustName.setText(res.customerName);

        String details = res.numberOfPeople + " People - " + res.time + " - " + res.date;
        holder.textResDetails.setText(details);

        holder.btnCancel.setOnClickListener(v -> listener.onCancelClick(res, position));
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView textCustName, textResDetails;
        ImageButton btnCancel;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            textCustName = itemView.findViewById(R.id.text_cust_name);
            textResDetails = itemView.findViewById(R.id.text_res_details);
            btnCancel = itemView.findViewById(R.id.btn_cancel_res);
        }
    }

    public void removeAt(int position) {
        reservations.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, reservations.size());
    }
}