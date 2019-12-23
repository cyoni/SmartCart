package com.example.smartcart;


import java.util.HashMap;

public class userBoard {
    private String accountType;
    private String name, email, address;

    private String storeName, storeAddress;
    private int storeID;
    private HashMap<String,String> hm=new HashMap<>();


    public userBoard(String accountType, String name, String email, String address){
        this.accountType = accountType;
        this.name = name;
        this.email =  email;
        this.address = address;
    }

    public userBoard(String accountType, String name, String email, String address, String storeAddress, int storeID, String storeName){
        this(accountType, name, email, address);
        this.storeAddress = storeAddress;
        this.storeID = storeID;
        this.storeName = storeName;
    }

    public userBoard(userBoard u){
        this.name = u.name;
        this.email = u.email;
        this.address = u.address;
        this.hm = new HashMap<String,String>(u.hm);


    }


    // getters:

    public String getAccountType() {
        return accountType;
    }

    public boolean isManager(){
        if (accountType.equals("1")) return true;
        else
            return false;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    // manager:
    public String storeName() {
        return storeName;
    }

    public String storeAddress() {
        return storeAddress;
    }

    public int storeID() {
        return storeID;
    }

    public HashMap<String,String> getHashMap() { return hm;}


}
