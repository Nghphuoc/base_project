package com.application.exceptions;

import com.application.enums.ResultCode;
import org.springframework.http.HttpStatus;

public class ForbiddenExceptionProvider extends ApplicationException {

    public ForbiddenExceptionProvider(ResultCode resultCode, String messageCode, String fallbackMessage, Object... args) {
        super(resultCode, HttpStatus.FORBIDDEN, messageCode, fallbackMessage, args);
    }
}
