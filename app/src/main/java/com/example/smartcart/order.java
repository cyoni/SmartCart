package com.example.smartcart;

import java.util.ArrayList;

public class order {
    private int number;
    private double totalPrice;
    private ArrayList<item> items;
    private String date , address, userId;


    public order(int number, String userId, double totalPrice, String date, String address, ArrayList<item> items){
        this.number = number;
        this.items = items;
        this.date = date;
        this.address = address;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

    public ArrayList<item>  getItems() {
    return items;
    }

    public int getOrderId(){return number;}

    public String getId(){
        return userId;
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

    public double getPrice() {
        return totalPrice;
    }
}
