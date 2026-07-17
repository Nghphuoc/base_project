package com.application.exceptions;

import com.application.enums.ResultCode;
import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException {

    public BadRequestException(ResultCode resultCode, String messageCode, Object... args) {
        super(resultCode, HttpStatus.BAD_REQUEST, messageCode, null, args);
    }
}
