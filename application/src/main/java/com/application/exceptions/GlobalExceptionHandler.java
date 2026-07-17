package com.application.exceptions;

import com.application.dtos.ApiResponse;
import com.application.enums.LoggerInstance;
import com.application.enums.ResultCode;
import com.application.logger.LoggerService;
import com.application.logger.LoggerSupport;
import com.application.logger.MessageService;
import com.application.logger.MessageSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler implements LoggerSupport, MessageSupport {

    private final MessageService messageService;
    private final LoggerService loggerService;

    @Override
    public MessageService getMessageService() {
        return messageService;
    }

    @Override
    public LoggerService getLoggerService() {
        return loggerService;
    }

    @Override
    public LoggerInstance getLoggerInstance() {
        return LoggerInstance.APPLICATION;
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<String>> handleApplicationException(ApplicationException exception) {
        String resolvedMessage = exception.resolveMessage(getMessageService());
        String logCode = exception.getMessageCode() != null ? exception.getMessageCode() : exception.getResultCode().getCode();
        logError(logCode, resolvedMessage, exception);
        return new ResponseEntity<>(exception.toApiResponse(resolvedMessage), exception.getStatus());
    }

    @ExceptionHandler({BadCredentialsException.class, AccessDeniedException.class})
    public ResponseEntity<ApiResponse<String>> handleSecurityException(Exception exception) {
        ResultCode resultCode = exception instanceof BadCredentialsException
                ? ResultCode.UNAUTHENTICATED
                : ResultCode.FORBIDDEN;

        String message = getMessage(resultCode.getMessageKey());
        loggerService.logError(LoggerInstance.SECURITY, resultCode.getCode(), message, exception);

        ApiResponse<String> apiResponse = new ApiResponse<>(resultCode, message, ApiResponse.NULL_VALUE);
        HttpStatus status = exception instanceof BadCredentialsException ? HttpStatus.UNAUTHORIZED : HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(apiResponse, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .findFirst()
                .orElseGet(() -> getMessage(ResultCode.INVALID_INPUT_PARAMETER.getMessageKey()));

        logError(ResultCode.INVALID_INPUT_PARAMETER.getCode(), message, exception);
        ApiResponse<String> apiResponse = new ApiResponse<>(ResultCode.INVALID_INPUT_PARAMETER, message, ApiResponse.NULL_VALUE);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResponse<String>> handleInputParameterException(Exception exception) {
        String message = getMessage(ResultCode.INVALID_INPUT_PARAMETER.getMessageKey(), exception.getMessage());
        logError(ResultCode.INVALID_INPUT_PARAMETER.getCode(), message, exception);

        ApiResponse<String> apiResponse = new ApiResponse<>(ResultCode.INVALID_INPUT_PARAMETER, message, ApiResponse.NULL_VALUE);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        String message = getMessage(ResultCode.CONFLICT.getMessageKey());
        logError(ResultCode.CONFLICT.getCode(), message, exception);

        ApiResponse<String> apiResponse = new ApiResponse<>(ResultCode.CONFLICT, message, ApiResponse.NULL_VALUE);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleUnexpectedException(Exception exception) {
        String message = getMessage(ResultCode.INTERNAL_SERVER_ERROR.getMessageKey());
        loggerService.logError(LoggerInstance.SYSTEM, ResultCode.INTERNAL_SERVER_ERROR.getCode(), exception.getMessage(), exception);

        ApiResponse<String> apiResponse = new ApiResponse<>(ResultCode.INTERNAL_SERVER_ERROR, message, ApiResponse.NULL_VALUE);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}