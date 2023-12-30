package com.example.shop;

import java.util.ArrayList;

public class Order {

    private int orderId;
    private ArrayList<Product> productDetails;
    private  String purchaseDate;

    private float totalPrice ;

    public Order(int orderId, ArrayList<Product> productDetails, String purchaseDate) {

        this.orderId = orderId;
        this.productDetails = productDetails;
        this.purchaseDate = purchaseDate;

    }

    public Order() {

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public ArrayList<Product> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ArrayList<Product> productDetails) {
        this.productDetails = productDetails;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", productDetails=" + productDetails +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
