package com.semicolon.ecommerceTask.application.port.output;

public interface EmailOutPort {
    void sendEmail(String to, String subject, String body);
}