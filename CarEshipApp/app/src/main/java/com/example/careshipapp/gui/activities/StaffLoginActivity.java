package com.example.careshipapp.gui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.careshipapp.R;
import com.example.careshipapp.gui.ForgotPasswordAuthActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class StaffLoginActivity extends AppCompatActivity {

        EditText staffEmail, staffPassword;
        Button loginBtn;
        TextView forgetPass;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    FirebaseFirestore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_login_page);

        mAuth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();

        staffEmail = (EditText) findViewById(R.id.staffEmail);
        staffPassword = (EditText) findViewById(R.id.staffPassword);
        loginBtn = (Button) findViewById(R.id.loginButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        progressBar.setVisibility(View.INVISIBLE);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = staffEmail.getText().toString();
                String password = staffPassword.getText().toString();

                if(TextUtils.isEmpty(email) ){
                   staffEmail.setError("Email field is empty");
                   staffEmail.requestFocus();
                }
                else if (TextUtils.isEmpty(password) ){
                   staffPassword.setError("Password field is empty");
                    staffPassword.requestFocus();
                }
                else {

                    progressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            checkIfAdmin(authResult.getUser().getUid());

                        }
                    }) .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(StaffLoginActivity.this, "Incorrect email or password, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    
                }
            }
        });

            forgetPass = (TextView) findViewById(R.id.forgetPassword);
            forgetPass.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ForgotPasswordAuthActivity.class);
                    startActivity(intent);
                }
            });
        }

    private void checkIfAdmin(String uid) {
        DocumentReference documentReference = store.collection("Users").document(uid);
     documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
    @Override
    public void onSuccess(DocumentSnapshot documentSnapshot) {
        if(documentSnapshot.getString("isAdmin") !=null){
            Toast.makeText(StaffLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StaffLoginActivity.this, StaffOrderActivity.class));

    } else{
            progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(StaffLoginActivity.this, "Not an authorized user.", Toast.LENGTH_SHORT).show();
    }
    }
});
}
    }



