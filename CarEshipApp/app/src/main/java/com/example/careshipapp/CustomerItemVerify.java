package com.example.careshipapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class CustomerItemVerify extends AppCompatActivity {

    TextView value;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_item_verify);

        value = (TextView) findViewById(R.id.value);
    }


    public void decrement(View view){
        if(count<= 0)
            count =0;
            else
        count--;
       value.setText(count);
    }
    public void increment(View view){
    count++;
       value.setText(count);
}

}

