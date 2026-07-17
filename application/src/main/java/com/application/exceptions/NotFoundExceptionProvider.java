package com.application.exceptions;

import com.application.enums.ResultCode;
import org.springframework.http.HttpStatus;

public class NotFoundExceptionProvider extends ApplicationException {

    public NotFoundExceptionProvider(ResultCode resultCode, String messageCode, String fallbackMessage, Object... args) {
        super(resultCode, HttpStatus.NOT_FOUND, messageCode, fallbackMessage, args);
    }
}
