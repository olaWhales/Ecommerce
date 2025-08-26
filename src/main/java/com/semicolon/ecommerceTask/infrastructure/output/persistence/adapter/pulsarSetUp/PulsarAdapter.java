package com.semicolon.ecommerceTask.infrastructure.output.persistence.adapter.pulsarSetUp;

import com.semicolon.ecommerceTask.application.port.output.MessagingPort;
import lombok.AllArgsConstructor;
import org.apache.pulsar.client.api.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PulsarAdapter implements MessagingPort {
    public final PulsarClient pulsarClient;

    @Override
    public void sendMessage(String topic, String message) {
        try{
            Producer<String> producer = pulsarClient.newProducer(Schema.STRING)
                    .topic(topic)
                    .create();
            producer.send(message);
            producer.close();
        }catch (PulsarClientException e){
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessages(String topic) {
        try{
            Consumer<String> consumer = pulsarClient.newConsumer(Schema.STRING)
                    .topic(topic)
                    .subscriptionName("subscription-name")
                    .subscribe();
            while(true){
                Message<String> msg = consumer.receive();
                System.out.println("Received message: " + msg.getMessageId() + ": " + msg.getValue());
                consumer.acknowledge(msg);
            }
        }catch(PulsarClientException e){
            e.printStackTrace();
        }
    }
}
