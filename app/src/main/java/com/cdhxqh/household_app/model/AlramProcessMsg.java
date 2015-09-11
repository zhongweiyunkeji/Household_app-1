package com.cdhxqh.household_app.model;

import java.io.Serializable;

/**
 * Created by hexian on 2015/9/11.
 */
public class AlramProcessMsg implements Serializable {

    // 主键
    private int adid;
    // 报警记录主键
    private int alramid;
    // 报警信息
    private String description;
    // 设备id
    private int caid;
    // 用户id
    private int uid;
    // 报警事件
    private String alramtime;
    // 处理状态
    private String alrmstatus;
    // 是否已协助核查
    private int helpcheck;
    // 是否存在安全隐患
    private int hasdanger;
    // 是否已处理
    private int isprocess;
    // 处理情况说明
    private String processResult;
    // 处理人
    private String username;
    // 报警事件
    private String starttime;
    // 处理时间
    private String processtimeStr;

    public AlramProcessMsg(){ }

    public AlramProcessMsg(int adid, int alramid, String alramtime, String alrmstatus, int caid, String description, int hasdanger, int helpcheck, int isprocess, String processResult, int uid, String username, String starttime, String processtimeStr) {
        this.adid = adid;
        this.alramid = alramid;
        this.alramtime = alramtime;
        this.alrmstatus = alrmstatus;
        this.caid = caid;
        this.description = description;
        this.hasdanger = hasdanger;
        this.helpcheck = helpcheck;
        this.isprocess = isprocess;
        this.processResult = processResult;
        this.uid = uid;
        this.username = username;
        this.starttime = starttime;
        this.processtimeStr = processtimeStr;
    }

    public int getAdid() {
        return adid;
    }

    public void setAdid(int adid) {
        this.adid = adid;
    }

    public int getAlramid() {
        return alramid;
    }

    public void setAlramid(int alramid) {
        this.alramid = alramid;
    }

    public String getAlramtime() {
        return alramtime;
    }

    public void setAlramtime(String alramtime) {
        this.alramtime = alramtime;
    }

    public String getAlrmstatus() {
        return alrmstatus;
    }

    public void setAlrmstatus(String alrmstatus) {
        this.alrmstatus = alrmstatus;
    }

    public int getCaid() {
        return caid;
    }

    public void setCaid(int caid) {
        this.caid = caid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHasdanger() {
        return hasdanger;
    }

    public void setHasdanger(int hasdanger) {
        this.hasdanger = hasdanger;
    }

    public int getHelpcheck() {
        return helpcheck;
    }

    public void setHelpcheck(int helpcheck) {
        this.helpcheck = helpcheck;
    }

    public int getIsprocess() {
        return isprocess;
    }

    public void setIsprocess(int isprocess) {
        this.isprocess = isprocess;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getProcesstimeStr() {
        return processtimeStr;
    }

    public void setProcesstimeStr(String processtimeStr) {
        this.processtimeStr = processtimeStr;
    }
}
