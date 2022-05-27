package com.example.careshipapp.gui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.careshipapp.gui.models.NewProductsModel;
import com.example.careshipapp.gui.models.ShowAllModel;

import com.example.careshipapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;


public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating,name,description,price,quantity;
    Button placeOrder;
    ImageView addItems,removeItems;

    Toolbar toolbar;

    int totalQuantity = 1;
    int totalPrice = 0;


    //New Products
    NewProductsModel newProductsModel = null;
    private FirebaseFirestore firestore;

    //Show All Products
    ShowAllModel showAllModel = null;


    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);



        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detailed");

        if(object instanceof NewProductsModel){
            newProductsModel =  (NewProductsModel) object;
        }else if(object instanceof ShowAllModel){
            showAllModel = (ShowAllModel) object;
        }

        detailedImg = findViewById(R.id.detailed_img);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        quantity = findViewById(R.id.quantity);

        placeOrder = findViewById(R.id.buy_order);


        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);


        //New Products
        if( newProductsModel != null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));

            totalPrice = newProductsModel.getPrice() * totalQuantity;

        }


        //Show All Models
        if( showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));

            totalPrice = showAllModel.getPrice() * totalQuantity;

        }


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                double amount = 0.0;


                if(newProductsModel != null){
                    amount =  newProductsModel.getPrice() * totalQuantity;
                }
                if( showAllModel != null){
                    amount = showAllModel.getPrice() * totalQuantity;
                }

                //addToOrderList();

                String saveCurrentTime,saveCurrentDate;
                Calendar calForDate = Calendar.getInstance();
                int orderID = generateOrderID();


                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy - MM - dd");
                saveCurrentDate = currentDate.format(calForDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
                saveCurrentTime = currentTime.format(calForDate.getTime());

                final HashMap<String,Object> cartMap = new HashMap<>();
                final HashMap<String,Object> staffMap = new HashMap<>();

                cartMap.put("productName", name.getText().toString());
                cartMap.put("productPrice",price.getText().toString());
                cartMap.put("currentTime",saveCurrentTime);
                cartMap.put("currentDate",saveCurrentDate);
                cartMap.put("orderStatus", "In Progress...");
                cartMap.put("totalQuantity",quantity.getText().toString());
                cartMap.put("totalPrice",totalPrice);
                cartMap.put("orderID",orderID+"");
                cartMap.put("payment","Not Yet Paid");


                firestore.collection("Order").document(auth.getCurrentUser().getUid())
                        .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(DetailedActivity.this,"To Payment", Toast.LENGTH_SHORT).show();

                            }
                        });


                staffMap.put("orderID",orderID+"");
                staffMap.put("address","Not yet added");
                staffMap.put("orderStatus", "In Progress...");
                staffMap.put("contactNumber","Not yet added");


                firestore.collection("StaffOrder").add(staffMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                    }
                });








                Intent intent = new Intent(DetailedActivity.this,CustomerPaymentActivity.class);
                intent.putExtra("amount",amount);
                intent.putExtra("orderid_detailed",orderID);
                startActivity(intent);
            }
        });


        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(totalQuantity < 10){
                   totalQuantity++;
                   quantity.setText(String.valueOf(totalQuantity));

                   if(newProductsModel != null){
                       totalPrice = newProductsModel.getPrice() * totalQuantity;
                   }
                   if(showAllModel != null){
                       totalPrice = showAllModel.getPrice() * totalQuantity;
                   }


               }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));

                    if(newProductsModel != null){
                        totalPrice = newProductsModel.getPrice() * totalQuantity;
                    }
                    if(showAllModel != null){
                        totalPrice = showAllModel.getPrice() * totalQuantity;
                    }

                }
            }
        });


    }









    private Integer generateOrderID(){
        Random rand = new Random(); //instance of random class
        int min = 1000000;
        int max = 1999999;
        int upperbound = 10;
        //generate random values from 0-24
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);

        return random_int;
    }


/*
    public void openPayment(){
        startActivity(new Intent(DetailedActivity.this,PaymentActivity.class));

        if(newProductsModel != null){
            getIntent().putExtra()
        }

    }

 */


}