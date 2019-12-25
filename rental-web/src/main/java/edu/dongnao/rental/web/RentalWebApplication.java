package edu.dongnao.rental.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableDubbo
@SpringBootApplication
public class RentalWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentalWebApplication.class, args);
    }

}
