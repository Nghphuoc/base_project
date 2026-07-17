package com.application.services.impl;


import com.application.dtos.RegisterRequest;
import com.application.entities.User;
import com.application.enums.ResultCode;
import com.application.exceptions.UserExistedException;
import com.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public void register(RegisterRequest request) {
        try{
            if (userRepository.existsUserByUserName(request.username())) {
                throw new UserExistedException(ResultCode.CONFLICT, "USER IS EXITING: ", request.username());
            }
            User newUser = new User();
            newUser.setUserName(request.username());
            newUser.setPassword(passwordEncoder.encode(request.password()));
            userRepository.save(newUser);
        } catch (Exception e){
            throw new UserExistedException(ResultCode.CONFLICT, "ERROR CANNOT CREATE USER: ", request.username());
        }
    }
}