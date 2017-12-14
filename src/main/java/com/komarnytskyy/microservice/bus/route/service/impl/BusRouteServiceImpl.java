package com.komarnytskyy.microservice.bus.route.service.impl;

import com.komarnytskyy.microservice.bus.route.dto.RouteCheckResponse;
import com.komarnytskyy.microservice.bus.route.exception.BadRequestException;
import com.komarnytskyy.microservice.bus.route.repository.BusRoutesRepository;
import com.komarnytskyy.microservice.bus.route.service.BusRoutesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@inheritDoc}
 */
@Service
@Slf4j
public class BusRouteServiceImpl implements BusRoutesService {

    @Autowired
    private BusRoutesRepository busRoutesRepository;

    @Override
    public RouteCheckResponse checkRoute(int depSid, int arrSid) {
        check(depSid, arrSid);
        boolean exists = busRoutesRepository.check(depSid, arrSid);
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
