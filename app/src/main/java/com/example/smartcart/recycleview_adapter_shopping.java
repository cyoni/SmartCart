package com.example.smartcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class recycleview_adapter_shopping extends RecyclerView.Adapter<recycleview_adapter_shopping.ViewHolder> {

    private ArrayList<item> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    recycleview_adapter_shopping(Context context, ArrayList<item> data) {
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
        int price = mData.get(position).getPrice();
        int my_q = mData.get(position).getMyQuantity();

        holder.myTextView.setText(name);
        holder.price.setText("₪" + price);
        holder.q.setText(my_q+"");

    }

    // total number of rows
    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    public ArrayList<item> getItems(HashMap<String, item> my_list) {

        ArrayList<item> l = new ArrayList<>();
        for (int i=0; i< getItemCount(); i++){
            item tmp = getItem(i);

            if (tmp.getMyQuantity() == 0 && my_list.get(tmp.getName()) == null) continue;
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
            b2.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            if (view.getId() == b1.getId()) {
                //  Toast.makeText(view.getContext(), "val: " + q.getText() + " +ITEM PRESSED = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                int n = Integer.parseInt(q.getText().toString());
                n++;
                q.setText(n + "");
                mData.set(getAdapterPosition(), mData.get(getAdapterPosition()).increase());

            }
            if (view.getId() == b2.getId()) {
                //  Toast.makeText(view.getContext(), "val: " + q.getText() + " +ITEM PRESSED = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                int n = Integer.parseInt(q.getText() + "");
                if (n == 0) return;
                n--;
                q.setText(n + "");

                mData.set(getAdapterPosition(), mData.get(getAdapterPosition()).decrease());

            }

        }
    }

    // convenience method for getting data at click position
    public item getItem(int id) {
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
