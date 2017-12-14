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
     * @throws IOException if something went wrong during file reading
     */
    public List<Set<Integer>> parse(String filePath) throws IOException {

        List<Set<Integer>> destination = new ArrayList<>();

        Path path = Paths.get(filePath);

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            log.error("Provided path {} is invalid", filePath);
            throw new FileNotFoundException(String.format("Provided path - %s - is invalid", filePath));
        }

        Stream<String> lines = Files.lines(path);

        lines.skip(1).forEach(line -> {

            String[] busRouteArray = line.split(" ");

            Set<Integer> stations = new HashSet<>();
            Arrays.stream(busRouteArray).skip(1).forEach(st -> stations.add(Integer.valueOf(st)));
            destination.add(stations);

        });
        log.debug("Successfully parsed data file {}", filePath);

        return destination;
    }
}
