package com.stackroute.notificationServer.service;

import com.stackroute.domain.Application;
import com.stackroute.notificationServer.exception.ApplicationAlreadyExistsException;
import com.stackroute.notificationServer.exception.ApplicationNotFoundException;
import com.stackroute.notificationServer.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;

    
    public Application insertApplication(Application application) throws ApplicationAlreadyExistsException {
        if(!applicationRepository.existsById(application.getId())) {
            Application insertdApplication=applicationRepository.insert(application);
            if(insertdApplication!=null)
                return insertdApplication;
            else
                throw new ApplicationAlreadyExistsException("null: application already exists");
        }
        else
            throw new ApplicationAlreadyExistsException("application already exists");
    }
    
    public List<Application> searchApplication(String Name) throws ApplicationNotFoundException {
        if(applicationRepository.getApplicationByApplicationName(Name)!=null){
            return applicationRepository.getApplicationByApplicationName(Name);
        }
        else throw new ApplicationNotFoundException("application not found");
    }

    
    public Application searchApplicationById(int applicationId){
        return applicationRepository.findById(applicationId).get();
    }

    
    public List<Application> getAllApplications() {
        return (List<Application>) applicationRepository.findAll();
    }

}
