package com.example.careshipapp.customer_gui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.careshipapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderConfirmation extends AppCompatActivity {
    private TextView dateText;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirmation);

        dateText = (TextView) findViewById(R.id.dateText);

        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        dateText.setText(date);

        Button btnTrackOrder = (Button) findViewById(R.id.buttonTrackOrder);

        btnTrackOrder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(OrderConfirmation.this, TrackOrder.class));
            }
        });

    }

    }
