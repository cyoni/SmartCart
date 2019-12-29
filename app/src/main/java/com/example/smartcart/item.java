package com.example.smartcart;

// this class represents an item
public class item {
    String name;
    int price, q;

    public item(String name, int price, int q){
        this.name = name;
        this.q = q;
        this.price = price;
    }

    public int getq(){
        return q;
    }

    public int getPrice(){
        return price;
    }

    public String getName(){
        return name;
    }

    public item increase(){
        q++;
        return  this;
    }
    public item decrease(){
        q--;
        return this;
    }
}
