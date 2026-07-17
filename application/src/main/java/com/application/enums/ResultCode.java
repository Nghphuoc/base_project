package com.application.enums;

import lombok.Getter;

/** ResultCode Enum. */
@Getter
public enum ResultCode {
    UI_INCORRECT_LOGIN_INFORMATION("ui.incorrect.login.information", "UI_INCORRECT_LOGIN_INFORMATION"),
    UI_INCORRECT_OTP("ui.incorrect.otp", "UI_INCORRECT_OTP"),

    SUCCESS_CODE("000000", "SUCCESS_CODE"),
    DATA_EXPORT_SUCCESSFULLY("200000", "DATA_EXPORT_SUCCESSFULLY"),
    FILE_UPLOADED_SUCCESSFULLY("200001", "FILE_UPLOADED_SUCCESSFULLY"),
    UPDATE_PASSWORD_SUCCESSFULLY("200002", "UPDATE_PASSWORD_SUCCESSFULLY"),
    REGISTER_FINAL_APPROVAL_DATA("200003", "REGISTER_FINAL_APPROVAL_DATA"),
    EXPORT_FILE_SIZE("200004", "EXPORT_FILE_SIZE"),

    INVALID_INPUT_FORMAT("9200", "INVALID_INPUT_FORMAT"),
    PERMIT_NUMBER_NOT_MATCH_VESSEL_TABLE("9201", "PERMIT_NUMBER_NOT_MATCH_VESSEL_TABLE"),
    VESSEL_TYPE_NOT_MATCH_VESSEL_TYPE_TABLE("9202", "VESSEL_TYPE_NOT_MATCH_VESSEL_TYPE_TABLE"),
    INSUFFICIENT_REPORT_CONTENT("9203", "INSUFFICIENT_REPORT_CONTENT"),
    INVALID_INPUT_PARAMETER("9400", "INVALID_INPUT_PARAMETER"),
    UNAUTHENTICATED("9401", "UNAUTHENTICATED"),
    FORBIDDEN("9403", "FORBIDDEN"),
    NOT_FOUND("9404", "NOT_FOUND"),
    CONFLICT("9409", "CONFLICT"),
    INTERNAL_SERVER_ERROR("9500", "JAFIC_EXCEPTION");

    private final String code;
    private final String messageKey;

    ResultCode(String code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public static ResultCode fromCode(String code) {
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.getCode().equals(code)) {
                return resultCode;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid code %s", code));
    }
}
