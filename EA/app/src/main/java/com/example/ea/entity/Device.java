package com.example.ea.entity;

public class Device {
    String manufacture;
    String productName;
    String dateOfManufacture;
    int quantity;
    String timeStamp;

    public Device() {
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(String dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public Device(String manufacture, String productName, String dateOfManufactory) {
        this.manufacture = manufacture;
        this.productName = productName;
        this.dateOfManufacture = dateOfManufactory;
    }
}
