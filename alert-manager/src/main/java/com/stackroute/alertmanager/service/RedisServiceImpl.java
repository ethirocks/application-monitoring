//package com.stackroute.alertmanager.service;
//
//import com.stackroute.domain.ThresholdValue;
//import com.stackroute.alertmanager.repository.RedisRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RedisServiceImpl implements RedisService {
//
//    private RedisRepository redisRepository;
//
//    @Autowired
//    public RedisServiceImpl(RedisRepository redisRepository){
//        this.redisRepository = redisRepository;
//    }
//
//    @Override
//    public ThresholdValue saveThresholdValue (ThresholdValue thresholdValue) {
//        System.out.println(1212);
//        ThresholdValue savedValue = redisRepository.save(thresholdValue);
//        return savedValue;
//    }
//
//    @Override
//    public ThresholdValue getThresholdValueById(int applicationId){
//        ThresholdValue returnedValue = redisRepository.findbyAppId(applicationId);
//        return returnedValue;
//    }
//
//    @Override
//    public String getThresholdValueByIdandName(int applicationId, String metricName){
//        System.out.println(metricName);
//        ThresholdValue returnedValue = redisRepository.findbyAppIdandMetricName(applicationId, metricName);
//        return returnedValue.getThresholdValue();
//    }
//
//    @Override
//    public ThresholdValue updateThresholdValue(int applicationId,int userId, String metricName, String thresholdValue){
//        ThresholdValue updateValue = redisRepository.findbyAppIdandMetricName(applicationId, metricName);
//        ThresholdValue storedValue = (ThresholdValue) updateValue;
//        storedValue.setThresholdValue(thresholdValue);
//        return redisRepository.save(storedValue);
//    }
//    @Override
//    public String hello(String mk){
//        return mk;
//    }
//}
