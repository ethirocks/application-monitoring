package com.stackroute.repository;

import com.stackroute.domain.Userlogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Userlogin,String> {
    public Userlogin findByEmail(String email);
}
