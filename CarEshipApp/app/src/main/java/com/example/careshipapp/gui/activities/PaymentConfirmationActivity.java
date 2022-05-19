package com.example.careshipapp.gui.activities;

import androidx.appcompat.app.AppCompatActivity;
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
        private TextView dateText, estimatedDelivery;

        private Calendar calendar;
        private SimpleDateFormat dateFormat;
        private String orderDate,estDate;
        private Button backToMainBtn;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.order_confirmation);

            calendar = Calendar.getInstance();

            dateText = (TextView) findViewById(R.id.dateText);

            dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.GERMANY);
            orderDate = dateFormat.format(calendar.getTime());
            dateText.setText(orderDate);



            estimatedDelivery = (TextView) findViewById(R.id.estimatedDeliveryDate);

            dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+2);
            estDate = dateFormat.format(calendar.getTime());
            estimatedDelivery.setText(estDate);


            backToMainBtn = (Button) findViewById(R.id.backButton);
            backToMainBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
        startActivity(new Intent(PaymentConfirmationActivity.this, MainActivity.class));

                }
            });

        }

    }
