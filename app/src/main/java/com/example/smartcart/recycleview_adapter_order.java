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
    MyAdapterListener listener;

    // data is passed into the constructor
    recycleview_adapter_order(Context context, ArrayList<order> data, MyAdapterListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.listener = listener;
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
        int orderNum = mData.get(position).getNumber();
      /*

        int my_q = mData.get(position).getMyQuantity();

        holder.myTextView.setText(name);
        holder.price.setText(mData.get(position).setPrice());
        holder.q.setText(my_q+"");
*/

      holder.myTextView.setText(orderNum+"");

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
        Button b1, b2;
        TextView price, q;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.item_name);
            b1 = itemView.findViewById(R.id.b1);
            b2 = itemView.findViewById(R.id.b2);
            q = itemView.findViewById(R.id.q);
            price = itemView.findViewById(R.id.price);

            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
            q.setVisibility(View.GONE);
            price.setVisibility(View.GONE);


            myTextView.setTextSize(20);


    /*

            b1.setOnClickListener(this);
            if (what_activity == 0) { // chooseItemsActivity
                b2.setOnClickListener(this);

            }
            else{ // cartActivity
                b1.setText("x");
                b1.setTextColor(Color.RED);
                b2.setVisibility(View.GONE);

            }*/
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
          //   if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());


           // System.out.println(mData.get(getAdapterPosition()).getItems().size()+ "##" );
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

    public interface MyAdapterListener {
        void onContainerClick(ArrayList<order> items);
    }

}