package com.example.smartcart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class historyList extends AppCompatActivity implements recycleview_adapter_shopping.ItemClickListener {

    ArrayList<item> myCartFromPast;
    recycleview_adapter_shopping adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);




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

        myCartFromPast = new ArrayList<>();

        Intent a = getIntent();
        Bundle bundle = a.getExtras();

        if (bundle != null) {
            myCartFromPast = bundle.getParcelableArrayList("my_last_items");

            setRecycleView(myCartFromPast);
        }




    }


    private void setRecycleView(ArrayList<item> items_list)
    {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recycleview_adapter_shopping(this, items_list);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

      // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));


    }
    @Override
    public void onItemClick(View view, int position) {

    }
}
