package com.maxb.notification.controller;


import com.maxb.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/")
@Slf4j
public class NotificationController {


    @Autowired
    private NotificationService notificationService;


    @RequestMapping(value="/test",method = RequestMethod.GET)
    public String test(){
        return "hello there man";
    }


}
