package com.maxb.user.events;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomChannels {

    @Input("inboundBalanceChanges")
    SubscribableChannel balanceInput();

}
