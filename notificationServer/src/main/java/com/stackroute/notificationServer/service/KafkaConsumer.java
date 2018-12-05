package com.stackroute.notificationServer.service;

import com.stackroute.domain.Alert;
import com.stackroute.domain.Application;
import com.stackroute.domain.Mail;
import com.stackroute.domain.User;
import com.stackroute.notificationServer.exception.ApplicationAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class KafkaConsumer {

    @Autowired
    UserService userService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    GmailService emailService;

    @Autowired
    KafkaTemplate<String,Mail> kafkaTemplate;

    public KafkaConsumer(UserService userService, ApplicationService applicationService, GmailService emailService) {
        this.userService = userService;
        this.applicationService = applicationService;
        this.emailService = emailService;
    }

//    @KafkaListener(topics = "user", containerFactory = "userKafkaListenerContainerFactory")
//    public void consumeUser(User user) throws UserAlreadyExistsException {
//        System.out.println(user.toString());
//        userService.insertUser(user);
//    }

    @KafkaListener(topics = "application2", containerFactory = "applicationKafkaListenerContainerFactory")
    public void consumeApplication(Application application) throws ApplicationAlreadyExistsException {
        System.out.println(application.toString());
        applicationService.insertApplication(application);
    }
//    @KafkaListener(topics = "alert", containerFactory = "alertKafkaListenerContainerFactory")
    public void consumeAlert(Alert alert) throws Exception {

        String to = "";
        String subject = "";
        String text = "";

        System.out.println(alert);
        //time of alert
        Date date = new Date(alert.getTime());

        //severity
        String severity = "";
        switch (alert.getAlertLevel()) {
            case 1:
                severity = "low";
                break;
            case 2:
                severity = "medium";
                break;
            case 3:
                severity = "high";
                break;
            case 4:
                severity = "critical";
                break;
            default:
                severity = "minimal";
        }

        if (userService.searchUserById(alert.getUserId()) != null) {
            System.out.println("user exists.....");
            //find user details
            User user = userService.searchUserById(alert.getUserId());

            if (applicationService.searchApplicationById(alert.getApplicationId()) != null) {
                System.out.println("application exists......");
                //find application details
                Application application = applicationService.searchApplicationById(alert.getApplicationId());

                to = user.getEmail();

                if (severity.equals("critical")) {
                    subject = "URGENT!! Critical issue in your Application " + application.getApplicationName();
                } else {
                    subject = "ALERT! Issue in your Application " + application.getApplicationName();
                }

                text = "\nHello " + user.getUserName() + " ," +

                        "\n\n\tThis mail is to inform you that there is an abnormality in your " +
                        "application" + application.getApplicationName() + "." +

                        "\nThe " + alert.getMetricsName() + " of your application deviated from its normal behaviour " +
                        "at " + date + " with " + severity + " severity." +

                        "\nPlease rectify the issue as soon as possible." +

                        "\nRegards" +
                        "\nMonitor Hub";
            }
        } else {
            //    //    String to = "divyatejaswinivengada@gmail.com";
            to = "tanushareddy182@gmail.com";
            text = "heyy...!!" +
                    "\n\n\tThis mail is to inform you that there is an abnormality in your \" +\n" +
                    "                        \"application\"";
            subject = "URGENT!! Critical issue in your Application ";

        }
        if (alert.getAlertLevel() < 2) {
            Mail mail = new Mail(to, subject, text);
            kafkaTemplate.send("notification", mail);
        } else {
            emailService.sendSimpleMessage(to, subject, text);
        }
//        MessageService messageService=new MessageService();
//        messageService.sendmessage();
    }
}
