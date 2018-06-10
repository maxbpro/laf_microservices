package com.maxb.like.events.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BalanceChangeModel {

    @NonNull
    private final String type;

    @NonNull
    private final Action action;

    @NonNull
    private final String userId;

    @NonNull
    private final int value;

    public enum Action{
        NONE, INCREASE, DECREASE
    }
}
