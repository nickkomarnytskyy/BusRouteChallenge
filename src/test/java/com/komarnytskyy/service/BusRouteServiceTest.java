package com.komarnytskyy.service;

import com.komarnytskyy.microservice.bus.route.dto.RouteCheckResponse;
import com.komarnytskyy.microservice.bus.route.exception.BadRequestException;
import com.komarnytskyy.microservice.bus.route.repository.BusRoutesRepository;
import com.komarnytskyy.microservice.bus.route.service.impl.BusRouteServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BusRouteServiceTest {

    @InjectMocks
    private BusRouteServiceImpl busRouteService;

    @Mock
    private BusRoutesRepository busRoutesRepository;

    @Test(expected = BadRequestException.class)
    public void testInvalidData() {
        busRouteService.checkRoute(1, 1);
    }

    @Test
    public void testFalse() {

        final int depSid = 1;
        final int arrSid = 2;

        when(busRoutesRepository.getBusRouteIds(depSid)).thenReturn(new HashSet<>());
        when(busRoutesRepository.getBusRouteIds(arrSid)).thenReturn(new HashSet<>());

        RouteCheckResponse routeCheckResponse = busRouteService.checkRoute(depSid, arrSid);

        assertNotNull(routeCheckResponse);
        assertEquals("Dep_sid should be the same", depSid, routeCheckResponse.getDepSid());
        assertEquals("Arr_sid should be the same", arrSid, routeCheckResponse.getArrSid());
        assertFalse("Direct should be false", routeCheckResponse.isDirectBusRoute());

    }

    @Test
    public void testTrue() {

        final int depSid = 1;
        final int arrSid = 2;

        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();

        set1.addAll(Arrays.asList(1, 2));
        set2.addAll(Arrays.asList(2, 3));

        when(busRoutesRepository.getBusRouteIds(depSid)).thenReturn(set1);
        when(busRoutesRepository.getBusRouteIds(arrSid)).thenReturn(set2);

        RouteCheckResponse routeCheckResponse = busRouteService.checkRoute(depSid, arrSid);

        assertNotNull(routeCheckResponse);
        assertEquals("Dep_sid should be the same", depSid, routeCheckResponse.getDepSid());
        assertEquals("Arr_sid should be the same", arrSid, routeCheckResponse.getArrSid());
        assertTrue("Direct should be true", routeCheckResponse.isDirectBusRoute());

    }
}
