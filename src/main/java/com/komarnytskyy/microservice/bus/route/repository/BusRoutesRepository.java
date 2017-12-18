package com.komarnytskyy.microservice.bus.route.repository;

import java.util.Set;

/**
 * Repository for bus routes
 */
public interface BusRoutesRepository {

    /**
     * @param stationId id of station
     * @return ids set of bus routes containing given station id
     */
    Set<Integer> getBusRouteIds(int stationId);

}
