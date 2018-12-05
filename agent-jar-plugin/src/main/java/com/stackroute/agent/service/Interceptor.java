package com.stackroute.agent.service;

import com.stackroute.agent.domain.HttpMetrics;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class Interceptor extends HandlerInterceptorAdapter {

    private static List<HttpMetrics> httpMetricsList=new ArrayList<>();
    private static List<HttpMetrics> httpMetricsListDummy=new ArrayList<>();
    private static long requestId=1;
    private static long requestCount=1;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        HttpMetrics httpMetrics=new HttpMetrics();
        httpMetrics.setRequestMethod(request.getMethod());
        httpMetrics.setRequestUrl(request.getRequestURL().toString());
        httpMetrics.setResponseStatus(response.getStatus());
        httpMetrics.setServerName(request.getServerName());
        httpMetrics.setServerPort(request.getServerPort());
        httpMetrics.setSessionCreationTime(request.getSession().getCreationTime());
        httpMetrics.setSessionLastAccessedTime(request.getSession().getLastAccessedTime());
        httpMetrics.setSessionId(request.getSession().getId());
        httpMetrics.setRequestCount(requestCount);
        for (HttpMetrics httpMetricsListItem :
                httpMetricsListDummy) {
            if(httpMetricsListItem.getRequestUrl().equals(request.getRequestURL().toString()))  {
                httpMetrics.setRequestCount(httpMetricsListItem.getRequestCount()+1);
            }
        }
        httpMetrics.setRequestId(requestId);
        request.setAttribute("id",requestId);
        requestId++;
        httpMetricsListDummy.add(httpMetrics);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, //
                           Object handler, ModelAndView modelAndView) throws Exception {    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long responseTime=endTime - startTime;

        for (HttpMetrics httpMetrics :
                httpMetricsListDummy) {
            if(httpMetrics.getRequestId()==(long)request.getAttribute("id")){
                httpMetrics.setResponseTime(responseTime);
                request.removeAttribute("startTime");
                this.addHttpMetrics(httpMetrics);
            }
            System.out.println(response.getHeaderNames());
        }
    }

    public List<HttpMetrics> getHttpMetricsList() {
        return this.httpMetricsList;
    }

    public void addHttpMetrics(HttpMetrics httpMetrics) {
        this.httpMetricsList.add(httpMetrics);
    }
}
