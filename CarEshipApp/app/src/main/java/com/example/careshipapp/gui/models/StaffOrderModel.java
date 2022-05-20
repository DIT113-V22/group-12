package com.example.careshipapp.gui.models;

import java.io.Serializable;

public class StaffOrderModel implements Serializable {

        String orderID;
        String address;
        String contactNumber;
        String orderStatus;

    public StaffOrderModel() {
    }

    public StaffOrderModel(String orderID, String address, String contactNumber, String orderStatus) {
        this.orderID = orderID;
        this.address = address;
        this.contactNumber = contactNumber;
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
