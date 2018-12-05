package com.stackroute.repository;


import com.stackroute.domain.Userprofiledomain1;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserprofileRepository extends MongoRepository<Userprofiledomain1,Integer> {

}
