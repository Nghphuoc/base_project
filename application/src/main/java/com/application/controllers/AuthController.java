package com.application.controllers;

import com.application.configs.JwtUtils;
import com.application.dtos.ApiResponse;
import com.application.dtos.JwtResponse;
import com.application.dtos.LoginRequest;
import com.application.enums.ResultCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );
        // Save security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);
        ApiResponse<JwtResponse> response = new ApiResponse<>(
                ResultCode.SUCCESS_CODE,
                "REGISTER_SUCCESSFULLY",
                new JwtResponse(jwt)
        );
        return ResponseEntity.ok(response);
    }
}