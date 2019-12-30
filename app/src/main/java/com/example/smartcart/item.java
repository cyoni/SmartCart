package com.example.smartcart;

// this class represents an item
public class item {
    String name;
    int price, q// available quantity
            , myQuantity=0;

    public item(String name, int price, int q){
        this.name = name;
        this.q = q;
        this.price = price;
    }

    public int getMyQuantity(){
        return myQuantity;
    }

    public int getPrice(){
        return price;
    }

    public String getName(){
        return name;
    }

    public item increase(){
        myQuantity++;
        return  this;
    }

    public item decrease(){
        if (myQuantity > 0)
            myQuantity--;
        return this;
    }
}
