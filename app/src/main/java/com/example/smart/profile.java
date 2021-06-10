package com.example.smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference ref;
    private String userId;

    private TextView nameTextView,emailTextView, mobileNumberTextView;

    private Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();
        userId = user.getUid();

        nameTextView = findViewById(R.id.name);
        emailTextView = findViewById(R.id.email);
        mobileNumberTextView = findViewById(R.id.mobileNumber);


        signOut = findViewById(R.id.signOut);
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(),logIn_Activity.class));
//            }
//        });
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            }
//        });

        ref.child("Users/"+userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //User userProfile = snapshot.getValue(User.class);
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null) {
                    String name = userProfile.getName();
                    String email = userProfile.getEmail();
                    String mobile = userProfile.getNumber();

                    nameTextView.setText(name);
                    emailTextView.setText(email);
                    mobileNumberTextView.setText(mobile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Something went wrong..!",Toast.LENGTH_LONG).show();
            }
        });
    }
}