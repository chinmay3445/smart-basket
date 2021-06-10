package com.example.smart.billAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smart.R;
import com.example.smart.billModel.BillModel;
import com.example.smart.bill_inner_main;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class billOuterAdapter extends FirebaseRecyclerAdapter<BillModel,billOuterAdapter.myViewHolder> {

    public billOuterAdapter(@NonNull FirebaseRecyclerOptions<BillModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, final int position, @NonNull final BillModel model) {
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.amount.setText(model.getTot_amt().toString());

        holder.billDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, bill_inner_main.class);
                intent.putExtra("id",model.getId());
                intent.putExtra("date",model.getDate());
                intent.putExtra("time",model.getTime());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_bill_outer,parent,false);
        return  new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView date,time,amount;
        Button billDetailsBtn;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            amount = itemView.findViewById(R.id.amount);
            billDetailsBtn = itemView.findViewById(R.id.billBtn);
        }

    }
}
