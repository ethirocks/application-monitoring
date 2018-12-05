package com.stackroute.service;


import com.stackroute.domain.Userprofiledomain1;
import com.stackroute.repository.UserprofileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserprofileServiceImplementation implements UserprofileService {

    private UserprofileRepository userprofileRepository;

    @Autowired
    public UserprofileServiceImplementation(UserprofileRepository userprofileRepository){
        this.userprofileRepository = userprofileRepository;
    }

    @Override
    public Userprofiledomain1 saveid(Userprofiledomain1 domain){
        int counter= domain.getId();
        int dummy=counter;
        List<Userprofiledomain1> allprofiles = userprofileRepository.findAll();
        counter=0;
        for(int i=0;i<allprofiles.size();i++){

                counter++;

           }

        domain.setId(counter);
        Userprofiledomain1 savedid= userprofileRepository.save(domain);
        return savedid;
    }

    @Override
    public List<Userprofiledomain1> getallinfo(){
        return userprofileRepository.findAll();
    }

}
