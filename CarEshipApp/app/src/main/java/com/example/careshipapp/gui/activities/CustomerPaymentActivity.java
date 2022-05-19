package com.example.careshipapp.gui.activities;


import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.careshipapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class CustomerPaymentActivity extends AppCompatActivity {

        EditText accountNumber, ccv, personName;
        Button completeButton;
        TextView subTotal;
        private FirebaseAuth mAuth;
        private FirebaseFirestore fStore;
        private DatePickerDialog dialog;
        private Button expiryDatePicker;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.customer_payment);

            accountNumber = findViewById(R.id.accountNumber);
            ccv = findViewById(R.id.CCV);
            personName = findViewById(R.id.personName);
            expiryDatePicker = findViewById(R.id.expiryDatePickerBtn);
            expiryDatePicker.setText(currentDate());


            subTotal = findViewById(R.id.subTotal);


            double amount = 0.0;
            amount = getIntent().getDoubleExtra("amount", 0.0);
            subTotal = findViewById(R.id.subTotal);
            subTotal.setText("Total:  " + amount + " $");


            mAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();

            dateShow();
            expiryDatePicker = findViewById(R.id.expiryDatePickerBtn);
            expiryDatePicker.setText(currentDate());

            completeButton = findViewById(R.id.CompleteButton);
            completeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    cardValidate();
                }
            });

        }


        private void cardValidate() {

            String accountNum = accountNumber.getText().toString();
            String CCV = ccv.getText().toString();
            String name = personName.getText().toString();
            String expiry = (String) expiryDatePicker.getText();


                if(accountNum.isEmpty() || expiry.isEmpty() || CCV.isEmpty() || name.isEmpty()){
                    Toast.makeText(CustomerPaymentActivity.this, "Please fill-in all boxes.", Toast.LENGTH_SHORT).show();
                }
                else if (accountNum.length() != 10) {
                    accountNumber.setError("invalid account number.");
                    accountNumber.requestFocus();
                }
                else if (CCV.length() != 3) {
                    ccv.setError("invalid ccv.");
                    ccv.requestFocus();

                }else if(!name.matches("[a-zA-Z ]+"))
                {
                    personName.requestFocus();
                    personName.setError("Enter alphabetical characters only.");

                }
                else  {
                    String notPaid = "Not Yet Paid";
                    String id  = mAuth.getCurrentUser().getUid();

                    Map<String, Object> paid = new HashMap<>();
                    paid.put("payment", "Paid");

                    fStore.collection("Order").document(id)
                            .collection("User").whereEqualTo("payment", notPaid)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {

                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        String documentID = documentSnapshot.getId();

                                        fStore.collection("Order").document(id).collection("User").document(documentID).update(paid)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {


                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(CustomerPaymentActivity.this,"Payment registered.",Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(CustomerPaymentActivity.this, PaymentConfirmationActivity.class));
                                                    }

                                                }) .addOnFailureListener(new OnFailureListener() {

                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Toast.makeText(CustomerPaymentActivity.this,"Something went wrong.",Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                    }
                                }

                            });

                }

            }
            private String currentDate()
            {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                int year = cal.get(java.util.Calendar.YEAR);
                int month = cal.get(java.util.Calendar.MONTH);
                month = month + 1;
                int day = cal.get(java.util.Calendar.DAY_OF_MONTH);
                return dateString(day, month, year);
            }

            private void dateShow(){
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date = dateString(day, month, year);
                        month = month + 1;
                        expiryDatePicker.setText(date);

                    }
                };

                java.util.Calendar calender = java.util.Calendar.getInstance();
                int year = calender.get(java.util.Calendar.YEAR);
                int month = calender.get(java.util.Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);

                dialog = new DatePickerDialog(this, dateSetListener, year, month, day);



            }

            private String dateString(int day, int month, int year) {
                return   day + "-" + monthFormat(month) + "-" + year;
            }



            private String monthFormat(int month){
                if(month == 1)
                    return "01";
                if(month == 2)
                    return "02";
                if(month == 3)
                    return "03";
                if(month == 4)
                    return "04";
                if(month == 5)
                    return "05";
                if(month == 6)
                    return "06";
                if(month == 7)
                    return "07";
                if(month == 8)
                    return "08";
                if(month == 9)
                    return "09";
                if(month == 10)
                    return "10";
                if(month == 11)
                    return "11";
                if(month == 12)
                    return "12";
                return "";
            }


            public void datePickerOpened(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 0);
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                dialog.show();

            }

        }
