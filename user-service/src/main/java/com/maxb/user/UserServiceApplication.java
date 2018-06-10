package com.maxb.user;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableResourceServer
@EnableBinding(Sink.class)
public class UserServiceApplication extends ResourceServerConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                //.antMatchers("/" , "/demo").permitAll()
//                .anyRequest().authenticated();

        http.authorizeRequests()
                .anyRequest().permitAll();
    }

//    @Autowired
//    private UserRepository userRepository;
//
//    @StreamListener(Sink.INPUT)
//    public void loggerSink(BalanceChangeModel orgChange){
//        logger.debug("Received an event for organization id {}" , orgChange.getOrganizationId());
//
//        User user = new User();
//        user.setUsername("test maxb " + new Date().toString());
//
//        userRepository.save(user);
//    }
}
