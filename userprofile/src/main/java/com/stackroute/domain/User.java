package com.stackroute.domain;


import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class User {
    @NotNull
    @Id
    private int id;
    private String email;
    private String password;
    private String userName;
    private String phonenumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public User(@NotNull int id, String email, String password, String userName, String phonenumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.phonenumber = phonenumber;
    }

    public User(){
    }

    public User(String email, String password, int id) {
        this.id = id;
        this.email= email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
