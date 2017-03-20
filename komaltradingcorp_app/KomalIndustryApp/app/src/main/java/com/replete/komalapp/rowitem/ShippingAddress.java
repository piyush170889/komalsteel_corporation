package com.replete.komalapp.rowitem;

/**
 * Created by MR JOSHI on 28-Mar-16.
 */
public class ShippingAddress {
    private int shippingAddressId;
    private String pincode;
    private String address;
    private String city;
    private String state;
    private String mark;
    private String destination;
    private String transporterName;
    private String vatTinNo;

    public ShippingAddress(int shippingAddressId, String pincode, String address, String city, String state, String mark, String destination, String transporterName, String vatTinNo) {
        this.shippingAddressId = shippingAddressId;
        this.pincode = pincode;
        this.address = address;
        this.city = city;
        this.state = state;
        this.mark = mark;
        this.destination = destination;
        this.transporterName = transporterName;
        this.vatTinNo = vatTinNo;
    }


    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public int getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public String getVatTinNo() {
        return vatTinNo;
    }

    public void setVatTinNo(String vatTinNo) {
        this.vatTinNo = vatTinNo;
    }
}
