package com.komarnytskyy.microservice.bus.route.repository.impl;

import com.komarnytskyy.microservice.bus.route.parser.BusRoutesParser;
import com.komarnytskyy.microservice.bus.route.repository.BusRoutesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * {@inheritDoc}
 */
@Component
@Slf4j
public class InMemoryBusRoutesRepositoryImpl implements BusRoutesRepository {

    private Map<Integer, Set<Integer>> holder;

    @Autowired
    private BusRoutesParser routesParser;

    @Autowired
    private ApplicationArguments applicationArguments;

    @PostConstruct
    public void populateData() throws IOException {
        holder = routesParser.parse(applicationArguments.getSourceArgs()[0]);
        log.debug("InMemoryBusRoutesRepositoryImpl was successfully initialized");
    }

    @Override
    public Set<Integer> getBusRouteIds(int stationId) {
        Set<Integer> routes = holder.get(stationId);

        Set<Integer> resultSet = Objects.nonNull(routes) ? new HashSet<>(routes) : new HashSet<>();
        log.debug("Found {} bus route ids for station id {}", resultSet.size(), stationId);
        return resultSet;
    }

}
