package com.example.smart;

public class Model {


    String itemName;
    String purl;

    Double price;
    Integer quantity;


    String itemID;
    Double saleprice;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getName() {
        return itemName;
    }

    public void setName(String name) {
        this.itemName = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSaleprice() { return saleprice; }

    public void setSaleprice(Double saleprice) { this.saleprice = saleprice; }


    public String getItemid() { return itemID; }

    public void setItemid(String itemid) { this.itemID = itemid; }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPurl() { return purl; }

    public void setPurl(String purl) { this.purl = purl; }

    public Model(String name, String purl, Double price, Integer quantity) {
        this.itemName = name;
        this.purl=purl;
        this.price = price;
        this.quantity = quantity;
    }

    public Model(String itemName, String purl, Double price, Double saleprice) {
        this.itemName = itemName;
        this.purl = purl;
        this.price = price;
        this.saleprice = saleprice;
    }

    public Model(String itemName, String purl, Double price, String itemid, Double saleprice) {
        this.itemName = itemName;
        this.purl = purl;
        this.price = price;
        this.itemID = itemid;
        this.saleprice = saleprice;
    }

    public Model() {

    }
}
