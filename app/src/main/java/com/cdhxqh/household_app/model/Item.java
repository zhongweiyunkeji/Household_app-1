package com.cdhxqh.household_app.model;

/**
 * 菜单项.
 * Created by hexian on 2015/8/16.
 */
public class Item {

    public String text;
    public int id;
    public int icon;

    public Item(String text, int id, int icon) {
        this.text = text;
        this.id = id;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

