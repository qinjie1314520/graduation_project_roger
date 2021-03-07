package com.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class GatewayConfiguration {
    //下面的配置即可
    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("graduation-project-roger-authority", j -> j.path("/graduation-project-roger-authority/**").uri("lb://graduation-project-roger-authority"))
                .route("graduation-project-roger-notification", j -> j.path("/graduation-project-roger-notification/**").uri("lb://graduation-project-roger-notification"))
                .route("graduation-project-roger-article", j -> j.path("/graduation-project-roger-article/**").uri("lb://graduation-project-roger-article"))
                .route("graduation-project-roger-admin", j -> j.path("/graduation-project-roger-admin/**").uri("lb://graduation-project-roger-admin"))
                .route("graduation-project-roger-user-center", j -> j.path("/graduation-project-roger-user-center/**").uri("lb://graduation-project-roger-user-center"))
                .build();
    }

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
