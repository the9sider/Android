package com.ktds.cocomo.mydatabase.vo;

/**
 * Created by 206-032 on 2016-06-20.
 */
public class Person {

    // PK
    private int _id;

    private String name;
    private int age;
    private String phone;
    private String address;

    public String getAddress() {
        return address;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
