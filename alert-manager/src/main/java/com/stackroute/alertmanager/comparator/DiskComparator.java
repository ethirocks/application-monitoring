package com.stackroute.alertmanager.comparator;

import org.springframework.stereotype.Service;

@Service
public class DiskComparator {
    public int compareValues(long diskTotalLive, long diskFree, long diskTotalSample){
        long total;
        if(diskTotalLive == diskTotalSample){
            total = diskTotalSample;
        }else{
            total = diskTotalLive;
        }
        long diskUsed = total - diskFree;
        int diskUsedPercent = (int) ((diskUsed/total)*100);

        if(diskUsedPercent >= 95){
            return 4;
        }
        else if (diskUsedPercent >= 90){
            return 3;
        }
        else if (diskUsedPercent >= 85){
            return 2;
        }
        else if (diskUsedPercent >= 80){
            return 1;
        }
        else{
            return 0;
        }
    }
}
