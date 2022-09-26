package com.example.myvocab.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
public class IpAddressFilter implements Filter {
    private List<String> allowedIpRanges;

    public IpAddressFilter(List<String> allowedIpRanges) {
        super();
        this.allowedIpRanges = allowedIpRanges;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
       //String clientIpAddress = servletRequest.getRemoteAddr();
        String clientIpAddress = "127.0.0.1";

        boolean matched = allowedIpRanges.stream()
                .anyMatch(ipRange -> matchIpRange(ipRange, clientIpAddress));
        if (matched) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }


    }

    private boolean matchIpRange(String ipRange, String clientIpAddress) {

        return clientIpAddress.startsWith(ipRange);
    }
}
