package com.example.smart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.widget.Toast.LENGTH_SHORT;


public class DashboardActivity extends AppCompatActivity {

    private DatabaseReference mDatabase,reff;
    private FirebaseAuth mAuth;
    FirebaseUser cur_user;

    public TextView textView,a,b;
    public Button cartButton;

//    boolean doubleBackToExitPressedOnce = false;
//
//    private long lastPressedTime;
//    private static final int PERIOD = 2000;

    private boolean backPressedOnce = false;
    private Handler statusUpdateHandler = new Handler();
    private Runnable statusUpdateRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mDatabase=FirebaseDatabase.getInstance().getReference();
        reff=FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();

        cur_user= mAuth.getCurrentUser();

//        textView = findViewById(R.id.scanResult);
//        cartButton = findViewById(R.id.cartButton);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser mfirebaseuser = mAuth.getCurrentUser();
        if(mfirebaseuser!=null)
        {
            //some user is logged in
            //Toast.makeText(getApplicationContext(),"Welcome "+mfirebaseuser.getEmail()+" !",LENGTH_SHORT).show();
        }
        else
        {
            //not logged in
            startActivity(new Intent(this,logIn_Activity.class));
            finish();
        }
    }

    private void addToCart(String itemID) {

        cart user = new cart(itemID);
        user.setItemID(itemID);

        //To fetch from products directory
        product product = new product(itemID);
        //String iname= mDatabase.child("product/").child("itemID").getKey();



        reff.child("product").child(itemID.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String itemname = snapshot.child("itemname").getValue().toString();
                Double itemprice = Double.valueOf(snapshot.child("price").getValue().toString());
                String purl = snapshot.child("purl").getValue().toString();
                //Integer p=Integer.valueOf(snapshot.child("itemprice").getValue().toString());
                String date = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()));

                product.setItemName(itemname);
                product.setPrice(itemprice);
//                Toast.makeText(getApplicationContext(),product.getItemName(), LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),product.getPrice().toString(), LENGTH_SHORT).show();

                user.setItemName(product.getItemName());
                user.setPrice(product.getPrice());
                user.setPurl(purl);
                user.setQuantity(1);
                mDatabase.child("cart/"+cur_user.getUid()+"/"+date+"/"+itemID.toString()).setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        user.setItemName(product.getItemName());
//        user.setPrice(product.getPrice());
        //Toast.makeText(this,product.getItemName(), LENGTH_SHORT).show();
        //mDatabase.child("cart/").child("items").push().setValue(user);
    }

    public void ScanButton(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    public void cartPage(View view)
    {
        Intent intent = new Intent(this, cartPage.class);
        startActivity(intent);
    }

    public void PaymentButton(View view) {
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }


    public void wishlistButton(View view) {
        Intent intent = new Intent(this, wishlist.class);
        startActivity(intent);
    }

    public void billButton(View view) {
        Intent intent = new Intent(this,billMain.class);
       // Log.d("TAG", "billButton: ");
        startActivity(intent);
    }

    public void profileButton(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        //Intent intent = new Intent(this, profile.class);
        startActivity(intent);
    }

    public void todaysDealPage(View view)
    {
        Intent intent = new Intent(this, todaysDeal.class);
        startActivity(intent);
    }

    public void setCartButton(View view)
    {
        Toast.makeText(this,"Item Added To Cart", LENGTH_SHORT).show();

        //Testing
//
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                cart cart = dataSnapshot.getValue(cart.class);
//                // ...
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        };
//        mDatabase.child("product/").child(textView.getText().toString()).addListenerForSingleValueEvent(postListener);
//        cart cart1 = new cart();
//        Toast.makeText(this,cart1.getItemName().toString(),Toast.LENGTH_SHORT).show();
//        // ...

//        addToCart(textView.getText().toString());
//        textView.setText("Scan Result");
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {



            if (intentResult.getContents() == null) {
                //textView.setText("Cancelled");
            } else {
//                textView.setText(intentResult.getContents());
//                cartButton.setVisibility(View.VISIBLE);

                addToCart(intentResult.getContents());
            }




        }
        super.onActivityResult(requestCode, resultCode, data);
//        Intent intent = new Intent(this, scanResult.class);
//        startActivity(intent);
    }

//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            finish();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            switch (event.getAction()) {
//                case KeyEvent.ACTION_DOWN:
//                    if (event.getDownTime() - lastPressedTime < PERIOD) {
//                        finish();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Press again to exit.",
//                                Toast.LENGTH_SHORT).show();
//                        lastPressedTime = event.getEventTime();
//                    }
//                    return true;
//            }
//        }
//        return false;
//    }

    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            finish();
        }

        backPressedOnce = true;
        final Toast toast = Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT);
        toast.show();

        statusUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                backPressedOnce = false;
                toast.cancel();  //Removes the toast after the exit.
            }
        };

        statusUpdateHandler.postDelayed(statusUpdateRunnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (statusUpdateHandler != null) {
            statusUpdateHandler.removeCallbacks(statusUpdateRunnable);
        }
    }

}