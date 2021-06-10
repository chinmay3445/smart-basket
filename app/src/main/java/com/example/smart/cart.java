package com.example.smart;

public class cart {
    String itemID;
    String itemName;



    Double price;
    Integer quantity;
    String purl;

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }


    public cart() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public cart(String itemID, String itemName, Double price, Integer quantity, String purl) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.purl = purl;
    }

    public cart(String itemID) {
        this.itemID = itemID;
    }
}
