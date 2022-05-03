package com.example.careshipapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdatePassword extends AppCompatActivity {

    EditText newPassword, retypeNewPassword, emailAddress;
    Button resetButton;
    DBHelperClass database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.update_password);
        newPassword = (EditText) findViewById(R.id.newPass);
        retypeNewPassword= (EditText) findViewById(R.id. retypeNewPass);
        emailAddress = (EditText) findViewById(R.id.EmailAddress);
        resetButton =(Button) findViewById(R.id.resetBtn);

        database = new DBHelperClass(this);

        resetButton.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                validatePass();
            }
        }));
    }

    private boolean validatePass() {
        String  email = emailAddress.getText().toString();
        String newPass = newPassword.getText().toString();
        String retypePass = retypeNewPassword.getText().toString();

        if(email.isEmpty() || newPass.isEmpty() || retypePass.isEmpty()) {
            Toast.makeText(this,"Please fill in all boxes",Toast.LENGTH_SHORT).show();
            return false;

        } else if (newPass.length()<8) {
            Toast.makeText(this," Password must be at least 8 characters.",Toast.LENGTH_SHORT).show();
            return false;

        } else if (!newPass.equals(retypePass)) {
            Toast.makeText(this," Password does not match.",Toast.LENGTH_SHORT).show();
            return false;

        }else {
            database.updatePassword(email, newPass);
            Intent intent = new Intent(this,UserActivityController.class);
            startActivity(intent);
            Toast.makeText(this," Password has been successfully updated.",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}


