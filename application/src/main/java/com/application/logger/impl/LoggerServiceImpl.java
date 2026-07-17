package com.application.logger.impl;

import com.application.enums.LoggerInstance;
import com.application.logger.LoggerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class LoggerServiceImpl implements LoggerService {

    private static final String CORRELATION_ID_KEY = "correlationId";

    @Override
    public void logError(LoggerInstance instance, String code, String message, Throwable exception) {
        String formattedMessage = formatMessage(instance, code, message);
        if (exception != null) {
            log.error(formattedMessage, exception);
        } else {
            log.error(formattedMessage);
        }
    }

    @Override
    public void logInfo(LoggerInstance instance, String code, String message) {
        String formattedMessage = formatMessage(instance, code, message);
        log.info(formattedMessage);
    }

    private String formatMessage(LoggerInstance instance, String code, String message) {
        String correlationId = MDC.get(CORRELATION_ID_KEY);
        String context = StringUtils.hasText(correlationId) ? String.format("[correlationId=%s]", correlationId) : "";
        return String.format("%s[%s] [%s] - %s",
                context,
                instance != null ? instance.name() : "SYSTEM",
                code,
                message);
    }
}