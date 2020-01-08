package com.example.smartcart;


import java.util.HashMap;

public class userBoard {
    private String accountType;
    private String name, email, address;
    private HashMap<String,String> hm=new HashMap<>();

    public userBoard(){}

    public userBoard(String accountType, String name, String email, String address){
        this.accountType = accountType;
        this.name = name;
        this.email =  email;
        this.address = address;
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



}
