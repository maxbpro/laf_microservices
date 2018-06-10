package com.maxb.user.repository;

import com.maxb.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUserId(String userId);

    User findByInviteCode(String inviteCode);
}
