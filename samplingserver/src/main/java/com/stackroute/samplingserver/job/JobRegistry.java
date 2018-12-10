package com.stackroute.samplingserver.job;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class JobRegistry {

    private List<Class> classes;

    public JobRegistry() {
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses() {
    }
}
