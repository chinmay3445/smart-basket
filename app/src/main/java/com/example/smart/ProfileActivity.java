package com.example.smart;

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
import com.google.zxing.integration.android.IntentIntegrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference ref;
    private String userId;

    private TextView nameTextView,emailTextView, mobileNumberTextView,tv3,tv4;

    private Button signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();
        userId = user.getUid();

        nameTextView =(TextView) findViewById(R.id.userpname);
        emailTextView =(TextView) findViewById(R.id.userpmail);
        mobileNumberTextView =(TextView) findViewById(R.id.userpmob);
        tv3=(TextView) findViewById(R.id.tv3);
        tv4=(TextView) findViewById(R.id.tv4);

        ref.child("Users/"+userId.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String name = snapshot.child("name").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String mobile = snapshot.child("number").getValue().toString();

                    nameTextView.setText(name);
                    emailTextView.setText(email);
                    mobileNumberTextView.setText(mobile);
                    tv3.setText(name);
                    tv4.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        ref.child("Users/"+userId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                com.example.loginregistration.User userProfile = snapshot.getValue(com.example.loginregistration.User.class);
//                if(userProfile != null) {
//                    String name = userProfile.getName();
//                    String email = userProfile.getEmail();
//                    String mobile = userProfile.getNumber();
//
//                    nameTextView.setText(name);
//                    emailTextView.setText(email);
//                    mobileNumberTextView.setText(mobile);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(),"Something went wrong..!",Toast.LENGTH_LONG).show();
//            }
//        });

    }

    public void homeButton(View view) {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    public void signoutButton(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),logIn_Activity.class));
        finish();
    }

}
