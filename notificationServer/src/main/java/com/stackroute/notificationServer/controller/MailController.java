package com.stackroute.notificationServer.controller;

import com.stackroute.domain.Alert;
import com.stackroute.notificationServer.exception.UserAlreadyExistsException;
import com.stackroute.notificationServer.service.ApplicationService;
import com.stackroute.notificationServer.service.GmailService;
import com.stackroute.notificationServer.service.KafkaConsumer;
import com.stackroute.notificationServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MailController {

    @Autowired
    private GmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    ApplicationService applicationService;

    @GetMapping("hello")
    public String s() throws UserAlreadyExistsException, Exception {
//        User user=new User(0,"tanusha","yu","tanushareddy182@gmail.com","098765");
//        Application application=new Application(0,0,"monitorHub","jar","locgassdah");
//        userService.insertUser(user);
//        applicationService.insertApplication(application);
        KafkaConsumer kafkaConsumer=new KafkaConsumer(userService,applicationService,emailService);
        Alert alert=new Alert(0,0,1543340000,"cpu temp",3);
        kafkaConsumer.consumeAlert(alert);
        return "hello";
    }

}
