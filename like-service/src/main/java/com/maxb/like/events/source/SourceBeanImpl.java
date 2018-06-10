package com.maxb.like.events.source;

import com.maxb.like.events.models.BalanceChangeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SourceBeanImpl {

    private Source source;


    @Autowired
    public SourceBeanImpl(Source source){
        this.source = source;
    }

    public void publishBalanceChange(BalanceChangeModel.Action action, String userId, int value){
        log.debug("Sending Kafka message {}", action, userId);

        BalanceChangeModel change =  new BalanceChangeModel(
                BalanceChangeModel.class.getTypeName(),
                action,
                userId,
                value);

        source.output().send(MessageBuilder.withPayload(change).build());
    }
}
