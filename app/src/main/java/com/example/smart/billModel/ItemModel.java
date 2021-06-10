package com.example.smart.billModel;

public class ItemModel {
    String Name;
    long Price,Quantity;

    public ItemModel(String name, long price, long quantity) {
        Name = name;
        Price = price;
        Quantity = quantity;
    }

    public ItemModel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }

    public long getQuantity() {
        return Quantity;
    }

    public void setQuantity(long quantity) {
        Quantity = quantity;
    }
}

