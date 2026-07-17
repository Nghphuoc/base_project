package com.application.exceptions;

import com.application.enums.ResultCode;
import org.springframework.http.HttpStatus;

public class ConflictExceptionProvider extends ApplicationException {

    public ConflictExceptionProvider(ResultCode resultCode, String messageCode, String fallbackMessage, Object... args) {
        super(resultCode, HttpStatus.CONFLICT, messageCode, fallbackMessage, args);
    }
}
