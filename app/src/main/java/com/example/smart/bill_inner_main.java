package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.smart.billAdapter.billInnerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class bill_inner_main extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView bill,time,date;
    billInnerAdapter billListAdapter;
    private FirebaseAuth mAuth;
    FirebaseUser cur_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_inner_main);

        String billId = getIntent().getStringExtra("id");

        mAuth=FirebaseAuth.getInstance();
        cur_user= mAuth.getCurrentUser();

        recyclerView = findViewById(R.id.billRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ItemModel> options =
                new FirebaseRecyclerOptions.Builder<ItemModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Bill/"+cur_user.getUid().toString()+"/"+billId +"/Items"), ItemModel.class)
                        .build();
        billListAdapter = new billInnerAdapter(options);
        recyclerView.setAdapter(billListAdapter);

        bill = findViewById(R.id.billId);
        bill.setText(billId);

        time = findViewById(R.id.time);
        time.setText(getIntent().getStringExtra("time").toString());

        date = findViewById(R.id.date);
        date.setText(getIntent().getStringExtra("date").toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        billListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        billListAdapter.stopListening();
    }
}