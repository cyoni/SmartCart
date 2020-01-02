package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcart.dialog.confirmPurchase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartActivity extends AppCompatActivity implements recycleview_adapter_shopping.ItemClickListener, recycleview_adapter_shopping.MyAdapterListener { //
    recycleview_adapter_shopping adapter;

    ArrayList<item> list;
    userBoard _user;
    confirmPurchase purchase_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


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
        setView();


        ////
        EditText search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {        }

            @Override
            public void afterTextChanged(Editable editable) {
                filterList(editable.toString());
            }
        });


    }

    private void filterList(String what) {
        ArrayList<item> search_list = new ArrayList<>();
        if (what.trim().length() == 0) setRecycleView(list);
        else{
            for (int i=0; i<list.size(); i++){
                if (list.get(i).getName().contains(what)) search_list.add(list.get(i));
            }
            setRecycleView(search_list);
        }
    }

    @Override
    public void onBackPressed() {
        if (list.size() != 0) {
            Intent intent = new Intent(this, shoppingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("items", adapter.getItems());
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    private void setView()
    {
        list = new ArrayList<>();
        Intent intent = getIntent(); // get intent from shoppingActivity - to set the items that the user choose to the shopping list
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            list =  bundle.getParcelableArrayList("my_items");
        }
        if (list.size() == 0) {
            controller.toast(this, "Your cart is empty");
            Button buy = findViewById(R.id.buy);
            buy.setEnabled(false);
            return;
        }

        Gson gson = new Gson(); // set user Data
        _user = gson.fromJson(intent.getStringExtra("userMetaData"), userBoard.class);

        setRecycleView(list);
    }

    private void setRecycleView(ArrayList<item> l){
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recycleview_adapter_shopping(this, l, 1, this);
        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
        setSubTotal();
    }
    private int getSum(){

        int total = 0;
        for (int i=0; i< list.size(); i++){
            total += list.get(i).price*list.get(i).getMyQuantity();
        }
        return total;
    }
    private void setSubTotal(){

        TextView t = findViewById(R.id.subtotal);
        int total = getSum();
        t.setText("Subtotal: ₪" + total);

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public void buy(View view) {
        if (_user == null) {
            controller.toast(this, "Please log in first");

            Intent a = new Intent(this, LoginActivity.class);
            startActivity(a);

        }
        else{
        // open buy Dialog
            purchase_dialog = new confirmPurchase(this);
            purchase_dialog.show();
            purchase_dialog.setValues(_user.getName(), _user.getAddress(), "₪" +getSum());

        }

    }


    @Override
    public void onContainerClick(ArrayList<item> items) {
        list.clear();
        list.addAll(items);
        setSubTotal();
    }

    public void placeTheOrder(View view) {

        purchase_dialog.disableButtons();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        Map<String, Object> order = new HashMap<>();
        order.put("date", date);
        order.put("address", purchase_dialog.getAddress());
        order.put("items", getItems());
        final int number  = (int)(Math.random()*((1000000)));

         mDatabase.child("orders").child(mAuth.getUid()).child(number+"").setValue(order).addOnCompleteListener(new OnCompleteListener <Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            controller.toast(getApplicationContext(), "Order " + number + " is confirm. Thank you");
            purchase_dialog.cancel();
            Intent a = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(a);
            finish();
         }

         });


    }

    private String getItems() {
        String items = "";
        for (int i=0; i<list.size(); i++){
            item tmp = list.get(i);
            items += tmp.getCategory() + "," + tmp.getName() + "," + tmp.getMyQuantity() + "," + tmp.getPrice() + "," + tmp.getMyQuantity()*tmp.getPrice() + ";";
        }
        return items;
    }

    public void back(View view) {
        purchase_dialog.cancel();
    }
}