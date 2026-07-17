package com.application.logger;

import com.application.enums.LoggerInstance;

public interface LoggerService {

    void logError(LoggerInstance instance, String code, String message, Throwable exception);
    void logInfo(LoggerInstance instance, String code, String message);
}
