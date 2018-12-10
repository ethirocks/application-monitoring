package com.stackroute.monitoringserver.service;

import com.stackroute.monitoringserver.consumer.IConsumer;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class PollingService {
    private Timer timer;

    private TimerTask timerTask;

    public void setTimerTask(IConsumer consumer,String url,Integer userID,Integer applicationID) {
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    consumer.consumeMetrics(url,userID,applicationID);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public boolean start(long interval) {
        if(timer != null) {
            return false;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(this.timerTask, 0, interval);
        return true;
    }

    public boolean stop() {
        timer.cancel();
        timer = null;
        return true;
    }
}
