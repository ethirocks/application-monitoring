package com.stackroute.alertmanager.comparator;

import org.springframework.stereotype.Service;

@Service
public class RamComparator {
    public int compareValues(long ramTotalLive, long ramFree, long ramTotalSample){
        long total;
        if(ramTotalLive == ramTotalSample){
            total = ramTotalSample;
        }else{
            total = ramTotalLive;
        }
        long ramUsed = total - ramFree;
        int ramUsedPercent = (int) ((ramUsed/total)*100);

        if(ramUsedPercent >= 95){
            return 4;
        }
        else if (ramUsedPercent >= 90){
            return 3;
        }
        else if (ramUsedPercent >= 85){
            return 2;
        }
        else if (ramUsedPercent >= 80){
            return 1;
        }
        else{
            return 0;
        }
    }
}
