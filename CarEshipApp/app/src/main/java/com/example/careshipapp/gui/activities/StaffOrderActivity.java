package com.example.careshipapp.gui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.careshipapp.R;
import com.example.careshipapp.gui.adapters.MyOrderAdapter;
import com.example.careshipapp.gui.adapters.StaffOrderAdapter;
import com.example.careshipapp.gui.models.StaffOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StaffOrderActivity extends AppCompatActivity {


    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    List<StaffOrderModel> staffOrderModelList;
    StaffOrderAdapter staffOrderAdapter;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_order);


        firestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.my_order_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.cart_rec2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        staffOrderModelList = new ArrayList<>();
        staffOrderAdapter= new StaffOrderAdapter(this,staffOrderModelList);
        recyclerView.setAdapter(staffOrderAdapter);

        firestore.collection("StaffOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        StaffOrderModel staffOrderModel = doc.toObject(StaffOrderModel.class);
                        staffOrderModelList.add(staffOrderModel);
                        staffOrderAdapter.notifyDataSetChanged();

                    }
                }else {
                    Toast.makeText(StaffOrderActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


}