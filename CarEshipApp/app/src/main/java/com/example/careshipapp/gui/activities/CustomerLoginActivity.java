package com.example.careshipapp.gui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.careshipapp.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomerLoginActivity extends AppCompatActivity {

    EditText email, password;
    MaterialButton loginButton, createAccountButton2;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    FirebaseFirestore store;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_page);

        mAuth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();

        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.password2);
        loginButton = (MaterialButton) findViewById(R.id.LoginButton);
        createAccountButton2 = (MaterialButton) findViewById(R.id.CreateAccountButton2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        progressBar.setVisibility(View.INVISIBLE);


        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(Email) ){
                    email.setError("Email field is empty");
                    email.requestFocus();
                }
                else if (TextUtils.isEmpty(pass) ){
                    password.setError("Password field is empty");
                    password.requestFocus();
                }
                else {

                progressBar.setVisibility(View.VISIBLE);

             mAuth.signInWithEmailAndPassword(Email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                 @Override
                 public void onSuccess(AuthResult authResult) {
                     checkIfCustomer(authResult.getUser().getUid());

                 }
             }) .addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     progressBar.setVisibility(View.GONE);
                     Toast.makeText(CustomerLoginActivity.this, "Incorrect email or password, please try again.", Toast.LENGTH_SHORT).show();
                 }
            });
        }
      }
    });

        createAccountButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });


        TextView forgotPassword = (TextView) this.findViewById(R.id.ForgetPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordAuthActivity.class);
                startActivity(intent);
            }
        });

    }



    private void checkIfCustomer(String uid) {
        DocumentReference documentReference = store.collection("Users").document(uid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.getString("isCustomer") !=null){
                    Toast.makeText(CustomerLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                } else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(CustomerLoginActivity.this, "Not an authorized user.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
