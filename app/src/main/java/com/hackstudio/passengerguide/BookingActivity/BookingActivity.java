package com.hackstudio.passengerguide.BookingActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hackstudio.passengerguide.Models.ParkingSpace;
import com.hackstudio.passengerguide.R;

public class BookingActivity extends AppCompatActivity {

    private TextView tvTitle;
    private Spinner spinnerVehicleType;
    private TextView tvCapacity;
    private Button btnBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        tvTitle = findViewById(R.id.tvTitle);
        spinnerVehicleType = findViewById(R.id.spinnerVehicleType);
        tvCapacity = findViewById(R.id.tvCapacity);
        btnBook = findViewById(R.id.btnBook);

        // Populate spinner with vehicle types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicle_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicleType.setAdapter(adapter);

//        btnBook.setOnClickListener(v -> bookParking());
    }

//    private void bookParking() {
//        // Get the selected parking space details (assuming you've already fetched the necessary details)
//        // For demonstration purposes, let's assume you have the parking space document ID in a variable called `parkingSpaceId`
//
//        // Fetch the current capacity from Firestore
//        db.collection("parkingSpaces")
//                .document(parkingSpaceId) // Assuming you have this variable with the ID of the parking space
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        ParkingSpace parkingSpace = documentSnapshot.toObject(ParkingSpace.class);
//                        if (parkingSpace != null) {
//                            int currentCapacity = parkingSpace.getCapacity();
//
//                            // Check if there's available capacity to book
//                            if (currentCapacity > 0) {
//                                // Subtract 1 from the current capacity
//                                int updatedCapacity = currentCapacity - 1;
//
//                                // Create a map with updated capacity value
//                                Map<String, Object> updatedData = new HashMap<>();
//                                updatedData.put("capacity", updatedCapacity);
//
//                                // Update the capacity value in Firestore
//                                db.collection("parkingSpaces")
//                                        .document(parkingSpaceId)
//                                        .update(updatedData)
//                                        .addOnSuccessListener(aVoid -> {
//                                            Toast.makeText(BookingActivity.this, "Parking space booked successfully", Toast.LENGTH_SHORT).show();
//                                            // Optionally, you can also update the local ParkingSpace object if needed
//                                        })
//                                        .addOnFailureListener(e -> {
//                                            Toast.makeText(BookingActivity.this, "Failed to book parking space: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        });
//                            } else {
//                                Toast.makeText(BookingActivity.this, "No available slots in this parking space", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    } else {
//                        Toast.makeText(BookingActivity.this, "Parking space details not found", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    Toast.makeText(BookingActivity.this, "Failed to fetch parking space details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                });
//    }

}
