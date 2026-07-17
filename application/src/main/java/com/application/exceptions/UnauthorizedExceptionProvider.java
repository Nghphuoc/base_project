package com.application.exceptions;

import com.application.enums.ResultCode;
import org.springframework.http.HttpStatus;

public class UnauthorizedExceptionProvider extends ApplicationException {

    public UnauthorizedExceptionProvider(ResultCode resultCode, String messageCode, String fallbackMessage, Object... args) {
        super(resultCode, HttpStatus.UNAUTHORIZED, messageCode, fallbackMessage, args);
    }
}
