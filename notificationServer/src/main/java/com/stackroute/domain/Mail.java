package com.stackroute.domain;

import lombok.Data;

@Data
public class Mail {
    private String to;
    private String subject;
    private String text;

    public Mail(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
}

