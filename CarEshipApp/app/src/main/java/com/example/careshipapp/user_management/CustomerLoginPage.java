package com.example.careshipapp.user_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.careshipapp.R;
import com.example.careshipapp.customer_gui.ListOrders;
import com.example.careshipapp.customer_gui.MainActivityCategoryList;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomerLoginPage extends AppCompatActivity {

    EditText email, password;
    MaterialButton loginButton, createAccountButton2;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    FirebaseFirestore store;

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
                     Toast.makeText(CustomerLoginPage.this, "Incorrect email or password, please try again.", Toast.LENGTH_SHORT).show();
                 }
            });
        }
      }
    });

        createAccountButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityCreateAccount.class);
                startActivity(intent);
            }
        });


        TextView forgotPassword = (TextView) this.findViewById(R.id.ForgetPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordAuth.class);
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
                    Toast.makeText(CustomerLoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivityCategoryList.class);
                    startActivity(intent);

                } else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(CustomerLoginPage.this, "Not an authorized user.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
