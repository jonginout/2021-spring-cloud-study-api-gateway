package com.jonginout.springcloudstudyspringcloudgateway.config;

import com.jonginout.springcloudstudyspringcloudgateway.filter.AuthorizationHeaderFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(
            RouteLocatorBuilder builder,
            AuthorizationHeaderFilter authorizationHeaderFilter
    ) {
        return builder.routes()
                .route(r -> r.path("/user-service/login").and()
                        .method(HttpMethod.POST)
                        .filters(f ->
                                f.removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://USER-SERVICE"))
                .route(r -> r.path("/user-service/users").and()
                        .method(HttpMethod.POST)
                        .filters(f ->
                                f.removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://USER-SERVICE"))
                .route(r -> r.path("/user-service/**").and()
                        .method(HttpMethod.GET)
                        .filters(f ->
                                f.removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)", "/${segment}")
                                .filter(
                                        authorizationHeaderFilter.apply(
                                                new AuthorizationHeaderFilter.Config()
                                        )
                                )
                        )
                        .uri("lb://USER-SERVICE"))
                .route(r -> r.path("/user-service/actuator/**").and()
                        .method(HttpMethod.GET, HttpMethod.POST)
                        .filters(f ->
                                f.removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://USER-SERVICE"))
                .route(r -> r.path("/catalog-service/**")
                        .uri("lb://CATALOG-SERVICE"))
                .route(r -> r.path("/order-service/**")
                        .uri("lb://ORDER-SERVICE"))
                .build();
    }
}
