package com.its.camcar.model;

import android.util.Log;

public class Booking {

    private String id;
    private String driverId;
    private String customerId;
    private String scheduleId;
    private String customerPhone;
    private int numOfPerson;
    private String date;
    private long createdAt;

    public Booking(String id, String driverId, String customerId, String scheduleId, String customerPhone, int numOfPerson, String date, Long createdAt) {
        this.id = id;
        this.driverId = driverId;
        this.customerId = customerId;
        this.scheduleId = scheduleId;
        this.customerPhone = customerPhone;
        this.numOfPerson = numOfPerson;
        this.date = date;
        this.createdAt = createdAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public Booking() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getNumOfPerson() {
        return numOfPerson;
    }

    public void setNumOfPerson(int numOfPerson) {
        this.numOfPerson = numOfPerson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
