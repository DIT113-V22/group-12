package com.example.careshipapp.customer_gui;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.careshipapp.R;
import com.example.careshipapp.user_management.MainActivityCreateAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class CustomerPayment  extends AppCompatActivity {

    EditText accountNumber, expiryDate, ccv, personName;
    Button completeButton,  linkBtn;
    TextView subTotal, verifyMsg;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_payment);

       accountNumber = findViewById(R.id.accountNumber);
       expiryDate = findViewById(R.id.expiryDate);
       ccv = findViewById(R.id.CCV);
       personName = findViewById(R.id.personName);
       completeButton = findViewById(R.id.CompleteButton);
       linkBtn = findViewById(R.id.sendLinkBtn);
       verifyMsg= findViewById(R.id.verifyMsg);
       //Include amount in payment screen?
       subTotal = findViewById(R.id.subTotal);

        mAuth = FirebaseAuth.getInstance();

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cardValidate();
            }
        });
    }
    private void cardValidate() {

        Editable editable = null;

        String accountNum = accountNumber.getText().toString();
        String expiry = expiryDate.getText().toString();
        String CCV = ccv.getText().toString();
        String name = personName.getText().toString();

        if(accountNum.isEmpty() ||expiry.isEmpty() || CCV.isEmpty() ||name.isEmpty()){
            Toast.makeText(CustomerPayment.this, "Please fill-in all boxes.", Toast.LENGTH_SHORT).show();
        }
        else if (accountNumber.length() != 10) {
            Toast.makeText(CustomerPayment.this, "Please fill-in all boxes.", Toast.LENGTH_SHORT).show();
        }
        else if (ccv.length() != 3) {
            Toast.makeText(CustomerPayment.this, "ccv should contain only 3 digits.", Toast.LENGTH_SHORT).show();


        }  //not sure about this and where it should be yet
        else if (editable.length() > 0 && (editable.length() % 3) == 0) {
                final char c = editable.charAt(editable.length() - 1);
                if ('/' == c) {
                    editable.delete(editable.length() - 1, editable.length());
                }
            }
           else if (editable.length() > 0 && (editable.length() % 3) == 0) {
                char c = editable.charAt(editable.length() - 1);
                if (Character.isDigit(c) && TextUtils.split(editable.toString(), String.valueOf("/")).length <= 2) {
                    editable.insert(editable.length() - 1, String.valueOf("/"));
                }
            }

       linkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = mAuth.getCurrentUser();
                assert user != null;
                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    Toast.makeText(CustomerPayment.this,"Verification link has been sent to your email",Toast.LENGTH_SHORT).show();
                    }
                }) .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"onFailure: email not sent." + e.getMessage());
                    }
                });
                completeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!user.isEmailVerified()) {
                            verifyMsg.setVisibility(View.VISIBLE);
                            Toast.makeText(CustomerPayment.this,"Email is not verified yet",Toast.LENGTH_SHORT).show();
                        } else {
                           Intent intent = new Intent(CustomerPayment.this,OrderConfirmation.class);
                        }
                    }
                });
            }
        });



        //Intent intent = new Intent(CustomerPayment.this,EmailVerification.class);



        }

        }


