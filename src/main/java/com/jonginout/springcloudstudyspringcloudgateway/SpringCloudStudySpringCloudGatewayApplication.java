package com.jonginout.springcloudstudyspringcloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudStudySpringCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStudySpringCloudGatewayApplication.class, args);
    }

    // actuator http trace
    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
