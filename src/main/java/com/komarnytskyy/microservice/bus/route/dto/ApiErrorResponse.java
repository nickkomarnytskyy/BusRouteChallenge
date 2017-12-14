package com.komarnytskyy.microservice.bus.route.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiErrorResponse {
    private String error;
}
