package com.cdhxqh.household_app.model;

import java.io.Serializable;

/**
 * Created by hexian on 2015/8/17.
 */
public class Alarm implements Serializable {

    private String img;
    private String title;
    private String msg;
    private String date;
    private int icon;
    private boolean status = false;  // 保存复选框的选中状态
    private boolean back;

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
}
