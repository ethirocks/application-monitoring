package com.stackroute.domain;

public class Application {
    private int userId;
    private int id;
    private String applicationName;
    private String dependency;
    private String address;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Application() {
    }

    @Override
    public String toString() {
        return "Application{" +
                "userId=" + userId +
                ", id=" + id +
                ", applicationName='" + applicationName + '\'' +
                ", dependency='" + dependency + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Application(int userId, int id, String applicationName, String dependency, String address) {
        this.userId = userId;
        this.id = id;
        this.applicationName = applicationName;
        this.dependency = dependency;
        this.address = address;
    }
}
