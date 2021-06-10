package com.example.smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

//    private PinView pinView;
//    private Button next;
//    private TextView topText;
//    private EditText userName, userPhone, userPass;
//    private ConstraintLayout first;

    private FirebaseAuth mAuth;
    private EditText editTextName, editTextEmail, editTextNumber, editTextPassword;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        topText = findViewById(R.id.topText);
////        next = findViewById(R.id.button);
////        userName = findViewById(R.id.username);
////        userPhone = findViewById(R.id.userPhone);
////        userPass = findViewById(R.id.userPassword);
////        first = findViewById(R.id.first_step);
////
////        next.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        registerBtn = findViewById(R.id.regiterBtn);
        editTextName = findViewById(R.id.Name);
        editTextEmail = findViewById(R.id.email);
        editTextNumber = findViewById(R.id.MobileNumber);
        editTextPassword = findViewById(R.id.password);

        registerBtn.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View view)
//    {
//
//        if (next.getText().equals("SIGN-UP"))
//        {
//            String name = userName.getText().toString();
//            String phone = userPhone.getText().toString();
//            String pass = userPass.getText().toString();
//
//            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pass))
//            {
//
//                Toast.makeText(MainActivity.this, "Successfully Registered !", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, DashboardActivity.class);
//                startActivity(intent);
//
//            }
//            else
//            {
//                Toast.makeText(MainActivity.this, "Please enter the details", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }

    @Override
    public void  onClick(View v) {
        switch (v.getId()){
            case R.id.regiterBtn:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String number = editTextNumber.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();


        if(name.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(number.isEmpty()) {
            editTextNumber.setError("Mobile number is required");
            editTextNumber.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.length()<6) {
            editTextPassword.setError("Minimum password length should be 6 character.");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name,email,number);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"User has been registered successfully !",Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(getApplicationContext(), logIn_Activity.class);
                                        startActivity(intent);
                                    }
                                    else  {
                                        Toast.makeText(getApplicationContext(),"Registration is failed. Try again!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                        } else  {
                            Toast.makeText(getApplicationContext(),"Try again!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}