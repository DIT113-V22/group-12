package com.example.careshipapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivityCreateAccount extends AppCompatActivity {

    EditText username, createPassword, passwordReentry;
    MaterialButton createAccountButton;
    DBHelperClass database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_create_account);

        username = (EditText) findViewById(R.id.Username);
        createPassword = (EditText) findViewById(R.id.CreatePassword);
        passwordReentry = (EditText) findViewById(R.id.passwordReentry);
        createAccountButton = (MaterialButton) findViewById(R.id.CreateAccountButton);
        database = new DBHelperClass(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = username.getText().toString();
                String passwrd = createPassword.getText().toString();
                String repeatPassword = passwordReentry.getText().toString();

                if(email.isEmpty() || passwrd.isEmpty() || repeatPassword.isEmpty()){
                    Toast.makeText(MainActivityCreateAccount.this, "Please enter non-empty values.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(passwrd.equals(repeatPassword)){
                        Boolean customerChecking = database.userExistsCheck(email, passwrd);
                        Boolean adminChecking = database.adminExistsCheck(email);

                        if(!customerChecking){
                            Boolean checkInsertedData = database.insertData(email, passwrd);
                            if(checkInsertedData){
                                Toast.makeText(MainActivityCreateAccount.this, "Account created successfully.", Toast.LENGTH_SHORT).show();
                               /* Intent intent = new Intent(getApplicationContext(), nextscreen); this action leads to the next screen after creation of the account.
                                startActivity(intent);*/
                            }
                            else{
                                Toast.makeText(MainActivityCreateAccount.this, "Failed to create the account", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else if(!adminChecking){
                            Boolean checkInsertedData = database.insertData(email, passwrd);
                            if(checkInsertedData){
                                Toast.makeText(MainActivityCreateAccount.this, "Account created successfully.", Toast.LENGTH_SHORT).show();
                               /* Intent intent = new Intent(getApplicationContext(), nextscreen); this action leads to the next screen after creation of the account.
                                startActivity(intent);*/
                            }
                            else{
                                Toast.makeText(MainActivityCreateAccount.this, "Failed to create the account.", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(MainActivityCreateAccount.this, "Account already exists.", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else{
                        Toast.makeText(MainActivityCreateAccount.this, "Please repeat the password.", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
    }
}