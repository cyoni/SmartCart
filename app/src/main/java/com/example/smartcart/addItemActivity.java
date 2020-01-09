package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.kusu.loadingbutton.LoadingButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class addItemActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Vector<String> cat_List;
    private Vector<String> items_List;
    private CheckBox del;
    private int sizeOfItems = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        cat_List = new Vector<>();
        items_List = new Vector<>();
        del = findViewById(R.id.delete);

        // set action bar:
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        View view = getSupportActionBar().getCustomView();
        ImageView img = view.findViewById(R.id.image_action);
        img.setImageResource(R.drawable.back_button);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();

        EditText search = findViewById(R.id.search);
        search.setVisibility(View.INVISIBLE);

        startTextListener();
        getCatList();
    }

    private void getItem(String cat, String str) {
        //   try{

        if (str.trim().length() == 0) return;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("items").child(cat.trim()).child(str.trim()).addListenerForSingleValueEvent(new ValueEventListener() { // items -> category -> item
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    final HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    String price = String.valueOf(dataMap.get("price"));
                    String quantity = String.valueOf(dataMap.get("quantity"));

                    TextView p = findViewById(R.id.price);
                    TextView q = findViewById(R.id.quantity);


                    p.setText(price);
                    q.setText(quantity);
                    del.setEnabled(true);
                } else {
                    del.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //   }
        //  catch (Exception e){}
    }

    private void LookForWords(String str) {
        try {
            if (str.trim().length() == 0) return;

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("items").child(str.trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        items_List.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            items_List.add(ds.getKey());
                        }
                        sizeOfItems = items_List.size();
                        setList(R.id.Item, items_List);
                        AutoCompleteTextView n = findViewById(R.id.Item);
                        n.requestFocus();
                        n.showDropDown();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        } catch (Exception e) {
        }
    }

    private void startTextListener() {

        final AutoCompleteTextView cat_txt = findViewById(R.id.cat);// listener for category
        final AutoCompleteTextView name_txt = findViewById(R.id.Item); // listener for product name

        cat_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cat_txt.getText().length() == 0) cat_txt.showDropDown();
            }
        });

        name_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name_txt.getText().length() == 0) name_txt.showDropDown();
            }
        });

        cat_txt.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                LookForWords(cat_txt.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        name_txt.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                getItem(cat_txt.getText().toString(), name_txt.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        });


    }


    private void setList(int v, Vector<String> list) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, list);
        AutoCompleteTextView textView = findViewById(v);

        textView.setAdapter(adapter);
        textView.showDropDown();
    }


    private void getCatList() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    cat_List.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String buttonName = ds.getKey();
                        cat_List.add(buttonName);
                    }
                    // update list:
                    setList(R.id.cat, cat_List);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void cancel(View view) {
        finish();
    }

    public void submit(View view) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


        EditText t_name = findViewById(R.id.Item);
        EditText t_price = findViewById(R.id.price);
        EditText t_quantity = findViewById(R.id.quantity);
        EditText t_cat = findViewById(R.id.cat);

        String cat = t_cat.getText().toString().trim();

        String name = t_name.getText().toString().trim();

        String price = t_price.getText().toString().trim();
        String quantity = t_quantity.getText().toString().trim();


        if (cat.length() == 0 || name.length() == 0 || price.length() == 0 || quantity.length() == 0) {
            controller.toast(this, "Please fill all values!");
            return;
        }
        cat = cat.toLowerCase();
        cat = cat.substring(0, 1).toUpperCase() + cat.substring(1);

        if (del.isChecked()) {
            mDatabase.child("items").child(cat).child(name).removeValue();
            if (sizeOfItems == 1){
                mDatabase.child("categories").child(cat).removeValue();
                sizeOfItems=0;}

            controller.toast(this, "success!");
        } else {


            Button submit = findViewById(R.id.submit);
            LoadingButton loadingButton = (LoadingButton) submit;
            loadingButton.showLoading();

            Map<String, Object> newItem = new HashMap<>();
            newItem.put("price", Double.valueOf(price));
            newItem.put("quantity", Integer.valueOf(quantity));

            name = name.toLowerCase();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);

            mDatabase.child("items").child(cat).child(name).setValue(newItem);
            mDatabase.child("categories").child(cat).setValue(cat);

            controller.toast(this, name + " has been updated!");
            getCatList();

            loadingButton = (LoadingButton) submit;
            loadingButton.hideLoading();
        }

        t_name.setText("");
        t_price.setText("");
        t_quantity.setText("");
        t_cat.setText("");
        del.setChecked(false);
        del.setEnabled(false);
    }

}
