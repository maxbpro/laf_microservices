package com.maxb.user.services;

import com.maxb.user.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    void register(User user);

    void increaseBalance(String userId, int value);

    void decreaseBalance(String userId, int value);

    User findByUserId(String userId);

    boolean activeInviteCode(String userId, String code);

    void clearAll();
}
