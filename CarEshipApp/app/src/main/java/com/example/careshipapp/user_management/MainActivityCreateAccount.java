package com.example.careshipapp.user_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.careshipapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivityCreateAccount extends AppCompatActivity {


    EditText username, createPassword, passwordReentry, postAddress, zipCode;
    MaterialButton createAccountButton;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseStore;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_create_account);

        mAuth = FirebaseAuth.getInstance();
       firebaseStore = FirebaseFirestore.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        username = (EditText) findViewById(R.id.Username);
        createPassword = (EditText) findViewById(R.id.CreatePassword);
        passwordReentry = (EditText) findViewById(R.id.passwordReentry);
        postAddress = (EditText) findViewById(R.id.postAddress);
        zipCode = (EditText) findViewById(R.id.zipCode);
        createAccountButton = (MaterialButton) findViewById(R.id.CreateAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
   });
    }

    private void register() {

   final String email = username.getText().toString();
    String password = createPassword.getText().toString();
    String repeatPassword = passwordReentry.getText().toString();
  final  String address = postAddress.getText().toString();
   final String code = zipCode.getText().toString();


    if(email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || code.isEmpty() || address.isEmpty()){
        Toast.makeText(MainActivityCreateAccount.this, "Please enter non-empty values.", Toast.LENGTH_SHORT).show();
        return;
    }
    else if (code.length() != 5) {
        Toast.makeText(MainActivityCreateAccount.this, "Please enter your 5 digit post code.", Toast.LENGTH_SHORT).show();
        zipCode.requestFocus();
        return;
    }
    else if (password.length() < 8) {
        Toast.makeText(MainActivityCreateAccount.this, "Please enter at least 8 characters or numbers.", Toast.LENGTH_SHORT).show();
        createPassword.requestFocus();
        return;
    }
    else if(!password.equals(repeatPassword)) {
        Toast.makeText(MainActivityCreateAccount.this, "The re-entered password is incorrect.", Toast.LENGTH_SHORT).show();
        return;

    }
    else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        Toast.makeText(MainActivityCreateAccount.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
        return;
    }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task ->  {

                    if (task.isSuccessful()) {
                       Toast.makeText(MainActivityCreateAccount.this, "Account has been created successfully.", Toast.LENGTH_SHORT).show();

                        id = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = firebaseStore.collection("Users").document(id);

                            Map<String,Object> usersInfo = new HashMap<>();
                            usersInfo.put("email", email);
                            usersInfo.put("address", address);
                            usersInfo.put("zipCode", code);
                            usersInfo.put("isCustomer","1");
                            usersInfo.put("id" , mAuth.getCurrentUser().getUid());

                            documentReference.set(usersInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Log.d("TAG", "Successfully added" + id);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("TAG", "Error adding user" + id);
                                        }
                                    });
                        startActivity(new Intent(MainActivityCreateAccount.this, CustomerLoginPage.class));
                        }
         });

    }
}







