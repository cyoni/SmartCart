package com.example.smartcart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.HashMap;


public class LastShoppingActivity extends AppCompatActivity implements recycleview_adapter_order.ItemClickListener, recycleview_adapter_order.MyAdapterListener { // {
    private FirebaseAuth mAuth;
    private ArrayList<order> list;
    private recycleview_adapter_order adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_shopping);

        list = new ArrayList<>();
        getHistoryList();

    }

    private void getHistoryList() { // example: the child is supposed to be like this: apple,5,1;banana,1,2;orange,4,5;lemon,5,6. See user zSW3dnUfzGMlYhTuMWgVzy6cSlT2


        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("orders").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String output = "";

                if (dataSnapshot.exists()) {

                    for (DataSnapshot tmp : dataSnapshot.getChildren()) {

                        final HashMap<String, Object> dataMap = (HashMap<String, Object>) tmp.getValue();

                        String items = dataMap.get("items").toString();
                        String date = dataMap.get("date").toString();
                        String address = dataMap.get("address").toString();

                        ArrayList<item> tmp_items = new ArrayList<>();

                        if (items.indexOf(";") != -1) {
                            String my_items[] = items.split(";");
                            for (int i = 0; i < my_items.length; i++) {
                                String item[] = my_items[i].split(",");

                                item tmpItem = new item(item[1], item[0], Integer.parseInt(item[3]), Integer.parseInt(item[2]));
                                tmp_items.add(tmpItem);


                            }
                            list.add(new order(Integer.parseInt(tmp.getKey()), tmp_items));
                        }

                    }

                    setRecycleView();

                }
                else{
                    controller.toast(getApplicationContext(), "Your shopping history is empty");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
  //  controller.toast(this, adapter.getItem(position).getNumber()+".");

        //setRecycleView(adapter.getItem(position).getItems().size() +"");
    }

    @Override
    public void onContainerClick(ArrayList<order> items) {

    }

    private void setRecycleView() {
        // set up the RecyclerView

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recycleview_adapter_order(this, list, this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}

