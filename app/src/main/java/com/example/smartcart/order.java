package com.example.smartcart;

import java.util.ArrayList;

public class order {
    private int number, totalPrice;
    private ArrayList<item> items;
    private String date , address;


    public order(int number, int totalPrice, String date, String address, ArrayList<item> items){
        this.number = number;
        this.items = items;
        this.date = date;
        this.address = address;
        this.totalPrice = totalPrice;
    }

    public ArrayList<item>  getItems() {
    return items;
    }



    public item getItem(int n) {
        if (items.size() > n)
            return items.get(n);
                else
                return null;
    }

    public int getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }
    public String getAddress() {
        return address;
    }

    public int getPrice() {
        return totalPrice;
    }
}
