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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

    @Before
    public void init() throws IOException {
        when(applicationArguments.getSourceArgs()).thenReturn(new String[]{"data/example"});
        when(busRoutesParser.parse(any())).thenReturn(getData());
        busRoutesRepository.populateData();
    }

    @Test
    public void testNonExisting() throws IOException {
        boolean check = busRoutesRepository.check(15, 10);
        assertFalse(check);
    }

    @Test
    public void testDirectFalse() throws IOException {
        boolean check = busRoutesRepository.check(2, 3);
        assertFalse(check);
    }

    @Test
    public void testDirectTrue() throws IOException {
        boolean check = busRoutesRepository.check(3, 5);
        assertTrue(check);
    }

    private List<Set<Integer>> getData() {
        List<Set<Integer>> holder = new ArrayList<>();
        HashSet<Integer> hashSet1 = new HashSet<>();
        hashSet1.addAll(Arrays.asList(1, 2));
        HashSet<Integer> hashSet2 = new HashSet<>();
        hashSet2.addAll(Arrays.asList(3, 5, 7));
        holder.add(hashSet1);
        holder.add(hashSet2);
        return holder;
    }

}
