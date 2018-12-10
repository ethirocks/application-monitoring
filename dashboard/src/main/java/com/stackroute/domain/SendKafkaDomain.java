package com.stackroute.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection="applicationdetails")
public class SendKafkaDomain {

    @Override
    public String toString() {
        return "SendKafkaDomain{" +
                "cid=" + cid +
                ", appDetails=" + appDetails +
                '}';
    }

    @Id
    private int cid;
    private List<AppDetails> appDetails;
    public SendKafkaDomain(int cid, List<AppDetails> appDetails) {
        this.cid = cid;
        this.appDetails = appDetails;
    }

    public SendKafkaDomain() {
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public List<AppDetails> getAppDetails() {
        return appDetails;
    }

    public void setAppDetails(List<AppDetails> appDetails) {
        this.appDetails = appDetails;
    }
}
