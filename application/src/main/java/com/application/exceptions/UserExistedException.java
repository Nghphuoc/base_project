package com.application.exceptions;


import com.application.enums.ResultCode;
import org.springframework.http.HttpStatus;

public class UserExistedException extends ApplicationException {

    public UserExistedException(ResultCode resultCode, String messageCode, String fallbackMessage, Object... args) {
        super(resultCode, HttpStatus.UNAUTHORIZED, messageCode, fallbackMessage, args);
    }
}