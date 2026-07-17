package com.application.exceptions;

import com.application.dtos.ApiResponse;
import com.application.enums.ResultCode;
import com.application.logger.MessageService;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final ResultCode resultCode;
    private final HttpStatus status;
    private final String messageCode;
    private final String fallbackMessage;
    private final Object[] args;

    protected ApplicationException(ResultCode resultCode, HttpStatus status, String messageCode, String fallbackMessage, Object... args) {
        super(fallbackMessage != null ? fallbackMessage : messageCode);
        this.resultCode = resultCode;
        this.status = status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR;
        this.messageCode = messageCode != null ? messageCode : (resultCode != null ? resultCode.getMessageKey() : null);
        this.fallbackMessage = fallbackMessage;
        this.args = args != null ? args : new Object[0];
    }

    public ApiResponse<String> toApiResponse(String resolvedMessage) {
        return new ApiResponse<>(resultCode, resolvedMessage, ApiResponse.NULL_VALUE);
    }

    public String resolveMessage(MessageService messageService) {
        if (messageService == null) {
            return fallbackMessage != null ? fallbackMessage : messageCode;
        }

        if (messageCode == null) {
            return fallbackMessage != null ? fallbackMessage : (resultCode != null ? resultCode.getMessageKey() : null);
        }

        try {
            return messageService.getMessage(messageCode, args);
        } catch (Exception ex) {
            return fallbackMessage != null ? fallbackMessage : messageCode;
        }
    }
}