package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userProfileActivity extends AppCompatActivity {
    userBoard user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

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
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseAuth mAuth  = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user  = dataSnapshot.getValue(userBoard.class);

                        TextView userName = findViewById(R.id.name);
                        TextView email = findViewById(R.id.email);
                        TextView address = findViewById(R.id.address);
                        TextView acctype = findViewById(R.id.accType);


                       userName.setText(user.getName());
                        email.setText(user.getEmail());
                        address.setText(user.getAddress());

                        if (user.getAccountType().equals("0"))
                            acctype.setText("CUSTOMER");
                            else
                            acctype.setText("MANAGER");

                        TableRow t1 = findViewById(R.id.t1);
                        t1.setVisibility(View.VISIBLE);
                        TableRow t2 = findViewById(R.id.t2);
                        t2.setVisibility(View.VISIBLE);
                        TableRow t3 = findViewById(R.id.t3);
                        t3.setVisibility(View.VISIBLE);
                        TableRow t4 = findViewById(R.id.t4);
                        t4.setVisibility(View.VISIBLE);


                        ProgressBar p = findViewById(R.id.progressBar1);
                        p.setVisibility(View.GONE);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                }
        );


    }



}
