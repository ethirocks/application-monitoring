package com.stackroute.domain;

public class AppDetails {
    @Override
    public String toString() {
        return "AppDetails{" +
                "aid=" + aid +
                ", applicationName='" + applicationName + '\'' +
                ", dependency='" + dependency + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                '}';
    }

    private int aid;
    private String applicationName;
    private String dependency;
    private String ipaddress;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public AppDetails() {
    }

    public AppDetails(int aid, String applicationName, String dependency, String ipaddress) {
        this.aid = aid;
        this.applicationName = applicationName;
        this.dependency = dependency;
        this.ipaddress = ipaddress;
    }
}
