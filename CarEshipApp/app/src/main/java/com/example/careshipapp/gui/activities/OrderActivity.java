package com.example.careshipapp.gui.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careshipapp.gui.adapters.MyOrderAdapter;
import com.example.careshipapp.gui.models.MyOrderModel;

import com.example.careshipapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<MyOrderModel> myOrderModelList;
    MyOrderAdapter myOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();


        toolbar = findViewById(R.id.my_order_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myOrderModelList = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(this,myOrderModelList);
        recyclerView.setAdapter(myOrderAdapter);

        firestore.collection("Order").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                MyOrderModel myOrderModel = doc.toObject(MyOrderModel.class);
                                myOrderModelList.add(myOrderModel);
                                myOrderAdapter.notifyDataSetChanged();

                            }
                        }else {
                            Toast.makeText(OrderActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
}

