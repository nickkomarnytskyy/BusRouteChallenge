package com.komarnytskyy.service;

import com.komarnytskyy.microservice.bus.route.dto.RouteCheckResponse;
import com.komarnytskyy.microservice.bus.route.exception.BadRequestException;
import com.komarnytskyy.microservice.bus.route.repository.BusRoutesRepository;
import com.komarnytskyy.microservice.bus.route.service.impl.BusRouteServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    public void testValidData() {

        final int depSid = 1;
        final int arrSid = 2;
        final boolean direct = true;

        when(busRoutesRepository.check(depSid, arrSid)).thenReturn(direct);

        RouteCheckResponse routeCheckResponse = busRouteService.checkRoute(depSid, arrSid);

        assertNotNull(routeCheckResponse);
        assertEquals("Dep_sid should be the same", depSid, routeCheckResponse.getDepSid());
        assertEquals("Arr_sid should be the same", arrSid, routeCheckResponse.getArrSid());
        assertEquals("Direct boolean should be the same", direct, routeCheckResponse.isDirectBusRoute());

    }
}
