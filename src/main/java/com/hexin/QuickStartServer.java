package com.hexin;

import com.hexin.common.filter.PermissionsAuthFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;
@EnableScheduling
@SpringBootApplication
@MapperScan("com.hexin.mapper")
public class QuickStartServer {

    public static void main(String[] args) {
        SpringApplication.run(QuickStartServer.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        PermissionsAuthFilter httpBasicFilter = new PermissionsAuthFilter();
        registrationBean.setFilter(httpBasicFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

}



