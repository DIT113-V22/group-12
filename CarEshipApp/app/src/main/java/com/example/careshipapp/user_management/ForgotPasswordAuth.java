package com.example.careshipapp.user_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.careshipapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordAuth extends AppCompatActivity {

   private FirebaseAuth mAuth;

    EditText email;
    Button linkBtn;
    String eMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.forgot_password_auth);

       mAuth = FirebaseAuth.getInstance();

       email = (EditText) findViewById(R.id.Email);
       linkBtn =(Button) findViewById(R.id.sendLinkBtn);


      linkBtn.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View view) {
           validateEmail();
          }
      });
}

    private void validateEmail() {

        eMail = email.getText().toString();

        if(eMail.isEmpty()){
            Toast.makeText(this,"Please add your email address",Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }else{
           forgetPassword();
        }
    }

    private void forgetPassword(){

        mAuth.sendPasswordResetEmail(eMail)
            .addOnCompleteListener(new OnCompleteListener<Void>() {

        @Override
      public void onComplete(@NonNull Task<Void> task) {
      if(task.isSuccessful()){
      Toast.makeText(ForgotPasswordAuth.this, "Email has been sent successfully! Please check your email.", Toast.LENGTH_SHORT).show();
      startActivity(new Intent(ForgotPasswordAuth.this,UserActivityController.class));
      finish();
} else {
          Toast.makeText(ForgotPasswordAuth.this,"Something went wrong." + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
      }
                    }
                });
    }
}
