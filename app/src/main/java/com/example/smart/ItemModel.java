package com.example.smart;

public class ItemModel {
    String Name;
    Integer Price,Quantity;

    public ItemModel(String name, Integer price, Integer quantity) {
        Name = name;
        Price = price;
        Quantity = quantity;
    }

    public  ItemModel() {}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }
}
