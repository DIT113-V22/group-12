package com.example.careshipapp.user_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.careshipapp.R;
import com.example.careshipapp.customer_gui.UpdatePassword;


public class StaffLoginPage extends AppCompatActivity {

        EditText staffEmail, staffPassword;
        Button loginBtn;
        DBHelperClass database;
        TextView forgetPass;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.staff_login_page);

            staffEmail = (EditText) findViewById(R.id.staffEmail);
            staffPassword = (EditText) findViewById(R.id.staffPassword);
            loginBtn = (Button) findViewById(R.id.loginButton);
            database = new DBHelperClass(this);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = staffEmail.getText().toString();
                    String password = staffPassword.getText().toString();
                    Boolean validateUser= database.userExistsCheck(email, password);

                    if(validateUser && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        Toast.makeText(StaffLoginPage.this,"Login successful.",Toast.LENGTH_SHORT).show();
                        successfulLogin();

                    } if(email.isEmpty() && password.isEmpty()){
                        Toast.makeText(StaffLoginPage.this,"Add both email and password",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(StaffLoginPage.this,"Invalid login attempt.",Toast.LENGTH_SHORT).show();

                    }
                }
            });

            forgetPass = (TextView) findViewById(R.id.forgetPassword);

            forgetPass.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), UpdatePassword.class);
                    startActivity(intent);
                }
            });
        }


        public void successfulLogin(){     //replace with orders list class when available
            Intent intent = new Intent(this, MainActivityLoginPage.class);
            startActivity(intent);
        }
    }