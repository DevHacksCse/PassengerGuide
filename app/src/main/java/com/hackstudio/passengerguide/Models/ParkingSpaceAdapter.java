package com.hackstudio.passengerguide.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackstudio.passengerguide.R;

import java.util.List;

public class ParkingSpaceAdapter extends RecyclerView.Adapter<ParkingSpaceAdapter.ViewHolder> {

    private List<ParkingSpace> parkingSpaces;
    private OnItemClickListener listener;

    public ParkingSpaceAdapter(List<ParkingSpace> parkingSpaces, OnItemClickListener listener) {
        this.parkingSpaces = parkingSpaces;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_parking_space, parent, false);
        return new ViewHolder(view, parkingSpaces, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParkingSpace space = parkingSpaces.get(position);
        holder.tvLocation.setText(space.getLocation());
        holder.tvCapacity.setText(String.valueOf(space.getCapacity()));
        holder.tvType.setText(space.getType());
        holder.tvPrice.setText(String.valueOf(space.getPrice()));
    }

    @Override
    public int getItemCount() {
        return parkingSpaces.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ParkingSpace parkingSpace);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocation, tvCapacity, tvType, tvPrice;
        private List<ParkingSpace> parkingSpaces;

        public ViewHolder(@NonNull View itemView, List<ParkingSpace> parkingSpaces, OnItemClickListener listener) {
            super(itemView);
            this.parkingSpaces = parkingSpaces;
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvCapacity = itemView.findViewById(R.id.tvCapacity);
            tvType = itemView.findViewById(R.id.tvType);
            tvPrice = itemView.findViewById(R.id.tvPrice);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(parkingSpaces.get(position));
                    }
                }
            });
        }
    }
}
