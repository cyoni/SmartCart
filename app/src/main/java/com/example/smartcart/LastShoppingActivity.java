package com.example.smartcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.smartcart.recycleview.recycleview_adapter_order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.HashMap;


public class LastShoppingActivity extends AppCompatActivity implements recycleview_adapter_order.ItemClickListener { // {
    private FirebaseAuth mAuth;
    private ArrayList<order> list;
    private recycleview_adapter_order adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_shopping);


        // set action bar:
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        View view = getSupportActionBar().getCustomView();
        ImageView img = view.findViewById(R.id.image_action);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        list = new ArrayList<>();
        getOrderList();

    }


    private void getOrderList() { // example: the child is supposed to be like this: apple,5,1;banana,1,2;orange,4,5;lemon,5,6. See user zSW3dnUfzGMlYhTuMWgVzy6cSlT2


        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("orders").child(mAuth.getUid()).orderByChild("date").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot tmp : dataSnapshot.getChildren()) {

                        final HashMap<String, Object> dataMap = (HashMap<String, Object>) tmp.getValue();

                        String items = dataMap.get("items").toString();
                        String date = dataMap.get("date").toString();
                        String address = dataMap.get("address").toString();

                        ArrayList<item> tmp_items = new ArrayList<>();

                        if (items.indexOf(";") != -1) {
                            String my_items[] = items.split(";");
                            int total = 0;
                            for (int i = 0; i < my_items.length; i++) {
                                String item[] = my_items[i].split(",");
                                total += Double.parseDouble(item[4]);
                                item tmpItem = new item(item[1], item[0], Double.parseDouble(item[3]), Integer.parseInt(item[2]) , 1000);
                                tmp_items.add(tmpItem);
                                                            }
                            list.add(new order(Integer.parseInt(tmp.getKey()), "", total ,date, address, tmp_items));
                        }

                    }
                    ArrayList<order> tmpList = new ArrayList<>(); // to get the opposite order
                    for (int i=0;i<list.size(); i++) tmpList.add(list.get(list.size()-1-i));

                    setRecycleView(tmpList);
                    ProgressBar p = findViewById(R.id.progressBar);
                    p.setVisibility(View.GONE);

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
        order o = adapter.getItem(position);


        for (int i=0; i< list.size(); i++){
            int orderNum = o.getNumber();
            if (list.get(i).getNumber() == orderNum){
                Intent a = new Intent(this, historyList.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("my_last_items", list.get(i).getItems());
                a.putExtras(bundle);
                startActivity(a);
                break;
            }
        }


    }

    private void setRecycleView(ArrayList<order> l) {
            RecyclerView recyclerView = findViewById(R.id.list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new recycleview_adapter_order(this,  l);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        }

}



