package com.stackroute.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
public class Userlogin {
    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column (name = "id")
    private int id;

    public Userlogin(String email, String password, int id) {
        this.email = email;
        this.password = password;
        this.id = id;
    }
//
//    public Userlogin() {
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setEmail(String email) {
        this.email = email;

    }
    public String getEmail() {
        return email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword( ) {
        return password;
    }

    @Override
    public String toString() {
        return "Userlogin{" +
                "userName='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
