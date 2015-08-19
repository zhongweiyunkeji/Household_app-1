package com.cdhxqh.household_app.model;

/**
 * Created by think on 2015/8/19.
 */
public class FileItem {
    private String filename;//文件名称
    private int filenumber;//文件数量
    private double filesize;//文件大小

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFilenumber() {
        return filenumber;
    }

    public void setFilenumber(int filenumber) {
        this.filenumber = filenumber;
    }

    public double getFilesize() {
        return filesize;
    }

    public void setFilesize(double filesize) {
        this.filesize = filesize;
    }
}
