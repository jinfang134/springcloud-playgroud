package playground.auth.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


public class JwtUtilsTest {

    private JwtUtils jwtUtils = new JwtUtils();

    @Before
    public void init() {
        jwtUtils.setExpire(3600 * 1000);
        jwtUtils.setSigningKey("3fdrew24wwerwr234234");
    }

    @Test
    public void generateToken() {
        System.out.println(jwtUtils.generateToken("test"));
    }

}