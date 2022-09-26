package com.example.myvocab.config;

import com.example.myvocab.filter.IpAddressFilter;
import com.example.myvocab.filter.RequestLogFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
public class FilterConfig {

    @Value("${app.allowed-ip-range:}")
    private List<String> allowedIpRanges;

    @Bean
    public FilterRegistrationBean ipAddressFilter(){
        FilterRegistrationBean filterReg=new FilterRegistrationBean();
        filterReg.setFilter(new IpAddressFilter(allowedIpRanges));
        filterReg.addUrlPatterns("/admin/*");
        filterReg.setOrder(HIGHEST_PRECEDENCE + 1);
        return filterReg;
    }

    @Bean
    public FilterRegistrationBean requestLogFilter() {
        FilterRegistrationBean filterReg = new FilterRegistrationBean();
        filterReg.setFilter(new RequestLogFilter());
        filterReg.addUrlPatterns("/*");
        filterReg.setOrder(HIGHEST_PRECEDENCE);
        return filterReg;
    }


}
