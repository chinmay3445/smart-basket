package com.example.smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

public class todaysDeal extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterDeal AdapterDeal;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    FirebaseUser cur_user;
    TextView tv1,tv2;

    Button btn;
    String pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_deal);

//        btn=findViewById(R.id.button);
//        btn.setText("PAY :");
        mAuth=FirebaseAuth.getInstance();
        ref= FirebaseDatabase.getInstance().getReference();

//        tv1 = (TextView) findViewById(R.id.billtvcart);
//        tv2 = (TextView) findViewById(R.id.billtvcart1);

        cur_user= mAuth.getCurrentUser();

//        String date = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()));
        recyclerView = findViewById(R.id.recviewdeal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("todaysDeals/"), Model.class)
                        .build();

        AdapterDeal = new AdapterDeal(options);
        recyclerView.setAdapter(AdapterDeal);

        //to calculate amount
//        ref.child("cart/"+cur_user.getUid().toString()+"/"+date+"/").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                long sum=0;
//                for(DataSnapshot dst : snapshot.getChildren()) {
//                    long price = 0;
//                    long quantity = (long) dst.child("quantity").getValue();
//                    price = (long) dst.child("price").getValue();
//
//                    sum += quantity * price;
//                }
//
//                btn.setText("PAY : \u20B9"+sum);
//
////                tv1.setText(String.valueOf(sum));
////                tv2.setText(String.valueOf(sum));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

//    public void paymentpage(View view)
//    {
//        Intent intent = new Intent(this, PaymentActivity.class);
//        startActivity(intent);
//    }

    @Override
    protected void onStart() {
        super.onStart();
        AdapterDeal.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        AdapterDeal.stopListening();
    }
}