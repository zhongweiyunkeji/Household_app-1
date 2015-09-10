package com.cdhxqh.household_app.model;

import java.io.Serializable;

/**
 * Created by hexian on 2015/9/10.
 */
public class SaceConfig implements Serializable {

    int acid; // 配置主键信息

    int caid;  // 设备id

    int uid; // 用户id

    String uids; // 接收人

    int help;  // 开启协助模式(1: 开启,  0:关闭)

    int feedback; // 是否需要协助(1: 开启,  0:关闭)

    String helpaddress;

    public SaceConfig(){}

    public SaceConfig(int acid, int caid, int feedback, int help, int uid, String uids, String helpaddress) {
        this.acid = acid;
        this.caid = caid;
        this.feedback = feedback;
        this.help = help;
        this.uid = uid;
        this.uids = uids;
        this.helpaddress = helpaddress;
    }

    public int getAcid() {
        return acid;
    }

    public void setAcid(int acid) {
        this.acid = acid;
    }

    public int getCaid() {
        return caid;
    }

    public void setCaid(int caid) {
        this.caid = caid;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public int getHelp() {
        return help;
    }

    public void setHelp(int help) {
        this.help = help;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUids() {
        return uids;
    }

    public void setUids(String uids) {
        this.uids = uids;
    }

    public String getHelpaddress() {
        return helpaddress;
    }

    public void setHelpaddress(String helpaddress) {
        this.helpaddress = helpaddress;
    }
}
