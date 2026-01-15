package io.samples.dynamic.controller;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/1/15
 */
public class UrlRegistrationRequest {
    private String path;
    private RequestMethod method;
    private String businessData;

    // getters and setters
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public RequestMethod getMethod() { return method; }
    public void setMethod(RequestMethod method) { this.method = method; }

    public String getBusinessData() { return businessData; }
    public void setBusinessData(String businessData) { this.businessData = businessData; }
}
