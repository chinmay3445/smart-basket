package com.example.smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logIn_Activity extends AppCompatActivity implements View.OnClickListener
{

//    private Button next;
//    private TextView RegText;
//    private EditText userPhone, userPass;

    private TextView register,forgotPassword;
    private FirebaseAuth mAuth;
    private EditText  editTextEmail, editTextPassword;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_);

//        userPhone = findViewById(R.id.userPhone);
//        userPass = findViewById(R.id.userPassword);
//        next = findViewById(R.id.signIn_button);
//        RegText = findViewById(R.id.registerText);
//
//        next.setOnClickListener(this);
//        //RegText.setOnClickListener(this);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

        register = findViewById(R.id.registerText);
        register.setOnClickListener(this);

        login = findViewById(R.id.signIn_button);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerText :
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.signIn_button :
                loginUser();
                break;
            case  R.id.forgotPassword:
                startActivity(new Intent(getApplicationContext(), ForgotPass.class));
        }
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
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

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if(user.isEmailVerified()) {
                                Intent intent = new Intent(getApplicationContext() , DashboardActivity.class);
                                startActivity(intent);
                            }else {
                                user.sendEmailVerification();
                                Toast.makeText(getApplicationContext(), "Eamil link has been sent.Check your email to varify your account!", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Login is failed! Try again!",Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

//    @Override
//    public void onClick(View view)
//    {
//        if (next.getText().equals("SIGN-IN"))
//        {
//            String phone = userPhone.getText().toString();
//            String pass = userPass.getText().toString();
//
//            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pass))
//            {
//                Intent intent = new Intent(this, DashboardActivity.class);
//
//                Toast.makeText(logIn_Activity.this, "Logged In Successfully!", Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//
//            }
//            else
//            {
//                Toast.makeText(logIn_Activity.this, "Please enter the correct details", Toast.LENGTH_SHORT).show();
//            }
//        }

//        if (RegText.getText().equals("Click here to register !"))
//        {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }

}