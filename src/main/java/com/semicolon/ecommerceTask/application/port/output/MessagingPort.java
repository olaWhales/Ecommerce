package com.semicolon.ecommerceTask.application.port.output;

public interface MessagingPort {
    void sendMessage(String topic, String message);
    void receiveMessages(String topic);
}