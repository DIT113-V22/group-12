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
import androidx.appcompat.widget.Toolbar;

import com.example.careshipapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {

    TextView order_id;
    EditText post_address, post_number;
    EditText contact_number;
    Button done;
    FirebaseFirestore firestore;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        order_id = findViewById(R.id.order_id_address);
        post_address = findViewById(R.id.post_address);
        post_number = findViewById(R.id.postcode);
        contact_number = findViewById(R.id.contact_number);
        done = findViewById(R.id.save_address);

        firestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        String orderid_address;
        orderid_address = getIntent().getStringExtra("orderid_address");
        order_id.setText(orderid_address);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String address = post_address.getText().toString();
                String postNumber = post_number.getText().toString();
                String contact = contact_number.getText().toString();

                if(address.isEmpty() ||postNumber.isEmpty() || contact.isEmpty() ){
                    Toast.makeText(AddressActivity.this, "Please enter non-empty values.", Toast.LENGTH_SHORT).show();
                }
                else if (postNumber.length() != 5) {
                    Toast.makeText(AddressActivity.this, "Please enter your 5 digit post code.", Toast.LENGTH_SHORT).show();
                    post_number.requestFocus();

                } else if(contact.length() != 10)  {
                    Toast.makeText(AddressActivity.this, "Please enter a valid number.", Toast.LENGTH_SHORT).show();
                    contact_number.requestFocus();
                } else {


                Map<String, Object> addressMap = new HashMap<>();
                addressMap.put("address",address);
                addressMap.put("contactNumber",contact);


                firestore.collection("StaffOrder").whereEqualTo("orderID", order_id.getText().toString())
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
                                                        Toast.makeText(AddressActivity.this, "Address Registered", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(AddressActivity.this, PaymentConfirmationActivity.class));


                                                    } else {
                                                        Toast.makeText(AddressActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                                }

                            }
                        });

            }
            }
        });

    }
}
