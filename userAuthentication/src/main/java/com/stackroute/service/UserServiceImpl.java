package com.stackroute.service;

import com.stackroute.domain.Userlogin;
import com.stackroute.domain.User;
import com.stackroute.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;



    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Userlogin save(Userlogin userlogin) {
        return userRepository.save(userlogin);
    }

    @Override
    public Userlogin findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


@KafkaListener(topics = "${kafka.listeningTopic}" ,groupId = "${kafka.groupId}",
        containerFactory="${kafka.containerFactory}")
    public void getUserFrmTopic(@Payload User user) {
        System.out.println("Consumed Json Message: "+ user.toString());
        Userlogin userlogin = new Userlogin();
        userlogin.setEmail(user.getEmail());
        userlogin.setPassword(user.getPassword());
        userlogin.setId(user.getId());
//        userlogin.setId(user.getId());
        userRepository.save(userlogin);
    }
}

