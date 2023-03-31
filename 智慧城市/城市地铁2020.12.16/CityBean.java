package com.example.atest;

public class CityBean {
    private String city_lineId;
    private String city_lineName;
    private String city_nextStep;
    private String city_reachTime;

    public CityBean(String city_lineId,String city_lineName, String city_nextStep, String city_reachTime) {
        this.city_lineId = city_lineId;
        this.city_lineName = city_lineName;
        this.city_nextStep = city_nextStep;
        this.city_reachTime = city_reachTime;
    }

    public void setCity_lineId(String city_lineId) {
        this.city_lineId = city_lineId;
    }

    public String getCity_lineId() {
        return city_lineId;
    }

    public void setCity_lineName(String city_lineName) {
        this.city_lineName = city_lineName;
    }

    public String getCity_lineName() {
        return city_lineName;
    }

    public void setCity_nextStep(String city_nextStep) {
        this.city_nextStep = city_nextStep;
    }

    public String getCity_nextStep() {
        return city_nextStep;
    }

    public void setCity_reachTime(String city_reachTime) {
        this.city_reachTime = city_reachTime;
    }

    public String getCity_reachTime() {
        return city_reachTime;
    }
}

