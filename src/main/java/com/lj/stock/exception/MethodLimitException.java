package com.lj.stock.exception;

import lombok.Data;

@Data
public class MethodLimitException extends RuntimeException{
    private int errorCode;

    public MethodLimitException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
