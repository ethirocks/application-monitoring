package com.stackroute.agent.domain;

import lombok.Data;

@Data
public class HttpMetrics {
    private String requestMethod;
    private String requestUrl;
    private String sessionId;
    private long sessionCreationTime;
    private long sessionLastAccessedTime;
    private long responseTime;
    private String serverName;
    private int serverPort;
    private int responseStatus;
    private long requestId;
    private long requestCount;

//    metrics+="\nAuth type.....   "+request.getAuthType();
//    metrics+="\nContext path....   "+request.getContextPath();
////        while (request.getHeaderNames().hasMoreElements()){
////            request.getHeaderNames().
////            metrics+="\nHeader Names....   "+request.getHeaderNames().nextElement();
////            metrics+="    Header value...   "+request.getHeader(request.getHeaderNames().nextElement());
////        }
//    metrics+="\nmethod....   "+request.getMethod();
//    metrics+="\ndate header....   "+request.getDateHeader("");
//    metrics+="\nservlet name....   "+request.getHttpServletMapping().getServletName();
//    metrics+="\nservlet mapping match value....   "+request.getHttpServletMapping().getMatchValue();
//    metrics+="\nservlet mapping pattern....   "+request.getHttpServletMapping().getPattern();
//    metrics+="\nmethod....   "+request.getMethod();
//    metrics+="\npath info....   "+request.getPathInfo();
//    metrics+="\nquery string....   "+request.getQueryString();
//    metrics+="\npath translated....   "+request.getPathTranslated();
//    metrics+="\nremote user....   "+request.getRemoteUser();
//    metrics+="\nreq session id....   "+request.getRequestedSessionId();
//    metrics+="\nreq url....   "+request.getRequestURL().toString();
//    metrics+="\nreq servlet path....   "+request.getServletPath();
//    metrics+="\nsession id....   "+request.getSession().getId();
//    metrics+="\nsess creation time...   "+request.getSession().getCreationTime();
//    metrics+="\nsess last accessed time....   "+request.getSession().getLastAccessedTime();
//    metrics+="\nsess max interactive interval....   "+request.getSession().getMaxInactiveInterval();
//    metrics+="\nseess attribute names....   "+request.getSession().getAttributeNames().toString();
//    metrics+="\nreq server name...   "+request.getServerName();
//    metrics+="\nserver port...   "+request.getServerPort();
//    metrics+="\nremote port...   "+request.getRemotePort();
//    metrics+="\nremote host...   "+request.getRemoteHost();
//        System.out.println("\n-------- Interceptor.preHandle --- "+metrics);
//    String metrics1 = "\nresp status...   " + response.getStatus();
//        System.out.println("\n-------- Interceptor.preHandle --- "+metrics1);
}
