package com.example.smart.billAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smart.ItemModel;
import com.example.smart.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class billInnerAdapter extends FirebaseRecyclerAdapter<ItemModel,billInnerAdapter.billViewHolder> {

    public billInnerAdapter(@NonNull FirebaseRecyclerOptions<ItemModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull billViewHolder holder,final int position, @NonNull ItemModel model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice().toString());
        holder.quantity.setText(model.getQuantity().toString());
    }

    @NonNull
    @Override
    public billViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_bill_inner,parent,false);
        return new billViewHolder(view);
    }

    class billViewHolder extends RecyclerView.ViewHolder {
        TextView name,quantity,price;

        public billViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            quantity = itemView.findViewById(R.id.quantityVal);
            price = itemView.findViewById(R.id.priceVal);
        }
    }
}
