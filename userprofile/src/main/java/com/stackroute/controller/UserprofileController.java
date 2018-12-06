package com.stackroute.controller;


import com.stackroute.domain.User;
import com.stackroute.domain.Userprofiledomain1;
import com.stackroute.service.UserprofileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserprofileController {


    @Autowired
    private KafkaTemplate <String, User> kafkaTemplate;

    private static final String TOPIC = "user1";


    private UserprofileService userprofileService;

    public UserprofileController(UserprofileService userprofileService){
        this.userprofileService = userprofileService;
    }

    @PostMapping("/userprofile")
    public ResponseEntity<?> idpost(@RequestBody Userprofiledomain1 userprofiledomain1){
        ResponseEntity responseEntity;
        Userprofiledomain1 userprofiledomain11=userprofileService.saveid(userprofiledomain1);
        System.out.println(userprofiledomain11.toString());
        User user = new User();
        user.setPassword(userprofiledomain11.getPassword());
        user.setEmail(userprofiledomain11.getEmail());
        user.setId(userprofiledomain11.getId());
        user.setUserName(userprofiledomain11.getUserName());
        user.setPhonenumber(userprofiledomain11.getPhonenumber());
        kafkaTemplate.send(TOPIC, user);
        responseEntity=new ResponseEntity<String>("Success created", HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/userprofile")
    public ResponseEntity<?> idget(){
        return new ResponseEntity<List<Userprofiledomain1>>(userprofileService.getallinfo(),HttpStatus.OK);
    }

}
