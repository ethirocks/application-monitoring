package com.stackroute.monitoringserver.consumer;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public interface IConsumer {

    boolean consumeMetrics(String url) throws IOException, JSONException, URISyntaxException;
}
