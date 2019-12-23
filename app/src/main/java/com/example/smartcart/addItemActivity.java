package com.example.smartcart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.kusu.loadingbutton.LoadingButton;

import java.util.HashMap;
import java.util.Map;

public class addItemActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

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

    }

    public void cancel(View view) {
        finish();
    }

    public void submit(View view) {

        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


        EditText t_name = findViewById(R.id.name);
        EditText t_price = findViewById(R.id.price);
        EditText t_quantity = findViewById(R.id.quantity);
        EditText t_cat = findViewById(R.id.cat);


        String cat = t_cat.getText().toString().trim();
        String name = t_name.getText().toString().trim();
        String price = t_price.getText().toString().trim();
        String quantity = t_quantity.getText().toString().trim();

        if (cat.length() == 0 || name.length() == 0 || price.length() == 0 || quantity.length() == 0){
            controller.toast(this, "Please fill all values!");
            return;
        }

        Button submit = findViewById(R.id.submit);
        LoadingButton loadingButton = (LoadingButton)submit; loadingButton.showLoading();

        Map<String, Object> newItem = new HashMap<>();
        newItem.put("price", Integer.valueOf(price));
        newItem.put("quantity",Integer.valueOf(quantity));

        mDatabase.child("items").child(cat).child(name).setValue(newItem);

        controller.toast(this, name + " has been added!");
        finish();

    }
}
