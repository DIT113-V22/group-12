package com.example.careshipapp.customer_gui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.careshipapp.user_management.DBHelperClass;
import com.example.careshipapp.R;


public class CustomerAddress extends AppCompatActivity {

    EditText postAddress, zipCode;
    Button createAccountButton;
    DBHelperClass database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_address);

        database = new DBHelperClass(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
        }


