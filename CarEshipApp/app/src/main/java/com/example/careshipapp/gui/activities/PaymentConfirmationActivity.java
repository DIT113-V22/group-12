package com.example.careshipapp.gui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.careshipapp.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


    public class PaymentConfirmationActivity extends AppCompatActivity {

        Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.order_confirmation);

            toolbar = findViewById(R.id.my_order_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });



            Calendar calendar = Calendar.getInstance();

            TextView dateText = (TextView) findViewById(R.id.dateText);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.GERMANY);
            String orderDate = dateFormat.format(calendar.getTime());
            dateText.setText(orderDate);


            TextView estimatedDelivery = (TextView) findViewById(R.id.estimatedDeliveryDate);

            dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+2);
            String estDate = dateFormat.format(calendar.getTime());
            estimatedDelivery.setText(estDate);


            Button backToMainBtn = (Button) findViewById(R.id.backButton);
            backToMainBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
        startActivity(new Intent(PaymentConfirmationActivity.this, MainActivity.class));

                }
            });

        }

    }
