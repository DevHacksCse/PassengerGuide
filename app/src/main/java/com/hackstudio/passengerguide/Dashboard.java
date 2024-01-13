package com.hackstudio.passengerguide;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.cardview.widget.CardView;

import com.hackstudio.passengerguide.Auth.Login;
import com.hackstudio.passengerguide.LostAndFound.ReportLostItemActivity;
import com.hackstudio.passengerguide.LostAndFound.ViewLostItemActivity;
import com.hackstudio.passengerguide.PayAndPark.PayAndPark;


public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Reference to CardView 1
        CardView cardView1 = findViewById(R.id.cardView1);
        CardView cardView2 = findViewById(R.id.cardView2);
        CardView cardView3 = findViewById(R.id.cardView3);

        // Set OnClickListener to CardView 1
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch another activity when CardView 1 is clicked
                Intent intent = new Intent(Dashboard.this, Login.class);
                startActivity(intent);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch another activity when CardView 1 is clicked
                Intent intent = new Intent(Dashboard.this, ViewLostItemActivity.class);
                startActivity(intent);
            }
        });

    }
}
