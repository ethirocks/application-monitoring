package com.stackroute.monitoringserver.domain;

public class Metrics<GenericMetrics> {

    // Object of type Metrics is declared
    GenericMetrics genericMetrics;
    Metrics(GenericMetrics genericMetrics){ this.genericMetrics=genericMetrics;   }        // constructor
    public GenericMetrics getMetrics(){    return this.genericMetrics;}

}