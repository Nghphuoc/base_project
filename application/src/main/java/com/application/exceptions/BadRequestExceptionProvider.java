package com.application.exceptions;

import com.application.enums.ResultCode;
import org.springframework.http.HttpStatus;

public class BadRequestExceptionProvider extends ApplicationException {

    public BadRequestExceptionProvider(ResultCode resultCode, String messageCode, String fallbackMessage, Object... args) {
        super(resultCode, HttpStatus.BAD_REQUEST, messageCode, fallbackMessage, args);
    }
}
