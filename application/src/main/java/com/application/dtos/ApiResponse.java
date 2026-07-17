package com.application.dtos;

import com.application.enums.ResultCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

/**
 * ApiResponse class.
 *
 * @author PhuocNH.
 */
@Getter
@Setter
public class ApiResponse<T> {

    public static final String NULL_VALUE = null;

    @JsonProperty("RESULT_CODE")
    private String resultCode;

    @JsonProperty("ERROR_MESSAGE")
    private String errorMessage;

    @JsonProperty("OUTPUT_PARAMETER")
    private T outputParameter;

    /**
     * Constructor for complete response.
     *
     * @param resultCode Result code
     * @param errorMessage Error message
     * @param outputParameter Output parameter
     */
    public ApiResponse(ResultCode resultCode, String errorMessage, T outputParameter) {
        this.resultCode = resultCode.getCode();
        this.errorMessage = errorMessage;
        this.outputParameter = outputParameter;
    }

    /**
     * Create success response with data.
     *
     * @param data Response data
     * @param <T> Data type
     * @return ApiCommonResponse with success status
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResultCode.SUCCESS_CODE, NULL_VALUE, data);
    }

    /**
     * Create success response with entity data.
     *
     * @param entityName The name of the entity
     * @param data The entity data
     * @param <T> Data type
     * @return ApiCommonResponse with success status
     */
    public static <T> ApiResponse<Object> success(String entityName, T data) {
        if (Objects.isNull(data)) {
            return new ApiResponse<>(ResultCode.SUCCESS_CODE, NULL_VALUE, NULL_VALUE);
        }

        if (ObjectUtils.isEmpty(data)) {
            return new ApiResponse<>(ResultCode.SUCCESS_CODE, NULL_VALUE, data);
        }

        Map<String, T> outputParameter = Map.of(entityName, data);
        return new ApiResponse<>(ResultCode.SUCCESS_CODE, NULL_VALUE, outputParameter);
    }

    /**
     * Create success response without data.
     *
     * @return ApiCommonResponse with success status
     */
    public static ApiResponse<String> success() {
        return new ApiResponse<>(ResultCode.SUCCESS_CODE, NULL_VALUE, NULL_VALUE);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ResultCode.SUCCESS_CODE.getCode().equals(this.resultCode);
    }

    /**
     * Check if the response has an error.
     *
     * @return true if error, false otherwise
     */
    @JsonIgnore
    public boolean isError() {
        return !isSuccess();
    }
}
