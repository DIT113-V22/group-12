package com.example.careshipapp.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careshipapp.gui.models.MyOrderModel;
import com.example.careshipapp.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private Context context;
    private List<MyOrderModel> list;
    int totalAmount = 0;

    public MyOrderAdapter(Context context, List<MyOrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.currentDate.setText(list.get(position).getCurrentDate());
        holder.currentTime.setText(list.get(position).getCurrentTime());
        holder.productName.setText(list.get(position).getProductName());
        holder.productPrice.setText(list.get(position).getProductPrice());
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());
        holder.payment.setText(list.get(position).getPayment());
        holder.totalPrice.setText("$ : " + list.get(position).getTotalPrice());


        //total amount passed to payment activity
        /*
        totalAmount = totalAmount + list.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

         */




    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView currentDate;
         TextView currentTime;

        TextView productName;
        TextView productPrice;
        TextView totalQuantity;
        TextView totalPrice;
        TextView payment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            currentDate = itemView.findViewById(R.id.current_date);
            currentTime = itemView.findViewById(R.id.current_time);

            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            payment = itemView.findViewById(R.id.payment_status);



        }
    }
}
