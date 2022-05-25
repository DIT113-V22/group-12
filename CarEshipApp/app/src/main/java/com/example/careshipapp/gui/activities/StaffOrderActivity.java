package com.example.careshipapp.gui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careshipapp.R;
import com.example.careshipapp.gui.adapters.StaffOrderAdapter;
import com.example.careshipapp.gui.fragments.HomeFragment;
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


    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    RecyclerView recyclerView;
    List<StaffOrderModel> staffOrderModelList;
    StaffOrderAdapter staffOrderAdapter;
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_order);


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_home_24);



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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.staff_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_logout){
            auth.signOut();
            startActivity(new Intent(StaffOrderActivity.this, UserControllerActivity.class));
            finish();

        }

        return true;
    }


}