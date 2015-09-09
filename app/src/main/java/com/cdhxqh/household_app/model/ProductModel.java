package com.cdhxqh.household_app.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/8/14.
 */
public class ProductModel implements Serializable{
    // 安装位置
    private String product_id;
    // 家庭住址
    private String position_id;
    // 设备序列号(萤石平台)
    private String deviceSerial;
    // 设备唯一标识(自己平台)
    private int caid;
    // 用户id(自己平台)
    private String uid;

    public ProductModel(){

    }

    public ProductModel(int caid, String deviceSerial, String position_id, String product_id, String uid) {
        this.caid = caid;
        this.deviceSerial = deviceSerial;
        this.position_id = position_id;
        this.product_id = product_id;
        this.uid = uid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPosition_id() {
        return position_id;
    }

    public void setPosition_id(String position_id) {
        this.position_id = position_id;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public int getCaid() {
        return caid;
    }

    public void setCaid(int caid) {
        this.caid = caid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
