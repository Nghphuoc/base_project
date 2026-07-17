package com.application.enums;

/**
 * Phân loại nguồn gốc phát sinh Log.
 */
public enum LoggerInstance {
    
    // Các lỗi liên quan đến logic nghiệp vụ của ứng dụng
    APPLICATION,
    
    // Các lỗi liên quan đến hệ thống (Network, OS, Memory...)
    SYSTEM,
    
    // Các lỗi liên quan đến xác thực và phân quyền (JWT, Login...)
    SECURITY,
    
    // Các lỗi liên quan đến kết nối hoặc truy vấn cơ sở dữ liệu
    DATABASE,
    
    // Các lỗi khi gọi API ra các hệ thống bên ngoài (Third-party API)
    EXTERNAL_SERVICE
}