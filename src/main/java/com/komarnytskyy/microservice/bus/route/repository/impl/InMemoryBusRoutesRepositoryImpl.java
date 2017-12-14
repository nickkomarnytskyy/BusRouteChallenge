package com.komarnytskyy.microservice.bus.route.repository.impl;

import com.komarnytskyy.microservice.bus.route.parser.BusRoutesParser;
import com.komarnytskyy.microservice.bus.route.repository.BusRoutesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@inheritDoc}
 */
@Component
@Slf4j
public class InMemoryBusRoutesRepositoryImpl implements BusRoutesRepository {

    private Set<Integer> uniquesStations = new HashSet<>();
    private List<Set<Integer>> holder;

    @Autowired
    private BusRoutesParser routesParser;

    @Autowired
    private ApplicationArguments applicationArguments;

    @PostConstruct
    public void populateData() throws IOException {
        holder = routesParser.parse(applicationArguments.getSourceArgs()[0]);
        populateUniqueStations();
        log.debug("InMemoryBusRoutesRepositoryImpl was successfully initialized");
    }

    private void populateUniqueStations() {
        holder.forEach(uniquesStations::addAll);
    }

    @Override
    // a little optimization
    @Cacheable("routes")
    public boolean check(int depId, int arrId) {

        //one more little optimization
        if (!uniquesStations.contains(depId) || !uniquesStations.contains(arrId)) {
            return false;
        }

        //assume that bus goes back-and-forth on the route
        return holder.stream().anyMatch(set -> set.contains(depId) && set.contains(arrId));
    }
}
