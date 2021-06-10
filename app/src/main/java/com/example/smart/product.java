package com.example.smart;

public class product {

    String itemID;
    String itemName;

    Double price;
    Integer quantity;


    public String getItemID() { return itemID; }

    public void setItemID(String itemID) { this.itemID = itemID; }

    public String getItemName() { return itemName; }

    public void setItemName(String itemName) { this.itemName = itemName; }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public product(String itemID, String itemName, Double price, Integer quantity) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public product(String itemID) {
        this.itemID = itemID;
    }

    public product() {
    }
}
