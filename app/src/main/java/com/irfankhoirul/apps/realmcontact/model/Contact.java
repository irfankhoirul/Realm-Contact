package com.irfankhoirul.apps.realmcontact.model;

import io.realm.RealmObject;

/**
 * Created by Irfan Khoirul on 21/05/2016.
 */
public class Contact extends RealmObject {
    private static int GROUP_FRIEND = 1;
    private static int GROUP_FAMILY = 1;
    private static int GROUP_FAVORITE = 1;
    private static int GROUP_EMERGENCY = 1;
    private String name;
    private String phone;
    private String email;
    private String address;
    private int group;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
