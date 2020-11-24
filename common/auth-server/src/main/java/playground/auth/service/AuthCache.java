package playground.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class AuthCache {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String ROLE_KEY_PREFIX = "role_";
    private static final String RESOURCE_KEY_PREFIX = "resource_";

    /**
     * unit: ms
     */
    @Value("${spring.security.jwt.expire}")
    private long expire = 3600 * 1000;

    /**
     * 设置权限
     *
     * @param username
     * @param resources
     */
    public void setResources(String username, Set<String> resources) {
        String key = getResourceKey(username);
        redisTemplate.opsForSet().add(key, resources.toArray());
        redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }


    /**
     * 是否有操作权限
     *
     * @param username
     * @param resource
     * @return
     */
    public boolean hasResource(String username, String resource) {
        String key = getResourceKey(username);
        return redisTemplate.opsForSet().isMember(key, resource);
    }

    /**
     * 设置角色
     * @param username
     * @param roles
     */
    public void setRoles(String username, Set<String> roles) {
        String key = getRoleKey(username);
        redisTemplate.opsForSet().add(key, roles.toArray());
        redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }

    private String getResourceKey(String username) {
        return RESOURCE_KEY_PREFIX + username;
    }

    private String getRoleKey(String username) {
        return ROLE_KEY_PREFIX + username;
    }

}
