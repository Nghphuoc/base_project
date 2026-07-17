package com.application.exceptions;

import com.application.dtos.ApiResponse;
import com.application.enums.ResultCode;
import com.application.logger.LoggerService;
import com.application.logger.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Test
    void shouldMapApplicationExceptionToBadRequestResponse() {
        MessageService messageService = mock(MessageService.class);
        LoggerService loggerService = mock(LoggerService.class);
        GlobalExceptionHandler handler = new GlobalExceptionHandler(messageService, loggerService);

        when(messageService.getMessage("INVALID_INPUT_PARAMETER")).thenReturn("Invalid request parameter");

        ApplicationException exception = new BadRequestException(ResultCode.INVALID_INPUT_PARAMETER, "INVALID_INPUT_PARAMETER");

        ResponseEntity<ApiResponse<String>> response = handler.handleApplicationException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getResultCode()).isEqualTo(ResultCode.INVALID_INPUT_PARAMETER.getCode());
        assertThat(response.getBody().getErrorMessage()).isEqualTo("Invalid request parameter");

        verify(loggerService).logError(any(), eq("INVALID_INPUT_PARAMETER"), eq("Invalid request parameter"), any(Throwable.class));
    }
}
