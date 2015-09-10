package com.cdhxqh.household_app.model;

import java.io.Serializable;

/**
 * Created by think on 2015/8/15.
 */
public class MyDevice implements Serializable {

    // 用户id(自己平台用于唯一表示用户)
    private String uid;

    // 是否公共设备
    private boolean ispublic;

    // 是否分享
    private boolean isShared;

    // 图片是否已加密(萤石平台返回的数据)
    private boolean isEncrypt;

    // 设备通道id
    private String cameraId;

    // 设备名称
    private String deviceName;

    // 是否打开检查开关(1:打开, 0:关闭)
    private int defence;

    // 设备序列号（萤石平台）
    private String deviceSerial;

    // 设备ID（自己平台唯一标识）
    private int ca_id;

    // 设备图片封面
    private String picUrl;

    private String deviceId;

    // 通道编号
    private int cameraNo;

    // 通道名称
    private String cameraName;

    // 设备状态(1：在线， 0：离线)
    private boolean status;

    public MyDevice(){

    }

    public MyDevice(int ca_id, String cameraId, int cameraNo, int defence, String deviceName, String deviceSerial, boolean isEncrypt, boolean ispublic, boolean isShared, String picUrl, boolean status, String uid, String deviceId, String cameraName) {
        this.ca_id = ca_id;
        this.cameraId = cameraId;
        this.cameraNo = cameraNo;
        this.defence = defence;
        this.deviceName = deviceName;
        this.deviceSerial = deviceSerial;
        this.isEncrypt = isEncrypt;
        this.ispublic = ispublic;
        this.isShared = isShared;
        this.picUrl = picUrl;
        this.status = status;
        this.uid = uid;
        this.deviceId = deviceId;
        this.cameraName = cameraName;
    }

    public int getCa_id() {
        return ca_id;
    }

    public void setCa_id(int ca_id) {
        this.ca_id = ca_id;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public int getCameraNo() {
        return cameraNo;
    }

    public void setCameraNo(int cameraNo) {
        this.cameraNo = cameraNo;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public boolean getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(boolean isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public boolean getIspublic() {
        return ispublic;
    }

    public void setIspublic(boolean ispublic) {
        this.ispublic = ispublic;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setIsShared(boolean isShared) {
        this.isShared = isShared;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public boolean ispublic() {
        return ispublic;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }
}

