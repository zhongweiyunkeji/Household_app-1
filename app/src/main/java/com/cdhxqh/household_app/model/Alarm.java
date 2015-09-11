package com.cdhxqh.household_app.model;

import java.io.Serializable;

/**
 * Created by hexian on 2015/8/17.
 */
public class Alarm implements Serializable {

    private String img;   // 左侧报警图片
    private String title; // 报警信息
    private String msg;   // 家庭住址
    private String date;  // 报警事件
    private int icon;    // 协助状态
    private int payVideoRes;  // 转到设备实时预览按钮
    private boolean status = false;  // 保存复选框的选中状态
    private boolean back;
    private boolean encryption;
    private String serial;
    private String checkSum;
    private int caid;  // 自己平台设备id
    private int uid;   // 自己平台用户id

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public Alarm() {
    }

    public Alarm(String date, int icon, String img, String msg, String title) {
        this.date = date;
        this.icon = icon;
        this.img = img;
        this.msg = msg;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isEncryption() {
        return encryption;
    }

    public void setEncryption(boolean encryption) {
        this.encryption = encryption;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public int getPayVideoRes() {
        return payVideoRes;
    }

    public void setPayVideoRes(int payVideoRes) {
        this.payVideoRes = payVideoRes;
    }

    public int getCaid() {
        return caid;
    }

    public void setCaid(int caid) {
        this.caid = caid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
