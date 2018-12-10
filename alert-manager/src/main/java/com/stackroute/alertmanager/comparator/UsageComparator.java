package com.stackroute.alertmanager.comparator;

import org.springframework.stereotype.Service;

import static java.lang.Math.abs;

@Service
public class UsageComparator {
    public int compareValues(Double cpuUsageLive, Double usageThreshold){
        Double difference = abs(cpuUsageLive - usageThreshold);
        Double differncePercentage = (difference/usageThreshold)*100;

        if(differncePercentage >= 80){
            return 4;
        }
        else if (differncePercentage >= 50){
            return 3;
        }
        else if (differncePercentage >= 30){
            return 2;
        }
        else if (differncePercentage >= 15){
            return 1;
        }
        else{
            return 0;
        }
    }

}
