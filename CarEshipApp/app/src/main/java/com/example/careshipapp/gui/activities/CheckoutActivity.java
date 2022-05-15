package com.example.careshipapp.gui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.careshipapp.R;

public class CheckoutActivity extends AppCompatActivity {

    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirmation);


      btn = findViewById(R.id.buttonTrackOrder);

      btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(CheckoutActivity.this, OrderActivity.class);
              startActivity(intent);
          }
      });

    }
}
