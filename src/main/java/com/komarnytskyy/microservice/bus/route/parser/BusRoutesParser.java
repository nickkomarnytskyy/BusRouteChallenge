package com.komarnytskyy.microservice.bus.route.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Parser for data file
 */
@Component
@Slf4j
public class BusRoutesParser {

    /**
     * Parses provided file and returns destination data structure
     *
     * @param filePath path to source file
     * @return map where key is station id and value is set with ids of bus routes containing station id
     * @throws IOException if something went wrong during file reading
     */
    public Map<Integer, Set<Integer>> parse(String filePath) throws IOException {

        Map<Integer, Set<Integer>> destination = new HashMap<>();

        Path path = Paths.get(filePath);
        checkPath(path);

        Stream<String> lines = Files.lines(path);
        lines.skip(1).forEach(line -> processLine(line, destination));
        log.debug("Successfully parsed data file {}", filePath);

        return destination;
    }

    private void processLine(String line, Map<Integer, Set<Integer>> destination) {
        String[] busRouteArray = line.split(" ");

        Integer routeId = Integer.parseInt(busRouteArray[0]);

        Arrays.stream(busRouteArray).skip(1).map(Integer::parseInt).
                forEach(st -> {
                    Set<Integer> stationIds = destination.get(st);
                    if (Objects.isNull(stationIds)) {
                        stationIds = new HashSet<>();
                        destination.put(st, stationIds);
                    }
                    stationIds.add(routeId);

                });
    }

    private void checkPath(Path path) throws FileNotFoundException {
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            log.error("Provided path {} is invalid", path);
            throw new FileNotFoundException(String.format("Provided path - %s - is invalid", path));
        }
    }
}
