package com.stackroute.dashboard.dashboard.Service;

import com.stackroute.dashboard.dashboard.Repository.ApplicationRepository;
import com.stackroute.domain.AppDetails;
import com.stackroute.domain.ApplicationDomain;
import com.stackroute.domain.SendKafkaDomain;
import com.stackroute.domain.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ApplicationgeneratorService{
    private ApplicationRepository applicationRepository;
    @Autowired
    public ApplicationgeneratorService(ApplicationRepository applicationRepository){
        this.applicationRepository=applicationRepository;
    }

    public List<SendKafkaDomain> getallinfo(){
        return applicationRepository.findAll();
    }

    public String addapplication(ApplicationDomain applicationDomain){
        int check;
//        ResponseEntity responseEntity=null;
        String message="";
        List<SendKafkaDomain> sendKafkaDomains;
        sendKafkaDomains=getallinfo();
        if(sendKafkaDomains.size()==0){
            List<AppDetails> appDetails=new ArrayList<AppDetails>();
            AppDetails appDetails1=new AppDetails(0,applicationDomain.getAppname(),applicationDomain.getDependency(),applicationDomain.getIpaddress());
            appDetails.add(appDetails1);
            SendKafkaDomain updateentrysend=new SendKafkaDomain(applicationDomain.getUid(),appDetails);
            applicationRepository.save(updateentrysend);
            message="added successfully";
            return message;
        }
        else{
            int y=0;
            List<AppDetails> appDetails2 = new ArrayList<>();
        for(SendKafkaDomain a:sendKafkaDomains) {
            int x = 0;
            if (a.getCid() == applicationDomain.getUid()) {
                List<AppDetails> appDetails;
                appDetails = a.getAppDetails();
//                if (appDetails.size() == 0) {
//
//                }
//                else{
                for (AppDetails b : appDetails) {
                    if (b.getApplicationName().equals(applicationDomain.getAppname())) {
//                        responseEntity=new ResponseEntity<String>("application name already exists", HttpStatus.CONFLICT);
                        message = "application name already exists";
//
                        return message;

                    } else {

                        x++;
                    }
                }
//            }

                if (x == 0) {
                } else {
                    int counter = 0;
                    for (int i = 0; i < appDetails.size(); i++) {
                        counter++;
                    }
                    AppDetails updateentry = new AppDetails(counter, applicationDomain.getAppname(), applicationDomain.getDependency(), applicationDomain.getIpaddress());
                    appDetails.add(updateentry);
                    SendKafkaDomain updateentrysend = new SendKafkaDomain(a.getCid(), appDetails);
                    applicationRepository.deleteById(applicationDomain.getUid());
                    applicationRepository.save(updateentrysend);
//                        responseEntity=new ResponseEntity<String>("added successfully",HttpStatus.OK);
                    message = "added successfully";
                    return message;
                }
            }
            else {
            y++;}
        }
        if(y==0){}
            else{
                        int counter = 0;
                        for (int i = 0; i < appDetails2.size(); i++) {
                            counter++;
                        }
                        AppDetails appDetails1 = new AppDetails(counter, applicationDomain.getAppname(), applicationDomain.getDependency(), applicationDomain.getIpaddress());
                        appDetails2.add(appDetails1);
                        SendKafkaDomain updateentrysend = new SendKafkaDomain(applicationDomain.getUid(), appDetails2);
                        applicationRepository.save(updateentrysend);
//                responseEntity=new ResponseEntity<String>("added successfully",HttpStatus.OK);
                        message = "added successfully";
                        return message;
                    }
            }
        return message;
    }
    public List<Application> sendingkafka(int id){
        List<SendKafkaDomain> sendKafkaDomains=new ArrayList<>();
        sendKafkaDomains=getallinfo();
        System.out.println("getall info "+sendKafkaDomains);
        List<Application> applications =new ArrayList<Application>();
            for(SendKafkaDomain a:sendKafkaDomains){
            if(a.getCid()==id){
                System.out.println(a.toString());
                List<AppDetails> appDetails=a.getAppDetails();
                System.out.println("app details"+appDetails);

                for(AppDetails b:appDetails){
                    Application application =new Application();
                    System.out.println("senfing kafka"+b);
                    application.setId(b.getAid());
                    application.setApplicationName(b.getApplicationName());
                    application.setDependency(b.getDependency());
                    application.setAddress(b.getIpaddress());
                    application.setUserId(id);
                    applications.add(application);
                    System.out.println(applications);
                }
                return applications;
            }
        }
        return applications;
    }
}