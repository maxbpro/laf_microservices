package com.maxb.user.services.impl;

import com.maxb.user.domain.User;
import com.maxb.user.repository.UserRepository;
import com.maxb.user.services.UserService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo4bDus9f/mRxY74vE/aK5HFvYq9Ia2b2qYs22mg0o9GFyqcR+1GWNOcGKHhCUZdtmaTfVGbpqdgmd/Ec0F4u6ofKyEmlMG8PpcmP4YUjFNImKSfDEu5ZfOcaCv+4zjIL9igFfQ5ZBRzPMja07kPu7qBOopv7dJE+Msk48ly2KSfT4TOBLdLUYSLIKpPc/yuWdkqSkWhrfGzYztjc6iiBGNZh1xMhmrvCn9bYePPTy69VQiH/d7G9d1eOk8mSefzeSKEgE9f82ppbeofW2ttaqTJ1uAz/f8tN4UVCYMvmx4wVp0i+1+bbvn4zDJDVczuupmAD7R7WYnqTK/fj2wFPSQIDAQAB";


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
    public boolean verify(String signedData, String signature) {

        try{

            byte[] keyBytes = Base64.decodeBase64(PUBLIC_KEY.getBytes("utf-8"));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(spec);

            Signature sign = Signature.getInstance("SHA1withRSA");
            sign.initVerify(publicKey);
            sign.update(signedData.getBytes());
            byte[] bytes = Base64.decodeBase64(signature);
            boolean success = sign.verify(bytes);

            return success;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public void clearAll() {
        userRepository.deleteAll();
    }
}
