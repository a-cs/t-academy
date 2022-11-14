package com.twms.wms.exceptions;

public class NoSuchClientException extends RuntimeException{
    public NoSuchClientException(String message) {
        super(message);
    }
}
