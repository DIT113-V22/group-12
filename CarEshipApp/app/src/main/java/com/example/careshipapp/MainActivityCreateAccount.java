package com.example.careshipapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class MainActivityCreateAccount extends AppCompatActivity {

    EditText username, createPassword, passwordReentry;
    MaterialButton createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_create_account);

        username = (EditText) findViewById(R.id.Username);
        createPassword = (EditText) findViewById(R.id.CreatePassword);
        passwordReentry = (EditText) findViewById(R.id.passwordReentry);
        createAccountButton = (MaterialButton) findViewById(R.id.CreateAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}