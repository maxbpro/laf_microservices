package com.maxb.user.controller;

import com.maxb.user.domain.User;
import com.maxb.user.repository.UserRepository;
import com.maxb.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    @RequestMapping(value="/test_data",method = RequestMethod.GET)
    private void addTestData(){
        userRepository.deleteAll();

        User otherGuy = new User();
        otherGuy.setBalance(100);
        otherGuy.setUserId("123456");
        otherGuy.setInviteCode("1111");
        otherGuy.setFirstName("John");
        otherGuy.setLastName("Doe");
        otherGuy.setSex(1);
        otherGuy.setRelation(1);

        userRepository.save(otherGuy);
    }

    @RequestMapping(value="/all",method = RequestMethod.GET)
    private List<User> getUsers(){
        return userService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    private User getUser(@RequestParam String userId){
        return userService.findByUserId(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    private ResponseEntity<User> registerUser(@RequestBody User user){
        userService.register(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/activeInviteCode", method = RequestMethod.POST)
    private ResponseEntity activeInviteCode(@RequestParam String userId, @RequestParam String code){
        log.debug("Invite code activation : "+code + " for " + userId);

        boolean result = userService.activeInviteCode(userId, code);
        if(result){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }


    @RequestMapping(value="/test",method = RequestMethod.GET)
    public String test(){
        return "hello there man";
    }

    @RequestMapping(value="/clear",method = RequestMethod.GET)
    public ResponseEntity clear(){
        userService.clearAll();
        return new ResponseEntity(HttpStatus.OK);
    }
}
