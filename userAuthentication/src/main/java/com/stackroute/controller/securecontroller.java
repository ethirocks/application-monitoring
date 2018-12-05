package com.stackroute.controller;

import com.stackroute.domain.Userlogin;
import com.stackroute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class securecontroller {

   private UserService userService;

    @Autowired
    public securecontroller(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/users")
    public String loginSuccess() {
        return "Login Successful!";
    }

    @RequestMapping(value = "/user/email", method = RequestMethod.POST)
    public Userlogin findByEmail(@RequestBody String email)
    {
        System.out.println("secure");
        System.out.println(userService.findByEmail(email));
        return userService.findByEmail(email);
    }

    @RequestMapping(value = "/userlogin/update", method = RequestMethod.POST)
    public Userlogin updateUser(@RequestBody Userlogin userlogin) {
        System.out.println(userlogin);
        return userService.save(userlogin);
    }
}
