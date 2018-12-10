package com.stackroute.domain;

import lombok.Data;

@Data
public class Application {
    private int id;
    private int userId;
    private String applicationName;
    private String dependency;
    private String address;

}
