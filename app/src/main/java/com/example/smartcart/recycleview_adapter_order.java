package com.example.smartcart;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class recycleview_adapter_order extends RecyclerView.Adapter<recycleview_adapter_order.ViewHolder> {


    private ArrayList<order> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
   public recycleview_adapter_order(Context context, ArrayList<order> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.order_row , parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int orderNum = mData.get(position).getNumber();
        String orderDate = mData.get(position).getDate();

        holder.myTextView.setText(orderNum+"");
        holder.price.setText("₪" +mData.get(position).getPrice());
        holder.address.setText(mData.get(position).getAddress());
        holder.date.setText(orderDate);
    }

    // total number of rows
    @Override
    public int getItemCount()
    {
        return mData.size();
    }



    public ArrayList<item> getItems() {
        ArrayList<item> l = new ArrayList<>();
/*        for (int i=0; i< getItemCount(); i++){
            item tmp = getItem(i);
            if (tmp.getMyQuantity() == 0) continue;
            l.add(tmp);
        }*/
        return l;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView price, address, date;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.orderId);
            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.price);
            address = itemView.findViewById(R.id.address);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
                   }
    }

    // convenience method for getting data at click position
    public order getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}