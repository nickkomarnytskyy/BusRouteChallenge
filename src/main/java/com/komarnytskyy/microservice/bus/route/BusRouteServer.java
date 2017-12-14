package com.komarnytskyy.microservice.bus.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BusRouteServer {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "busroute-server");
        checkArgs(args);
        SpringApplication.run(BusRouteServer.class, args);

    }

    private static void checkArgs(String[] args) {
        if (args.length != 1) {
            throw new IllegalStateException("You should specify path to the file!");
        }
    }

}