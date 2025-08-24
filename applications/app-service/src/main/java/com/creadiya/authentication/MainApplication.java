package com.creadiya.authentication;

import java.util.Currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        System.out.println(Currency.getInstance("USD").getSymbol());
        SpringApplication.run(MainApplication.class, args);
    }
}
