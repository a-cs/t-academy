package com.twms.wms.exceptions;

public class NoSuchBranchException extends RuntimeException{
    public NoSuchBranchException(String message) {
        super(message);
    }
}
