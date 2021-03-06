package com.example.smartcart.recycleview;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcart.R;
import com.example.smartcart.item;

import java.util.ArrayList;


public class recycleview_adapter_shopping extends RecyclerView.Adapter<recycleview_adapter_shopping.ViewHolder> {

    private ArrayList<item> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private String what;
    MyAdapterListener listener;

    // data is passed into the constructor
    public recycleview_adapter_shopping(Context context, ArrayList<item> data, MyAdapterListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.listener = listener;
        this.what = "";
    }

    public recycleview_adapter_shopping(Context context, ArrayList<item> data, String what) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.what = what;
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

        if (holder.myTextView.length() > 10) holder.myTextView.setTextSize(25);

      if (mData.get(position).getAvailableQuantity() <= 0){
            holder.myTextView.setPaintFlags(holder.myTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
          //  holder.myTextView.setTypeface(null, Typeface.ITALIC);
            holder.b1.setEnabled(false);
            holder.b2.setEnabled(false);
        }

        if (what.equals("SEARCH")){ holder.myTextView.setTextSize(25); holder.myTextView.setWidth(300);}


        setFadeAnimation(holder.myTextView);
        setFadeAnimation(holder.price);
        setFadeAnimation(holder.q);

        if (!what.equals("history")) {
            setFadeAnimation(holder.b1);
            setFadeAnimation(holder.b2);
        }

    }
       private void setFadeAnimation(View view) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(800);
            view.startAnimation(anim);
        }
    // total number of rows
    @Override
    public int getItemCount()
    {
        return mData.size();
    }


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

            if (what.equals("history")){
             b1.setVisibility(View.INVISIBLE);
             b2.setVisibility(View.INVISIBLE);
             q.setVisibility(View.INVISIBLE); // old quantity
             q = itemView.findViewById(R.id.qq); // new quantity - change location
             q.setVisibility(View.VISIBLE);
            }
            else {
                b1.setOnClickListener(this);
                b2.setOnClickListener(this);
            }
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            // if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            if (view.getId() == b1.getId()) {
                    int n = Integer.parseInt(q.getText().toString());
                    n++;
                    q.setText(n + "");
                    mData.set( getAdapterPosition() , mData.get(getAdapterPosition()).increase());
                    price.setText(mData.get(getAdapterPosition()).setPrice());
                    mClickListener.onItemClick(view, getAdapterPosition());

            }
            if (view.getId() == b2.getId()) {
                int n = Integer.parseInt(q.getText().toString());
                if (n == 0) return;
                n--;
                q.setText(n + "");
                mData.set( getAdapterPosition(), mData.get(getAdapterPosition()).decrease());
                price.setText(mData.get(getAdapterPosition()).setPrice());
                mClickListener.onItemClick(view, getAdapterPosition());
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