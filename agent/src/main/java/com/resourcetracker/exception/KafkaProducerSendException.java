package com.resourcetracker.exception;

import java.io.IOException;

public class KafkaProducerSendException extends IOException {
    public KafkaProducerSendException(){
        super("Error happened sending message");
    }
}
