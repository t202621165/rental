package edu.dongnao.rental.uc.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author International
 */
@EnableDubbo
@SpringBootApplication
public class RentalUcProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RentalUcProviderApplication.class).run(args);
    }

}
