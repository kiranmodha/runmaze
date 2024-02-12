package com.example.runmaze.stravazpot.common.api.exception;

public class StravaAPIException extends RuntimeException{

    public StravaAPIException(String message) {
        super(message);
    }

    public StravaAPIException(String message, Throwable cause) {
        super(message, cause);
    }

}
