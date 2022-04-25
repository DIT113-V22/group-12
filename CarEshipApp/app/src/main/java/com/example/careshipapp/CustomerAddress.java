package com.example.careshipapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class CustomerAddress extends AppCompatActivity {

    EditText postAddress, zipCode;
    Button doneButton;
    DBHelperClass database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_address);

        database = new DBHelperClass(this);

       postAddress = (EditText) findViewById(R.id.postAddress);
       zipCode = (EditText) findViewById(R.id.zipCode);
       doneButton = (Button) findViewById(R.id.doneBtn);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = postAddress.getText().toString();
               Integer postCode = Integer.valueOf(zipCode.getText().toString());


            }
        });
    }
        }


