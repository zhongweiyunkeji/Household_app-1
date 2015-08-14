package com.cdhxqh.household_app.model;

import android.widget.TextView;

/**
 * Created by Administrator on 2015/8/14.
 */
public class ProductModel {
    /**
     * 名称
     */
    private String product_id;

    /**
     *位置
     */
    private String position_id;

    /**
     *监控时间
     */
    private String monitor_time_id;

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

    public String getMonitor_time_id() {
        return monitor_time_id;
    }

    public void setMonitor_time_id(String monitor_time_id) {
        this.monitor_time_id = monitor_time_id;
    }
}
