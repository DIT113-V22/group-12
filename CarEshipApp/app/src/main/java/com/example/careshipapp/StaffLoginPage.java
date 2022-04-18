package com.example.careshipapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;



    public class StaffLoginPage extends AppCompatActivity {

        EditText staffUsername, staffPassword;
        Button loginBtn;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.staff_login_page);

            staffUsername = (EditText) findViewById(R.id.staffUsername);
            staffPassword = (EditText) findViewById(R.id.staffPassword);
            loginBtn = (Button) findViewById(R.id.loginButton);
    }
}



