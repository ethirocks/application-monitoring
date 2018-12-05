package com.stackroute.notificationServer.service;

import com.stackroute.domain.User;
import com.stackroute.notificationServer.exception.UserAlreadyExistsException;
import com.stackroute.notificationServer.exception.UserNotFoundException;
import com.stackroute.notificationServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User insertUser(User user) throws UserAlreadyExistsException {
        if(!userRepository.existsById(user.getId())) {
            User insertdUser=userRepository.insert(user);
            if(insertdUser!=null)
                return insertdUser;
            else
                throw new UserAlreadyExistsException("null: user already exists");
      }
        else
            throw new UserAlreadyExistsException("user already exists");

    }

    public List<User> searchUser(String Name) throws UserNotFoundException {
        if(userRepository.getUserByUserName(Name)!=null){
            return userRepository.getUserByUserName(Name);
        }
        else throw new UserNotFoundException("user not found");
    }


    public User searchUserById(int userId){
        if (userRepository.findById(userId).isPresent())
            return userRepository.findById(userId).get();
        else
            return null;
    }


    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

}
