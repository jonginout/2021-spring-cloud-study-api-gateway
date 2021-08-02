package com.jonginout.springcloudstudyspringcloudgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/user-service/**")
                        .uri("lb://USER-SERVICE"))
                .route(r -> r.path("/catalog-service/**")
                        .uri("lb://CATALOG-SERVICE"))
                .route(r -> r.path("/order-service/**")
                        .uri("lb://ORDER-SERVICE"))
                .build();
    }
}
