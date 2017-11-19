package com.coffee.adapter;

import android.graphics.drawable.Drawable;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ListViewItem {
    private Drawable iconDrawable;
    private String phoneNumber;
    private String openTime;
    private String addressStr;
    private String titleStr;
    private String descStr;
    private Double latitudeStr;
    private Double hardnessStr;


    public void setLatitude(Double latitude){
        latitudeStr = latitude;
    }

    public void setHardness(Double hardness){
        hardnessStr = hardness;
    }

    public void setIcon(Drawable icon) {
        iconDrawable = icon;
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setDesc(String desc) {
        descStr = desc;
    }

    public void setPhoneNumber(String phone) {
        phoneNumber = phone;
    }

    public void setOpenTime(String open) {
        openTime = open;
    }

    public void setAddress(String address) {
        addressStr = address;
    }

    public Drawable getIcon() {
        return this.iconDrawable;
    }

    public String getTitle() {
        return this.titleStr;
    }

    public String getDesc() {
        return this.descStr;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getOpenTime() {
        return this.openTime;
    }

    public String getAddress() {
        return this.addressStr;
    }

    public double getLatitude(){
        return this.latitudeStr;
    }

    public double getHardness(){
        return this.hardnessStr;
    }
}