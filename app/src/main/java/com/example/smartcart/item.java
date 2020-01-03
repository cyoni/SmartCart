package com.example.smartcart;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;


// this class represents an item
public class item  implements Parcelable {
    String name, category;
    int price, q// available quantity
            , myQuantity=0;
    ArrayList<item> items;



    public item(String name, int price, int q){
        this.name = name;
        this.q = q;
        this.price = price;
    }

    public item(String name, String category, int price, int q){
        this(name, price, q);
        this.category = category;
    }

    public item(ArrayList<item> items){
        this.items = items;
    }

    protected item(Parcel in) {
        name = in.readString();
        category = in.readString();
        price = in.readInt();
        q = in.readInt();
        myQuantity = in.readInt();
    }

    public int getSize(){
        return items.size();
    }

    public static final Creator<item> CREATOR = new Creator<item>() {
        @Override
        public item createFromParcel(Parcel in) {
            return new item(in);
        }

        @Override
        public item[] newArray(int size) {
            return new item[size];
        }
    };

    public int getMyQuantity(){
        return myQuantity;
    }

    public int getPrice(){
        return price;
    }

    public String getName(){
        return name;
    }
    public String getCategory(){
        return category;
    }

    public item getItem(){
        return this;
    }

    public item increase(){
        myQuantity++;
        return  this;
    }

    public item decrease(){
        if (myQuantity == -1) myQuantity = 0;
        else if (myQuantity > 0) myQuantity--;
        else if (myQuantity == 0) myQuantity=-1; // remove the item from myCart
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeInt(price);
        parcel.writeInt(q);
        parcel.writeInt(myQuantity);
    }

    public String setPrice() {
        if (myQuantity <= 1) return "₪" + price;
        return "₪" + price + "x" + myQuantity + "=₪" + price*myQuantity;
    }

    public void resetMyQuantity() {
        myQuantity = 0;
    }
}
