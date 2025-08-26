package com.semicolon.ecommerceTask.infrastructure.output.persistence.adapter.pulsarSetUp;

import com.semicolon.ecommerceTask.application.port.output.MessagingPort;
import com.semicolon.ecommerceTask.application.port.output.NotificationOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationAdapter implements NotificationOutPort {
    private final MessagingPort messagingPort;

    @Override
    public void notifySeller (String sellerId, String message){
        String topic = "persistent://public/default/seller-notifications-" + sellerId;
        messagingPort.sendMessage(topic , message);
    }

}
