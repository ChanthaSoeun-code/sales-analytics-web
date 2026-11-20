package com.yourname.sales.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS: អនុញ្ញាតឱ្យ frontend (Vite dev server) ហៅ API ផ្ទាល់បាន។
 * ពេល deploy ពិត សូមប្តូរ origin ទៅ domain របស់បង។
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173", "http://127.0.0.1:5173")
                .allowedMethods("GET", "OPTIONS");
    }
}
