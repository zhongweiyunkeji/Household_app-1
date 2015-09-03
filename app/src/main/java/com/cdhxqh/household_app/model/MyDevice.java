package com.cdhxqh.household_app.model;

import java.io.Serializable;

/**
 * Created by think on 2015/8/15.
 */
public class MyDevice implements Serializable{
    /**
     * 设备名称
     */
    private String name;
    /**
     * 角标数量
     */
    private int size;
    /**
     * 地点
     */
    private String place;
    /**
     * 编号
     */
    private String number;

    private boolean status;

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPlace() {
        return place;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
