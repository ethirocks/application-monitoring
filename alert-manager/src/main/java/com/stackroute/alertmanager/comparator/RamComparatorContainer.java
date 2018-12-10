package com.stackroute.alertmanager.comparator;

import org.springframework.stereotype.Service;

@Service
public class RamComparatorContainer {
    public int compareValues(double ramPercentContainer){
        if(ramPercentContainer >= 95){
            return 4;
        }
        else if (ramPercentContainer >= 90){
            return 3;
        }
        else if (ramPercentContainer >= 85){
            return 2;
        }
        else if (ramPercentContainer >= 80){
            return 1;
        }
        else{
            return 0;
        }
    }

    public int compareValues(long ramPercentContainer){
        if(ramPercentContainer >= 95){
            return 4;
        }
        else if (ramPercentContainer >= 90){
            return 3;
        }
        else if (ramPercentContainer >= 85){
            return 2;
        }
        else if (ramPercentContainer >= 80){
            return 1;
        }
        else{
            return 0;
        }
    }
}
