package com.application.logger.impl;

import com.application.logger.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    @Override
    public String getMessage(String code, Object... args) {
        try {
            return messageSource.getMessage(code, args, code, LocaleContextHolder.getLocale());
        } catch (Exception exception) {
            return code;
        }
    }
}