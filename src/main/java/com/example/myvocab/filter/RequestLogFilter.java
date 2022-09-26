package com.example.myvocab.filter;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Log4j2
public class RequestLogFilter implements Filter {
    private final List<String> skipLogAPI;

    public RequestLogFilter() {
        skipLogAPI = new ArrayList<>();
        skipLogAPI.add("http://localhost:8080/asset/");
        skipLogAPI.add("http://localhost:8080/vendor/");
        skipLogAPI.add("http://localhost:8080/upload/");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // log request
        if (isAPItoLog(httpServletRequest)) {
            log.info("[request] {} {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL());
        }


        // pass the request to next filter
        filterChain.doFilter(servletRequest, servletResponse);

        // log response
        if (isAPItoLog(httpServletRequest)) {
            log.info("[Response] {} {} {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL(), HttpStatus.valueOf(httpServletResponse.getStatus()));
        }
    }

    public boolean isAPItoLog(HttpServletRequest httpServletRequest) {
        for (String s : skipLogAPI) {
            if (httpServletRequest.getRequestURL().toString().startsWith(s)) {
                return false;
            }
        }
        return true;
    }
}
