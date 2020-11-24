package playground.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthCache {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置权限
     * @param username
     * @param resources
     */
    public void setResources(String username, Set<String> resources) {
        redisTemplate.opsForSet().add(username, resources);
    }

    /**
     * 是否有操作权限
     * @param username
     * @param resource
     * @return
     */
    public boolean hasResource(String username, String resource) {
        return redisTemplate.opsForSet().isMember(username, resource);
    }

}
