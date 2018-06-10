package com.maxb.user.events.handlers;

import com.maxb.user.events.CustomChannels;
import com.maxb.user.events.models.BalanceChangeModel;
import com.maxb.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;


@EnableBinding(CustomChannels.class)
public class BalanceChangeHandler {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(BalanceChangeHandler.class);

    @StreamListener("inboundBalanceChanges")
    public void loggerSink(BalanceChangeModel event) {
        logger.debug("Received a message of type " + event.getType() + " for " + event);
        switch(event.getAction()){
            case DECREASE:
                logger.debug("Received a DECREASE Balance event for " + event.getUserId());
                userService.decreaseBalance(event.getUserId(), event.getValue());
                break;
            case INCREASE:
                logger.debug("Received a INCREASE Balance event for " + event.getUserId());
                userService.increaseBalance(event.getUserId(), event.getValue());
                break;
            default:
                logger.error("Received an UNKNOWN Balance event");
                break;

        }
    }

}
