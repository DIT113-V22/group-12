package com.example.careshipapp.gui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.careshipapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class PaymentActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    DocumentReference documentReference;
    TextView subTotal;
    Button paymentBtn;
    //EditText address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_payment);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        paymentBtn = findViewById(R.id.CompleteButton);
        //address = findViewById(R.id.addressText);


        //get data from detailed activity
        double amount = 0.0;
        amount = getIntent().getDoubleExtra("amount", 0.0);

        subTotal = findViewById(R.id.subTotal);
        subTotal.setText("Total amount : " + amount + " $");


        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   updatePayment();


            }
        });

    }





    public void updatePayment(){
        String notPaid = "Not Yet Paid";

        Map<String, Object> paymentStatus = new HashMap<>();
        paymentStatus.put("payment", "Paid");

        firestore.collection("Order").document(auth.getCurrentUser().getUid())
                .collection("User").whereEqualTo("payment", notPaid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful() && !task.getResult().isEmpty()) {

                            DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                            String documentID = doc.getId();
                            firestore.collection("Order").document(auth.getCurrentUser().getUid())
                                    .collection("User").document(documentID)
                                    .update(paymentStatus)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(PaymentActivity.this, "Yesss", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(PaymentActivity.this, CheckoutActivity.class));
                                            } else {
                                                Toast.makeText(PaymentActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });


                        }

                    }
                });

    }



/*

    public  void updateAddress(){

        String changeAddress = address.getText().toString();
        String notAdd = "Not yet added";

        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("address", changeAddress);

        firestore.collection("StaffOrder").whereEqualTo("payment", notAdd)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                            String documentID = doc.getId();
                            firestore.collection("StaffOrder").document(documentID)
                                    .update(addressMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(PaymentActivity.this, "Yesss", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                                            } else {
                                                Toast.makeText(PaymentActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                        }

                    }
                });



    }

 */

}