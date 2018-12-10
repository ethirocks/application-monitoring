package com.stackroute.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "userDetails")
@Data
public class User {
    @Id
    private int id;
    private String userName;
    private String password;
    private String email;
    private String phonenumber;

    public User(int id, String userName, String password, String email, String phonenumber) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public User() {
    }
}
