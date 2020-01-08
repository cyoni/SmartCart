package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcart.dialog.manager_order;
import com.example.smartcart.recycleview.recycleview_adapter_shopping;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class orders_manager_Activity_content extends AppCompatActivity  implements recycleview_adapter_shopping.ItemClickListener {

    private ArrayList<item> myCartFromPast;
    private recycleview_adapter_shopping adapter;
    private manager_order Manager_order;
    private String userId, date, address;
    private int orderId;
    private double total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_orders_content);


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

        EditText search = findViewById(R.id.search);
        search.setVisibility(View.INVISIBLE);

        myCartFromPast = new ArrayList<>();

        Intent a = getIntent();
        Bundle bundle = a.getExtras();
        userId = a.getStringExtra("user_id");
        orderId = a.getIntExtra("order_id", 0);
        date = a.getStringExtra("user_date");
        address = a.getStringExtra("user_address");
        total = a.getDoubleExtra("user_total", 0);


        if (bundle != null) {
            myCartFromPast = bundle.getParcelableArrayList("user_items");
            setRecycleView(myCartFromPast);
        }
    }

    private void setRecycleView(ArrayList<item> items_list)
    {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recycleview_adapter_shopping(this, items_list, "history");
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(View view, int position) {

    }

    public void info(View view) {


        Manager_order = new manager_order(this);
        Manager_order.show();

        TextView total = Manager_order.findViewById(R.id.total);
        TextView address = Manager_order.findViewById(R.id.address);
        TextView orderId = Manager_order.findViewById(R.id.orderID);
        TextView date = Manager_order.findViewById(R.id.date);



        date.setText(this.date);
        address.setText(this.address);
        orderId.setText(this.orderId+"");
        date.setText(this.date);
        total.setText("₪" +this.total);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    final HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    String email = String.valueOf(dataMap.get("email"));
                    String name = String.valueOf(dataMap.get("name"));

                    TextView t_name = Manager_order.findViewById(R.id.name);
                    TextView t_email = Manager_order.findViewById(R.id.email);

                    t_name.setText(name);
                    t_email.setText(email);

                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
