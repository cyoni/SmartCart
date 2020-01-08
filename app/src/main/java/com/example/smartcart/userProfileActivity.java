package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class userProfileActivity extends AppCompatActivity {
    userBoard user;
    private EditText userName, email, address, acctype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        acctype = findViewById(R.id.accType);


        // set action bar:
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        View view =getSupportActionBar().getCustomView();
        ImageView img = view.findViewById(R.id.image_action);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Gson gson = new Gson(); // get user object
        user = gson.fromJson(getIntent().getStringExtra("userMetaData"), userBoard.class);

        // set data to fields


        email.setEnabled(false);
        acctype.setEnabled(false);

        userName.setText(user.getName());
        email.setText(user.getEmail());
        address.setText(user.getAddress());

        if (!user.isManager())
            acctype.setText("CUSTOMER");
        else
            acctype.setText("MANAGER");

    }

    public void update(View view) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> User = new HashMap<>();
        User.put("name", userName.getText().toString());
        User.put("address", address.getText().toString());
        User.put("email", email.getText().toString());
        User.put("accountType", user.isManager() ? "1" : "0");

        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(User).addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {
                controller.toast(getApplicationContext(), "Ok!");
                userBoard _user = user;
                _user.setName(userName.getText().toString());
                _user.setAddress(address.getText().toString());


                Gson gson = new Gson();
                String metaData = gson.toJson(_user);
                Intent a = new Intent(getApplicationContext() , MainActivity.class);
                a.putExtra("userMetaData", metaData);
                startActivity(a);
                finish();
            }
        }); // Post data to the fire-base

    }
}





