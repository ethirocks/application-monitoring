package com.stackroute.domain;


public class User {
    private String email;
    private String password;
    private int id;

    public User(String email, String password, int id) {
        this.email = email;
        this.password = password;
        this.id = id;
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

    public User(){
    }

    public User(String email, String password){
        this.email=email;
        this.password=password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email+ '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
