package com.example.careship;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderConfirmation extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirmation);

        Button trackButton = (Button) findViewById(R.id.buttonTrackOrder);
      

    }

    public void onClick(View view) {

        Intent myIntent = new Intent(OrderConfirmation.this, TrackOrder.class);
        startActivity(myIntent);
    }
}