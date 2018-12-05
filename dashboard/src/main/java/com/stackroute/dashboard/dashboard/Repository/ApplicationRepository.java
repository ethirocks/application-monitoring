package com.stackroute.dashboard.dashboard.Repository;

import com.stackroute.domain.SendKafkaDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends MongoRepository<SendKafkaDomain,Integer> {

}
