package com.ktds.cocomo.addressbook.vo;

/**
 * Created by 206-032 on 2016-06-08.
 */
public class AddressBookVO {

    private String name;
    private String phoneNumber;
    private String address;

    public AddressBookVO(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
