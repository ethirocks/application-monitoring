package com.stackroute.service;

import com.stackroute.domain.Userlogin;

public interface UserService {
    Userlogin save(Userlogin userlogin);

    Userlogin findByEmail(String email);
}
