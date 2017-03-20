package com.replete.komalapp.rowitem;

import java.util.List;

/**
 * Created by MR JOSHI on 23-Mar-16.
 */
public class Orders {

    private String orderId;
    private String orderPlacedDateNTime;
    //    private String packagePrice;
    private String orderStatus;
    private String orderDeliveredOn;
    private List<OrderProducts> orderProductsList;
    private String orderAddress;
    private String orderCity;
    private String orderState;
    private String orderPinCode;
    private String orderCountry;
    private String orderLrNo;
    private String orderLrDate;
    private String orderNoOfCartonLoaded;


    public Orders() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderPlacedDateNTime() {
        return orderPlacedDateNTime;
    }

    public void setOrderPlacedDateNTime(String orderPlacedDateNTime) {
        this.orderPlacedDateNTime = orderPlacedDateNTime;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDeliveredOn() {
        return orderDeliveredOn;
    }

    public void setOrderDeliveredOn(String orderDeliveredOn) {
        this.orderDeliveredOn = orderDeliveredOn;
    }

    public List<OrderProducts> getOrderProductsList() {
        return orderProductsList;
    }

    public void setOrderProductsList(List<OrderProducts> orderProductsList) {
        this.orderProductsList = orderProductsList;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderCity() {
        return orderCity;
    }

    public void setOrderCity(String orderCity) {
        this.orderCity = orderCity;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderPinCode() {
        return orderPinCode;
    }

    public void setOrderPinCode(String orderPinCode) {
        this.orderPinCode = orderPinCode;
    }

    public String getOrderCountry() {
        return orderCountry;
    }

    public void setOrderCountry(String orderCountry) {
        this.orderCountry = orderCountry;
    }

    public String getOrderLrNo() {
        return orderLrNo;
    }

    public void setOrderLrNo(String orderLrNo) {
        this.orderLrNo = orderLrNo;
    }

    public String getOrderLrDate() {
        return orderLrDate;
    }

    public void setOrderLrDate(String orderLrDate) {
        this.orderLrDate = orderLrDate;
    }

    public String getOrderNoOfCartonLoaded() {
        return orderNoOfCartonLoaded;
    }

    public void setOrderNoOfCartonLoaded(String orderNoOfCartonLoaded) {
        this.orderNoOfCartonLoaded = orderNoOfCartonLoaded;
    }
}
