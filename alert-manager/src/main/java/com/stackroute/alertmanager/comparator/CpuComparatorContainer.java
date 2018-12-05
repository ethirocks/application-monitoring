package com.stackroute.alertmanager.comparator;


import org.springframework.stereotype.Service;

@Service
public class CpuComparatorContainer {
    public int compareValues(double cpuPercentContainer){
        if(cpuPercentContainer >= 95){
            return 4;
        }
        else if (cpuPercentContainer >= 90){
            return 3;
        }
        else if (cpuPercentContainer >= 85){
            return 2;
        }
        else if (cpuPercentContainer >= 80){
            return 1;
        }
        else{
            return 0;
        }
    }

    public int compareValues(long cpuPercentContainer){
        if(cpuPercentContainer >= 95){
            return 4;
        }
        else if (cpuPercentContainer >= 90){
            return 3;
        }
        else if (cpuPercentContainer >= 85){
            return 2;
        }
        else if (cpuPercentContainer >= 80){
            return 1;
        }
        else{
            return 0;
        }
    }
}
