package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.smart.R;
import com.example.smart.billAdapter.billOuterAdapter;
import com.example.smart.billModel.BillModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class billMain extends AppCompatActivity {

    RecyclerView recyclerView;
    billOuterAdapter adapter;

    private FirebaseAuth mAuth;
    FirebaseUser cur_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_main);

        recyclerView=findViewById(R.id.recViewMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAuth=FirebaseAuth.getInstance();
        cur_user= mAuth.getCurrentUser();

        FirebaseRecyclerOptions<BillModel> options =
                new FirebaseRecyclerOptions.Builder<BillModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Bill/"+cur_user.getUid().toString()+"/"), BillModel.class)
                        .build();

        adapter=new billOuterAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}