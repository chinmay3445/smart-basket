package com.example.smart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

public class AdapterDeal extends FirebaseRecyclerAdapter <Model,AdapterDeal.myviewholder>{


    private DatabaseReference ref,mdatabase;
    private FirebaseAuth mAuth;
    String itemname;
    Double itemprice;

    public AdapterDeal(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Model model)
    {
        ref = FirebaseDatabase.getInstance().getReference();
        mdatabase = FirebaseDatabase.getInstance().getReference();

        mAuth= FirebaseAuth.getInstance();
        String curUser = mAuth.getCurrentUser().getUid().toString();
        String date = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()));

        product product = new product(model.getItemid());

        mdatabase.child("todaysDeals").child(model.getItemid().toString()).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 itemname = snapshot.child("itemname").getValue().toString();
                 itemprice = Double.valueOf(snapshot.child("salePrice").getValue().toString());

                 product.setItemName(itemname);
                 product.setPrice(itemprice);

                 ///////

                 holder.name.setText(product.getItemName());
                 holder.price.setText(String.valueOf(model.getPrice()));
                 holder.saleprice.setText(String.valueOf(itemprice));
                 Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

                 //plus
                 holder.addtocart.setOnClickListener(new View.OnClickListener() {


                     cart user = new cart();

                     //Map<String,Object> map = new HashMap<>();
                     @Override
                     public void onClick(View v) {

                         user.setItemName(product.getItemName());
                         //user.setPrice(Double.valueOf(model.getSaleprice()));
                         user.setPrice(product.getPrice());
                         user.setItemID(String.valueOf(model.getItemid()));
                         user.setQuantity(1);
                         user.setPurl(model.getPurl());

//                ref.child("cart/"+curUser+"/"+date+"/")
//                        .child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        int t = Integer.valueOf( snapshot.child("quantity").getValue().toString());
//                        int q =t+1;
//                        map.put("quantity",q);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

                         ref.child("cart/" + curUser + "/" + date + "/" + model.getItemid().toString() + "/")
                                 .setValue(user);
                     }

                 });
                 ///////

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });


        //minus
//        holder.minus.setOnClickListener(new View.OnClickListener() {
//            Map<String,Object> map = new HashMap<>();
//            @Override
//            public void onClick(View v) {
//                ref.child("cart/"+curUser+"/"+date+"/")
//                        .child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        int t = Integer.valueOf( snapshot.child("quantity").getValue().toString());
//                        int q =t-1;
//                        map.put("quantity",q);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
////                int q = (int) map.get("quantity");
////                if( q==1){
//
//
//
////                    ref.child("cart/"+curUser+"/"+date+"/")
////                            .child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot snapshot) {
////                            ref = FirebaseDatabase.getInstance().getReference()
////                                    .child("cart/"+curUser+"/"+date+"/").child(getRef(position).getKey());
////                            ref.removeValue();
//////                            for(DataSnapshot appleSnapshot: snapshot.getChildren()) {
//////                                appleSnapshot.getRef().removeValue();
//////                            }
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError error) {
////
////                        }
////                    });
////                }else if(1 < q){
////                q--;
////                 map.put("quantity",q);
//                ref.child("cart/"+curUser+"/"+date+"/")
//                        .child(getRef(position).getKey()).updateChildren(map);
//                //}
//            }
//        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_deal,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView name,price,saleprice;
        CircleImageView img;
        Button addtocart;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            img=(CircleImageView)itemView.findViewById(R.id.img1);
            price=(TextView)itemView.findViewById(R.id.price);
            saleprice=(TextView)itemView.findViewById(R.id.salePriceDeal);

            addtocart = itemView.findViewById(R.id.addToCart);

            //minus.setEnabled(false);
        }
    }
}
