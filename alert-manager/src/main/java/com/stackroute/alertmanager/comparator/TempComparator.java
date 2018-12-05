package com.stackroute.alertmanager.comparator;

import org.springframework.stereotype.Service;

import static java.lang.Math.abs;

@Service
public class TempComparator {
    private int count;
    public int compareValues(Double cpuTempLive, Double tempThreshold){
        Double difference = abs(cpuTempLive - tempThreshold);

        Double differncePercentage = (difference/tempThreshold)*100;

        if(differncePercentage >= 75){
            return 4;
        }
        else if (differncePercentage >= 50){
            return 3;
        }
        else if (differncePercentage >= 25){
            return 2;
        }
        else if (differncePercentage >= 10){
            return 1;
        }
        else{
            return 0;
        }
    }

}
