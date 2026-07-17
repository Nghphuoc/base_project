package com.application.controllers;

import com.application.dtos.ApiResponse;
import com.application.dtos.RegisterRequest;
import com.application.enums.ResultCode;
import com.application.services.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisUserController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody RegisterRequest request) {

        authService.register(request);

        ApiResponse<String> response = new ApiResponse<>(
                ResultCode.SUCCESS_CODE,
                "REGISTER_SUCCESSFULLY",
                "REGIS_OK"
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
