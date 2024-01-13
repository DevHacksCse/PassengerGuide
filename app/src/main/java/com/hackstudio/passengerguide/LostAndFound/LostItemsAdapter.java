package com.hackstudio.passengerguide.LostAndFound;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackstudio.passengerguide.LostAndFound.LostItem;
import com.hackstudio.passengerguide.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LostItemsAdapter extends RecyclerView.Adapter<LostItemsAdapter.LostItemViewHolder> {
    private List<LostItem> lostItems;
    private Context context;

    public LostItemsAdapter(Context context, List<LostItem> lostItems) {
        this.context = context;
        this.lostItems = lostItems;
    }

    @NonNull
    @Override
    public LostItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lost, parent, false);
        return new LostItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LostItemViewHolder holder, int position) {
        LostItem item = lostItems.get(position);

        holder.textDate.setText(item.getDate());

        Log.d("tag",item.getImageUrl());

        // Load image using Picasso
        Picasso.get()
                .load(item.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageItem);

        holder.textStation.setText(item.getStationName());
    }

    @Override
    public int getItemCount() {
        return lostItems.size();
    }

    public static class LostItemViewHolder extends RecyclerView.ViewHolder {
        TextView textDate;
        ImageView imageItem;
        TextView textStation;

        public LostItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.textDate);
            imageItem = itemView.findViewById(R.id.imageItem);
            textStation = itemView.findViewById(R.id.textStation);
        }
    }
}
