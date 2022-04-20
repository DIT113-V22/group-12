package com.example.careshipapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class heartdisease extends AppCompatActivity {

    TextView  medicineOrder;
    boolean[] selectedMedicine;
    ArrayList<Integer> medicineList = new ArrayList<>();
    String[] medicineArray = {"Aspirin 120 mg","Cozaar 100mg","Heparin 120 mg"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anxiety);

        medicineOrder = findViewById(R.id.medicine_order);

        selectedMedicine = new boolean[medicineArray.length];
        medicineOrder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        heartdisease.this
                );


                builder.setTitle("Select your medicine");

                builder.setCancelable(false);

                builder.setMultiChoiceItems(medicineArray, selectedMedicine, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            medicineList.add(i);

                            Collections.sort(medicineList);
                        }else{
                            medicineList.remove(i);
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for(int j=0; j<medicineList.size(); j++){
                            stringBuilder.append(medicineArray[medicineList.get(j)]);

                            if(j != medicineList.size()-1){
                                stringBuilder.append(", ");
                            }

                        }
                        medicineOrder.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        for(int j=0; j < selectedMedicine.length; j++){
                            selectedMedicine[j] = false;

                            medicineList.clear();

                            medicineOrder.setText("");
                        }
                    }


                });
                builder.show();
            }
        });
    }
}
