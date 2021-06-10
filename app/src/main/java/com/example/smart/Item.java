package com.example.smart;

public class Item {

    public  String UID,text;
    public  boolean done;

    Item(){
    }

    public Item(String UID, String text, boolean done) {
        this.UID = UID;
        this.text = text;
        this.done = done;
    }
}