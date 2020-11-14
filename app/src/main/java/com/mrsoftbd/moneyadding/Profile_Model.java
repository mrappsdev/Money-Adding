package com.mrsoftbd.moneyadding;

public class Profile_Model {

    
    String name;
    String phone;
    String parentUser;
    String accStatus;
    String password;

    Profile_Model(){

    }

    public Profile_Model(String parentUser){
        this.parentUser = parentUser;
    }

    public Profile_Model(String name, String phone,String password, String accStatus) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.accStatus = accStatus;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getRefferel() {
        return parentUser;
    }

    public void setRefferel(String parentUser) {
        this.parentUser = parentUser;
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

