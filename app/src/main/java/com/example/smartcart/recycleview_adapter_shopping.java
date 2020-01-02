package com.example.smartcart;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class recycleview_adapter_shopping extends RecyclerView.Adapter<recycleview_adapter_shopping.ViewHolder> {

    private int what_activity;
    private ArrayList<item> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    MyAdapterListener listener;

    // data is passed into the constructor
    public recycleview_adapter_shopping(Context context, ArrayList<item> data, int what_activity, MyAdapterListener listener) {
        this.what_activity = what_activity;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.listener = listener;
    }

    public recycleview_adapter_shopping(Context context, ArrayList<item> data, int what_activity) {
        this.what_activity = what_activity;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mData.get(position).getName();

        int my_q = mData.get(position).getMyQuantity();

        holder.myTextView.setText(name);
        holder.price.setText(mData.get(position).setPrice());
        holder.q.setText(my_q+"");


    }

    // total number of rows
    @Override
    public int getItemCount()
    {
        return mData.size();
    }


/*
    public ArrayList<item> getItems() {
        ArrayList<item> l = new ArrayList<>();
        for (int i=0; i< getItemCount(); i++){
            item tmp = getItem(i);
            if (tmp.getMyQuantity() == 0) continue;
            l.add(tmp);
        }
        return l;
    }
    */
    public ArrayList<item> getItems() {
        ArrayList<item> l = new ArrayList<>();
        for (int i=0; i< getItemCount(); i++){
            item tmp = getItem(i);
            if (tmp.getMyQuantity() == 0) continue;
            l.add(tmp);
        }
        return l;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        Button b1, b2;
        TextView price, q;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.item_name);
            b1 = itemView.findViewById(R.id.b1);
            b2 = itemView.findViewById(R.id.b2);
            q = itemView.findViewById(R.id.q);
            price = itemView.findViewById(R.id.price);


            b1.setOnClickListener(this);
            if (what_activity == 0) { // chooseItemsActivity
                b2.setOnClickListener(this);

            }
            else{ // cartActivity
                b1.setText("x");
                b1.setTextColor(Color.RED);
                b2.setVisibility(View.GONE);

            }
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            // if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            if (view.getId() == b1.getId()) {

                if (b1.getText().equals("x")) {
                    mData.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    listener.onContainerClick(getItems()); // invoke onContainerClick from cartActivity to update subtotal
                }
                else {

                    if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
                    int n = Integer.parseInt(q.getText().toString());
                    n++;
                    q.setText(n + "");

                    mData.set( getAdapterPosition() , mData.get(getAdapterPosition()).increase());
                    price.setText(mData.get(getAdapterPosition()).setPrice());
                }

            }
            if (view.getId() == b2.getId()) {
                //  Toast.makeText(view.getContext(), "val: " + q.getText() + " +ITEM PRESSED = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

                int n = Integer.parseInt(q.getText() + "");
                if (n == 0) return;
                n--;
                q.setText(n + "");

                mData.set( getAdapterPosition(), mData.get(getAdapterPosition()).decrease());
                price.setText(mData.get(getAdapterPosition()).setPrice());
            }

        }
    }

    // convenience method for getting data at click position
    public item getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface MyAdapterListener {
        void onContainerClick(ArrayList< item> items);
    }

}