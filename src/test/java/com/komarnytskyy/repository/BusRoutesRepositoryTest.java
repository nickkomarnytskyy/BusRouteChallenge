package com.komarnytskyy.repository;

import com.komarnytskyy.microservice.bus.route.parser.BusRoutesParser;
import com.komarnytskyy.microservice.bus.route.repository.impl.InMemoryBusRoutesRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.ApplicationArguments;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BusRoutesRepositoryTest {

    @InjectMocks
    private InMemoryBusRoutesRepositoryImpl busRoutesRepository;

    @Mock
    private BusRoutesParser busRoutesParser;

    @Mock
    private ApplicationArguments applicationArguments;

    private HashSet<Integer> hashSet1;
    private HashSet<Integer> hashSet2;

    @Before
    public void init() throws IOException {
        when(applicationArguments.getSourceArgs()).thenReturn(new String[]{"data/example"});
        when(busRoutesParser.parse(any())).thenReturn(getData());
        busRoutesRepository.populateData();
    }

    @Test
    public void testNonExisting() {
        Set<Integer> busRoutes = busRoutesRepository.getBusRouteIds(15);
        assertNotNull(busRoutes);
        assertTrue("BusRoutes set should be empty", busRoutes.isEmpty());
    }

    @Test
    public void testExisting() {
        Set<Integer> busRoutes = busRoutesRepository.getBusRouteIds(1);
        assertNotNull(busRoutes);
        assertTrue(hashSet1 != busRoutes);
        assertEquals("Sets should be the same", hashSet1, busRoutes);
    }

    private Map<Integer, Set<Integer>> getData() {
        Map<Integer, Set<Integer>> holder = new HashMap<>();

        hashSet1 = new HashSet<>();
        hashSet1.addAll(Arrays.asList(1, 2));
        hashSet2 = new HashSet<>();
        hashSet2.addAll(Arrays.asList(3, 5, 7));

        holder.put(1, hashSet1);
        holder.put(2, hashSet2);

        return holder;
    }

}
