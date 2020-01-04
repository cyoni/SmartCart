package com.example.smartcart.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcart.MainActivity;
import com.example.smartcart.R;
import com.example.smartcart.controller;
import com.example.smartcart.item;
import com.example.smartcart.recycleview_adapter_shopping;
import com.example.smartcart.shoppingActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static android.app.Activity.RESULT_OK;

public class search extends Dialog implements  View.OnClickListener, recycleview_adapter_shopping.ItemClickListener  {
    recycleview_adapter_shopping adapter;
    public Activity c;
    EditText s;
    public ArrayList<item> search_list;
    private HashMap<String, item> myCart;
    private ArrayList<String> cat_list;
    private String cat;

    public search(Activity a, ArrayList<item> myCart) {
        super(a);
        this.c = a;
        this.myCart = controller.convertToHashmap(myCart);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_search);
       s = findViewById(R.id.search);

        search_list = new ArrayList<>();

       getCategories();


    }

    private void getCategories() {

        // get category list:

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("categories").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 cat_list = new ArrayList<>();

                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String buttonName = ds.getKey();
                        cat_list.add(buttonName);
                    }
                    FindItems(s.getText().toString());
                    // create listener for search bar:
                       s.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            FindItems(editable.toString());
                        }
                    });


                }
                else
                    controller.toast(getContext(), "error 11675");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });
    }

    private String getCat(){
        return cat;
    }

    private void FindItems(String str) {
        final String find = str;
        search_list.clear();
        for (int i=0; i<cat_list.size(); i++){
            cat = cat_list.get(i);

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("items").child(cat).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String cat = getCat();

                    if (dataSnapshot.exists()) {


                        Iterator<DataSnapshot> a = dataSnapshot.getChildren().iterator();
                        DataSnapshot tmp;
                        while (a.hasNext()) {
                            tmp = a.next();
                            final HashMap<String, Object> dataMap = (HashMap<String, Object>) tmp.getValue();


                          //  String q = dataMap.get("quantity") + ""; // available quantity
                            String price = dataMap.get("price") + "";
                            String name = tmp.getKey() + "";

                            if (name.contains(find)){
                                item tmpItem = new item(name, cat, Integer.valueOf(price), 0);

                                // look for this item, if you have it already in myCart

                                if (myCart.get(tmpItem.getName()) != null) { search_list.add(myCart.get(tmpItem.getName()));}
                                else
                                    search_list.add(tmpItem);
                            }

                            setRecycleView();
                        }

                    } else
                        controller.toast(getContext(), "error 34536");

                }
                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError){

                    }


            });
        }

    }


    @Override
    public void onClick(View view) {

    }


    public void sendData(String data) {
        s = findViewById(R.id.search);
        s.setText(data);

        s.requestFocus();
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(s, InputMethodManager.SHOW_IMPLICIT);
    }

    private void setRecycleView()
    {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new recycleview_adapter_shopping(getContext(), search_list,""); // list is the user's cart
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        myCart = controller.convertToHashmap(adapter.getItems());
    }
    public int how(){
        return myCart.size();
    }

    public ArrayList<item> getItems() {
     return adapter.getItems();
    }
}
