package com.example.smartcart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.smartcart.recycleview.recycleview_adapter_shopping;

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

        EditText search = findViewById(R.id.search);
        search.setVisibility(View.INVISIBLE);

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
        adapter = new recycleview_adapter_shopping(this, items_list, "history");
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onItemClick(View view, int position) {

    }
}
