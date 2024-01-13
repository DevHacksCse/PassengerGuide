package com.hackstudio.passengerguide.LostAndFound;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackstudio.passengerguide.R;

import java.util.ArrayList;
import java.util.List;

public class ViewLostItemActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LostItemsAdapter adapter;
    private FirebaseFirestore db;

    Button add_item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lost_item);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        add_item=findViewById(R.id.add_item);

        // Fetch data from Firestore
        fetchDataFromFirestore();

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ViewLostItemActivity.this, ReportLostItemActivity.class);
                startActivity(i);
            }
        });
    }

    private void fetchDataFromFirestore() {
        db.collection("lost_items")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<LostItem> lostItems = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            String date = document.getString("date");
                            String imageUrl = document.getString("imageURL");
                            String stationName = document.getString("stationName");

                            LostItem lostItem = new LostItem(date, imageUrl, stationName);
                            lostItems.add(lostItem);
                        }

                        // Display data in RecyclerView
                        adapter = new LostItemsAdapter(this, lostItems);
                        recyclerView.setAdapter(adapter);

                    } else {
                        Log.e("Firestore", "Error getting documents: ", task.getException());
                    }
                });
    }

}
