package com.komarnytskyy.microservice.bus.route.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RouteCheckResponse {

    @JsonProperty("dep_sid")
    private int depSid;
    @JsonProperty("arr_sid")
    private int arrSid;
    @JsonProperty("direct_bus_route")
    private boolean directBusRoute;

}
