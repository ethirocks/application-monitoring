package com.stackroute.alertmanager.comparator;

import org.springframework.stereotype.Service;

import static java.lang.Math.abs;

@Service
public class HttpComparator {
    public int compareValues(long responseTimeLive, long jmeterThreshold){
        long difference = abs(responseTimeLive - jmeterThreshold);
        long differncePercentage = (difference/jmeterThreshold)*100;

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
