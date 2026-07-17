package com.application.logger;

public interface MessageSupport {

    MessageService getMessageService();

    default String getMessage(String code, Object... args) {
        return getMessageService().getMessage(code, args);
    }
}