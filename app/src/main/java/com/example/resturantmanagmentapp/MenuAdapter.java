package com.example.resturantmanagmentapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;
import com.bumptech.glide.Glide;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuItem> menuItems;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(MenuItem item);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MenuAdapter(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem item = menuItems.get(position);

        holder.name.setText(item.getName());

        try {
            double priceValue = Double.parseDouble(String.valueOf(item.getPrice()));
            holder.price.setText(String.format(Locale.UK, "£%.2f", priceValue));
        } catch (Exception e) {
            holder.price.setText("£" + item.getPrice());
        }

        Glide.with(holder.itemView.getContext())
                .load(item.getImageUrl())
                .placeholder(R.drawable.pizza)
                .error(R.drawable.pizza)
                .centerCrop()
                .into(holder.image);


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);
            image = itemView.findViewById(R.id.itemImage);
        }
    }
}