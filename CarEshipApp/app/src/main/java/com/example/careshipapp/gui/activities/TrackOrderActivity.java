package com.example.careshipapp.gui.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.careshipapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TrackOrderActivity extends AppCompatActivity {
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String orderdate;
    private TextView orderDate;
    private String estdate;
    private TextView estDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_order);

        orderDate = (TextView) findViewById(R.id.orderDate);

        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        orderdate = dateFormat.format(calendar.getTime());
        orderDate.setText(orderdate);

        // estimated time of delivery

        estDate = (TextView) findViewById(R.id.estDate);

        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+2);
        estdate = dateFormat.format(calendar.getTime());
        estDate.setText(estdate);

    }
}
