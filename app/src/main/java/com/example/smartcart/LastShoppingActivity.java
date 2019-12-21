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

import java.util.HashMap;

public class LastShoppingActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_shopping);

        getHistoryList();

    }

    private void getHistoryList() { // example: the child is supposed to be like this: apple,5,1;banana,1,2;orange,4,5;lemon,5,6. See user zSW3dnUfzGMlYhTuMWgVzy6cSlT2


        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String output = "";

                if (dataSnapshot.exists()) {
                    final HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    String data = String.valueOf(dataMap.get("lastPurchase"));
                    if (data.indexOf(";") != -1) {
                        String items[] = data.split(";");
                        for (int i=0; i<items.length; i++) {
                            String item[] = items[i].split(",");
                            output += "\nname: " + item[0] + ". quantity: " + item[1] + ". price: " + item[2];
                        }
                        TextView t = findViewById(R.id.list);
                        t.setText(output);

                    }
                    else{
                        controller.toast(getApplicationContext(), "Input is not ok");
                    }

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
//adding comment for commit purpose

    public void lastShop2(View view) {


    }

}

