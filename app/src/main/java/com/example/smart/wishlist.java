package com.example.smart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class wishlist extends AppCompatActivity {

    private FloatingActionButton fbtn;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    WishlistAdapter toDoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        ref = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid().toString();

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fbtn = findViewById(R.id.fab);


        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(wishlist.this);
                final EditText editText = new EditText(wishlist.this);
                FrameLayout container = new FrameLayout(wishlist.this);
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                editText.setLayoutParams(params);
                container.addView(editText);
                editText.setHint("e.g. paneer");
                builder.setTitle("Wishlist Item");
                builder.setView(container);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Item item = new Item();
                        item.text=editText.getText().toString();
                        item.done=false;
                        item.UID = ref.child("Users/"+uid+"/wishlist/").push().getKey();

                        ref.child("Users/"+uid+"/wishlist/"+item.UID).setValue(item);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Item saved",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();
            }
        });

        FirebaseRecyclerOptions<Item> options =
                new FirebaseRecyclerOptions.Builder<Item>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users/"+uid+"/wishlist/"), Item.class)
                        .build();

        toDoAdapter = new WishlistAdapter(options);
        recyclerView.setAdapter(toDoAdapter);

    }




    @Override
    protected void onStart(){
        super.onStart();
        toDoAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        toDoAdapter.stopListening();
    }


}