package com.cdhxqh.household_app.model;

import android.content.Intent;

/**
 * Created by think on 2015/8/15.
 */
public class MyDevice {
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
}
