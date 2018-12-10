package com.stackroute.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "applicationDetails")
@Data
public class Application {
    @Id
    private int id;
    private int userId;
    private String applicationName;
    private String dependency;
    private String address;

    public Application(int id, int userId, String applicationName, String dependency, String address) {
        this.id = id;
        this.userId = userId;
        this.applicationName = applicationName;
        this.dependency = dependency;
        this.address = address;
    }

    public Application() {
    }
}
