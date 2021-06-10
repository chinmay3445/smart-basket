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

import com.example.smart.billModel.BillModel;
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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Random;

public class cartPage extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    FirebaseUser cur_user;
    TextView tv1,tv2;

    Button btn;
    String pay;

    //BillID global
    long billId=1,amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        btn=findViewById(R.id.button);
        btn.setText("PAY :");
        mAuth=FirebaseAuth.getInstance();
        ref= FirebaseDatabase.getInstance().getReference();


//        tv1 = (TextView) findViewById(R.id.billtvcart);
//        tv2 = (TextView) findViewById(R.id.billtvcart1);

        cur_user= mAuth.getCurrentUser();

        String date = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()));
        recyclerView = findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("cart/"+cur_user.getUid()+"/"+date+"/"), Model.class)
                        .build();

        myAdapter = new MyAdapter(options);
        recyclerView.setAdapter(myAdapter);

        //to calculate amount
        ref.child("cart/"+cur_user.getUid().toString()+"/"+date+"/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long sum=0;
                for(DataSnapshot dst : snapshot.getChildren()) {
                    long price = 0;
                    long quantity = (long) dst.child("quantity").getValue();
                    price = (long) dst.child("price").getValue();

                    sum += quantity * price;
                }

                btn.setText("PAY : \u20B9"+sum);
                amt=sum;

//                tv1.setText(String.valueOf(sum));
//                tv2.setText(String.valueOf(sum));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void paymentpage(View view)
    {

        uploadToBill();
        billId+=1;
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }

    private void uploadToBill() {

        String billID= String.valueOf(billId);
        String date = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()));

        java.util.Date time = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        //System.out.println(sdf.format(time));

        BillModel billModel = new BillModel();
        billModel.setDate(date);
        billModel.setId(billID);
        billModel.setTot_amt(amt);
        billModel.setTime(sdf.format(time));

//        String time = String.valueOf(android.text.format.DateFormat.getTimeFormat("hh-mm-ss",new java.util.Date()));
        ref.child("Bill/"+cur_user.getUid().toString()+"/"+billID+"/").setValue(billModel);

        //to upload items details in bill

        ref.child("cart/"+cur_user.getUid().toString()+"/"+date+"/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ItemModel itemModel=new ItemModel();
                for(DataSnapshot dst : snapshot.getChildren()) {

                    String itemID= (String) dst.child("itemID").getValue();
                    String itemName= (String) dst.child("itemName").getValue();
                    long quantity = (long) dst.child("quantity").getValue();
                    long price = (long) dst.child("price").getValue();

                    itemModel.setName(itemName);
                    itemModel.setPrice((int) price);
                    itemModel.setQuantity((int) quantity);

                    ref.child("Bill/"+cur_user.getUid().toString()+"/"+billID+"/Items/"+itemID+"/").setValue(itemModel);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }
}