package playground.auth.service;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import playground.auth.service.AuthCache;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthCacheTest {

    @Autowired
    AuthCache authCache;

    @Test
    public void testResouces(){
        String username="test_name";
        Set<String> resouceSet= Sets.newHashSet("user:add","user:delete");
        authCache.setResources(username,resouceSet);
        assertThat(authCache.hasResource(username,"user:add")).isTrue();
    }

}