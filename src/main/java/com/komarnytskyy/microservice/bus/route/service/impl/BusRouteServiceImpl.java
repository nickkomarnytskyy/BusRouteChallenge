package com.komarnytskyy.microservice.bus.route.service.impl;

import com.komarnytskyy.microservice.bus.route.dto.RouteCheckResponse;
import com.komarnytskyy.microservice.bus.route.exception.BadRequestException;
import com.komarnytskyy.microservice.bus.route.repository.BusRoutesRepository;
import com.komarnytskyy.microservice.bus.route.service.BusRoutesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * {@inheritDoc}
 */
@Service
@Slf4j
public class BusRouteServiceImpl implements BusRoutesService {

    @Autowired
    private BusRoutesRepository busRoutesRepository;

    @Override
    @Cacheable("routes")
    public RouteCheckResponse checkRoute(int depSid, int arrSid) {
        //assume that bus goes back-and-forth on the route
        check(depSid, arrSid);

        Set<Integer> depBusRoutes = busRoutesRepository.getBusRouteIds(depSid);
        Set<Integer> arrBusRoutes = busRoutesRepository.getBusRouteIds(arrSid);

        depBusRoutes.retainAll(arrBusRoutes);

        boolean exists = depBusRoutes.size() != 0;

        log.info("BusRoute existence from station {} to station {} - {}", depSid, arrSid, exists);
        return new RouteCheckResponse(depSid, arrSid, exists);
    }

    private void check(int depSid, int arrSid) {

        if (depSid == arrSid) {
            log.warn("DepSid and ArrSid should be different");
            throw new BadRequestException("dep_sid and arr_sid should be different");
        }

    }

}
