package com.hackstudio.passengerguide.PayAndPark;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hackstudio.passengerguide.BookingActivity.BookingActivity;
import com.hackstudio.passengerguide.Models.ParkingSpace;
import com.hackstudio.passengerguide.Models.ParkingSpaceAdapter;
import com.hackstudio.passengerguide.R;

import java.util.ArrayList;
import java.util.List;

public class PayAndPark extends AppCompatActivity implements ParkingSpaceAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ParkingSpaceAdapter adapter;
    private List<ParkingSpace> parkingSpaces = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_and_park);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParkingSpaceAdapter(parkingSpaces, this);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        fetchParkingSpaces();
    }

    private void fetchParkingSpaces() {
        db.collection("parkingSpaces")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        parkingSpaces.clear(); // Clear existing data
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ParkingSpace parkingSpace = document.toObject(ParkingSpace.class);
                            parkingSpaces.add(parkingSpace);
                        }
                        adapter.notifyDataSetChanged(); // Notify adapter of data set change
                    } else {
                        Toast.makeText(PayAndPark.this, "Error fetching parking spaces", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(ParkingSpace parkingSpace) {
        // Handle item click here, for example:
        startActivity(new Intent(PayAndPark.this, BookingActivity.class));
    }
}
