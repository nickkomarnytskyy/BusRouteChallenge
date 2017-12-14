package com.komarnytskyy.microservice.bus.route.service;

import com.komarnytskyy.microservice.bus.route.dto.RouteCheckResponse;

/**
 * Service for working with bus routes
 */
public interface BusRoutesService {
    /**
     * @param depSid id of departure station
     * @param arrSid id of arrival station
     * @return {@link RouteCheckResponse} object with the result of check
     */
    RouteCheckResponse checkRoute(int depSid, int arrSid);
}
