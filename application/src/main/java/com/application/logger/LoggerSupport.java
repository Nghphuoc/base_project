package com.application.logger;

import com.application.enums.LoggerInstance;

/**
 * Interface hỗ trợ ghi log nhanh gọn.
 * Bất kỳ class nào implement interface này đều có sẵn các hàm logError, logInfo...
 */
public interface LoggerSupport {

    /**
     * Bắt buộc class implement phải cung cấp LoggerService (thường thông qua Dependency Injection).
     */
    LoggerService getLoggerService();

    /**
     * Bắt buộc class implement phải định nghĩa xem nó thuộc nhóm log nào (APPLICATION, SYSTEM, SECURITY...).
     */
    LoggerInstance getLoggerInstance();

    default void logError(String code, String message) {
        if (getLoggerService() != null) {
            getLoggerService().logError(getLoggerInstance(), code, message, null);
        }
    }

    default void logError(String code, String message, Throwable exception) {
        if (getLoggerService() != null) {
            getLoggerService().logError(getLoggerInstance(), code, message, exception);
        }
    }

    default void logInfo(String code, String message) {
        if (getLoggerService() != null) {
            getLoggerService().logInfo(getLoggerInstance(), code, message);
        }
    }
}