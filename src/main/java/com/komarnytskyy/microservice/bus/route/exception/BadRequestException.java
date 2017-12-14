package com.komarnytskyy.microservice.bus.route.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super("BadRequest");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
