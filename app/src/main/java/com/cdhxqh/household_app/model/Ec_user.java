package com.cdhxqh.household_app.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/8/20.
 */
public class Ec_user extends Zw_Model implements Parcelable {
    /**
     * userId
     */
    public int userId;

    /**
     * email
     */
    public String email;
    /**
     * userName
     */
    public String userName;
    /**
     * password
     */
    public String password;

    /**
     * mobilePhone
     */
    public String mobilePhone;



    public void parse(JSONObject jsonObject) throws JSONException {
        userId = jsonObject.getInt("userId");
        email = jsonObject.getString("email");
        userName = jsonObject.getString("userName");
        password = jsonObject.getString("password");
        mobilePhone = jsonObject.getString("mobilePhone");

    }

    public Ec_user() {
    }

    private Ec_user(Parcel in) {
        userId = in.readInt();
        email = in.readString();
        userName = in.readString();
        password = in.readString();
        mobilePhone = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(email);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(mobilePhone);
    }

    public static final Creator<Ec_user> CREATOR = new Creator<Ec_user>() {
        @Override
        public Ec_user createFromParcel(Parcel source) {
            return new Ec_user(source);
        }

        @Override
        public Ec_user[] newArray(int size) {
            return new Ec_user[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
