package io.samples.dynamic.controller;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/1/15
 */
public class UrlUnregistrationRequest {
    private String path;
    private RequestMethod method;

    // getters and setters
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public RequestMethod getMethod() { return method; }
    public void setMethod(RequestMethod method) { this.method = method; }
}
