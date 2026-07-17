package com.application.configs;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {

    /**
     * Định nghĩa Pointcut: Bắt tất cả các hàm nằm trong package "services" và các package con của nó.
     */
    @Pointcut("within(com.application.services..*)")
    public void servicePointcut() {
        // Pointcut method không cần nội dung
    }

    /**
     * @Before: Chạy NGAY TRƯỚC khi hàm trong Service được thực thi.
     * Dùng để log tham số (parameters) đầu vào.
     */
    @Before("servicePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("[SERVICE - START] {}.{}() - Input Param: {}",
                className, methodName, Arrays.toString(args));
    }

    /**
     * @AfterReturning: Chạy NGAY SAU khi hàm trong Service chạy xong và trả về kết quả thành công.
     * Dùng để log kết quả trả về (Response).
     */
    @AfterReturning(pointcut = "servicePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        // Cẩn thận: Nếu result là list quá lớn có thể gây tràn log. 
        // Trong thực tế, có thể chỉ in ra result.toString() hoặc lược bớt nếu cần.
        log.info("[SERVICE - END] {}.{}() - Result: {}",
                className, methodName, result);
    }
}