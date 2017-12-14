package com.komarnytskyy.microservice.bus.route.controller;

import com.komarnytskyy.microservice.bus.route.dto.RouteCheckResponse;
import com.komarnytskyy.microservice.bus.route.service.BusRoutesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api")
@Slf4j
public class BusRouteController {

    @Autowired
    private BusRoutesService busRoutesService;

    @RequestMapping(value = "/direct", method = RequestMethod.GET, produces = "application/json")
    public RouteCheckResponse checkRoute(@RequestParam("dep_sid") int depSid, @RequestParam("arr_sid") int arrSid) {

        log.info("Check route invoked with depSid {} and arrSid {}", depSid, arrSid);
        RouteCheckResponse routeCheckResponse = busRoutesService.checkRoute(depSid, arrSid);
        log.info("Check for route is {}", routeCheckResponse);

        return routeCheckResponse;
    }

}
