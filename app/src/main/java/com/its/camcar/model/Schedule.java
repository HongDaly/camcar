package com.its.camcar.model;

public class Schedule {
    private String id;
    private String price;
    private String startTime;
    private String arrivedTime;
    private String startProvince;
    private String startDistrict;
    private String startCommune;
    private String startExactLocation;
    private String arrivedProvince;
    private String arrivedDistrict;
    private String arrivedCommune;
    private String arrivedExactLocation;
    private String userId;

    public Schedule(String price, String startTime, String arrivedTime, String startProvince, String startDistrict, String startCommune, String startExactLocation, String arrivedProvince, String arrivedDistrict, String arrivedCommune, String arrivedExactLocation,String userId) {

        this.price = price;
        this.startTime = startTime;
        this.arrivedTime = arrivedTime;
        this.startProvince = startProvince;
        this.startDistrict = startDistrict;
        this.startCommune = startCommune;
        this.startExactLocation = startExactLocation;
        this.arrivedProvince = arrivedProvince;
        this.arrivedDistrict = arrivedDistrict;
        this.arrivedCommune = arrivedCommune;
        this.arrivedExactLocation = arrivedExactLocation;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getArrivedTime() {
        return arrivedTime;
    }

    public String getStartProvince() {
        return startProvince;
    }

    public String getStartDistrict() {
        return startDistrict;
    }

    public String getStartCommune() {
        return startCommune;
    }

    public String getStartExactLocation() {
        return startExactLocation;
    }

    public String getArrivedProvince() {
        return arrivedProvince;
    }

    public String getArrivedDistrict() {
        return arrivedDistrict;
    }

    public String getArrivedCommune() {
        return arrivedCommune;
    }

    public String getArrivedExactLocation() {
        return arrivedExactLocation;
    }
}
