package com.example.smartcart;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;


// this class represents an item
public class item  implements Parcelable {
    String name, category;
    double price;
    private int myQuantity=0, availableQuantity;
    ArrayList<item> items;



    public item(String name, double price){
        this.name = name;
        this.price = price;
    }

    public item(String name, String category, double price, int my_Quantity, int availableQuantity){
        this(name, price);
        this.availableQuantity = availableQuantity;
        this.myQuantity = my_Quantity;
        this.category = category;
    }

    public item(ArrayList<item> items){
        this.items = items;
    }

    protected item(Parcel in) {
        name = in.readString();
        category = in.readString();
        price = in.readDouble();
        myQuantity = in.readInt();
        availableQuantity = in.readInt();
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

    public double getPrice(){
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
        parcel.writeDouble(price);
        parcel.writeInt(myQuantity);
        parcel.writeInt(availableQuantity);
    }

    public String setPrice() {
        if (myQuantity <= 1) return "₪" + price;
        return "₪" + price + "x" + myQuantity + "=₪" + price*myQuantity;
    }

    public int getAvailableQuantity(){
        return availableQuantity;
    }

}
