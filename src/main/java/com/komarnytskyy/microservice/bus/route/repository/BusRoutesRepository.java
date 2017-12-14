package com.komarnytskyy.microservice.bus.route.repository;

/**
 * Repository for bus routes
 */
public interface BusRoutesRepository {
    /**
     * @param depId departure station id
     * @param arrId arrival station id
     * @return boolean indicator of the result
     */
    boolean check(int depId, int arrId);
}
