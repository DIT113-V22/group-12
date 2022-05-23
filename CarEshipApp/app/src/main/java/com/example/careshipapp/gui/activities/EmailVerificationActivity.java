package com.example.careshipapp.gui.activities;

import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.careshipapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



    public class EmailVerificationActivity extends AppCompatActivity {
        EditText email;
        Button continueBtn;
        TextView verifyMsg;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.email_verification);

            verifyMsg= findViewById(R.id.verifyMsg);
            email = findViewById(R.id.Email);
            continueBtn = findViewById(R.id.continueBtn);

            verifyMsg.setVisibility(View.INVISIBLE);

            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            FirebaseUser user = mAuth.getCurrentUser();
            String userId = mAuth.getCurrentUser().getUid();   //

            continueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (user != null) {
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EmailVerificationActivity.this,"Verification link has been sent to your email",Toast.LENGTH_SHORT).show();
                            }
                        }) .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"onFailure: email not sent." + e.getMessage());
                            }
                        });

                    }
                    continueBtn.setClickable(true);
                    continueBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            assert user != null;
                            if(!user.isEmailVerified()) {
                                verifyMsg.setVisibility(View.VISIBLE);
                                Toast.makeText(EmailVerificationActivity.this,"Email has not been verified yet",Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(EmailVerificationActivity.this, CustomerPaymentActivity.class));
                            }
                        }
                    });
                }
        });
        }
    }