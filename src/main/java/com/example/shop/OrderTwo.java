package com.example.shop;

public class OrderTwo {

    private String orderId;
    private  String purchaseDate;

    public OrderTwo(String orderId, String purchaseDate) {

        this.orderId = orderId;
        this.purchaseDate = purchaseDate;

    }

    public OrderTwo() {

    }

    public String getOrderId(String orderId) {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }


    @Override
    public String toString() {
        return "Order{" +
                "order=" + orderId +
                ", purchaseDate='" + purchaseDate + '\'' +
                '}';
    }
}
