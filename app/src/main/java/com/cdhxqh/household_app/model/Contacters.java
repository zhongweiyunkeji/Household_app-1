package com.cdhxqh.household_app.model;

/**
 * Created by Administrator on 2015/8/10.
 */
public class Contacters {
    /**
     * 姓名
     */
    private String name;
    /**
     * 类别
     */
    private String type;
    /**
     * 电话
     */
    private String phone;

    /**
     * 状态
     */
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
