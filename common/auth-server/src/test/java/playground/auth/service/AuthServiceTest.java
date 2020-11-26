package playground.auth.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {
    private Logger log= LoggerFactory.getLogger(AuthServiceTest.class);


    @Autowired
    private AuthService authService;

    @Test
    public void login(){
        String token=authService.login("user1","123456");
        log.info("new token: {}",token);
    }

}