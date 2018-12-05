package com.stackroute.domain;

public class ApplicationDomain {
//   private int aid;
    private int uid;
    private String dependency;
    private String appname;
    private String ipaddress;

    @Override
    public String toString() {
        return "ApplicationDomain{" +
                "uid=" + uid +
                ", dependency='" + dependency + '\'' +
                ", appname='" + appname + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                '}';
    }
//    public int getAid() {
//        return aid;
//    }
//
//    public void setAid(int aid) {
//        this.aid = aid;
//    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public ApplicationDomain( int uid,  String appname,String dependency, String ipaddress) {

        this.uid = uid;
        this.dependency = dependency;
        this.appname = appname;
        this.ipaddress = ipaddress;
    }

    public ApplicationDomain() {
    }
}
