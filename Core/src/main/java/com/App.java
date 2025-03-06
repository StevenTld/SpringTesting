package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // Activer Feign pour les appels entre services
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}