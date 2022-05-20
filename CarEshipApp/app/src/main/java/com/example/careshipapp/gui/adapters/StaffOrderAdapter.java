package com.example.careshipapp.gui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.careshipapp.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.careshipapp.car_control.JoystickMainActivity;
import com.example.careshipapp.gui.models.StaffOrderModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class StaffOrderAdapter extends RecyclerView.Adapter<StaffOrderAdapter.ViewHolder>{

    private Context context;
    private List<StaffOrderModel> list;

    public StaffOrderAdapter(Context context, List<StaffOrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.address.setText(list.get(position).getAddress());
        holder.orderID.setText(list.get(position).getOrderID());
        holder.contactNum.setText(list.get(position).getContactNumber());
        holder.orderStatus.setText(list.get(position).getOrderStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JoystickMainActivity.class);
                intent.putExtra("joystick",list.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView  orderID, orderStatus, address, contactNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.order_id);
            address = itemView.findViewById(R.id.address);
            contactNum = itemView.findViewById(R.id.contactNum);
            orderStatus = itemView.findViewById(R.id.order_status);


        }
    }
}
