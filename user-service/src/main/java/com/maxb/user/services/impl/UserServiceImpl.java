package com.maxb.user.services.impl;

import com.maxb.user.domain.User;
import com.maxb.user.repository.UserRepository;
import com.maxb.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public void register(User user) {

        int min = 100000;
        int max = 999999;

        Random r = new Random();
        int inviteCode = r.nextInt(max - min + 1) + min;

        user.setInviteCode(String.valueOf(inviteCode));

        user.setBalance(100);

        userRepository.save(user);
    }

    @Override
    public void increaseBalance(String userId, int value) {
        User user = userRepository.findByUserId(userId);

        if(user != null){
            user.setBalance(user.getBalance() + value);
            userRepository.save(user);
        }
    }

    @Override
    public void decreaseBalance(String userId, int value) {
        User user = userRepository.findByUserId(userId);

        if(user != null){
            user.setBalance(user.getBalance() - value);
            userRepository.save(user);
        }
    }

    @Override
    public boolean activeInviteCode(String userId, String code) {

        User codeOwner = userRepository.findByInviteCode(code);

        if(codeOwner == null){
            return false;
        }


        increaseBalance(codeOwner.getUserId(), 1000);

        User sourceUser = userRepository.findByUserId(userId);
        sourceUser.setBalance(1100);
        userRepository.save(sourceUser);

        //PushService.sendPush(String.valueOf(otherClient.getUserId()), "Кто-то использовал Ваш Инвайт-код!");
        //PushService.sendPush(String.valueOf(client.getUserId()), "Вы получили 1000 монет за использование Инвайт-кода!");


        return true;
    }

    @Override
    public void clearAll() {
        userRepository.deleteAll();
    }
}
