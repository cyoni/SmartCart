package com.example.smartcart;

import java.util.ArrayList;

public class order {
    private int number;
    private ArrayList<item> items;
    private ArrayList<order> orders;


    public order(int number, ArrayList<item> items){
        this.number = number;
        this.items = items;
    }

    public ArrayList<item>  getItems() {
    return items;
    }

    public order(ArrayList<order> orders){
        this.orders = orders;
    }


    public int getSize(){
        return orders.size();
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
}
