package com.mrsoftbd.moneyadding;

public class Profile_Model {

    String name;
    String phone;
    String refferel;

    Profile_Model(){

    }

    public Profile_Model(String refferel) {
        this.refferel = refferel;
    }

    public Profile_Model(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getRefferel() {
        return refferel;
    }

    public void setRefferel(String refferel) {
        this.refferel = refferel;
    }

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
}
