package com.stackroute.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor

@ToString
public class JwtResponse {
    String token;

    String email;

    String uid;

    public JwtResponse(String token, String email,String uid)  {
        this.token = token;
        this.email = email;
        this.uid=uid;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "token='" + token + '\'' +
                "email='" + email+ '\'' +
                "uid='" + uid+ '\'' +
                '}';
    }
}
