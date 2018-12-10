package com.stackroute.notificationServer.repository;

import com.stackroute.domain.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends MongoRepository<Application,Integer> {
    List<Application> getApplicationByApplicationName(String name);
}
