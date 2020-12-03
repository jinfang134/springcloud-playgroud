package playground.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import playground.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class AuthCacheWriter {

    public static final String X_CLIENT_TOKEN_USER = "x-client-token-user";
    private RedisTemplate redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    public AuthCacheWriter(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private static final String ROLE_KEY_PREFIX = "role_";
    private static final String RESOURCE_KEY_PREFIX = "resource_";

    /**
     * unit: ms
     */
    @Value("${spring.security.jwt.expire}")
    private long expire = 3600 * 1000;

    public String getCurrentUser() {
        //获取请求报文头部元数据
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求对象
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader(X_CLIENT_TOKEN_USER);
    }

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
    protected boolean hasResource(String username, String resource) {
        String key = getResourceKey(username);
        return redisTemplate.opsForSet().isMember(key, resource);
    }


    /**
     * 缓存当前用户信息
     *
     * @param key
     * @param user
     */
    public void setUser(String key, User user) {
        user.setPassword("");
        redisTemplate.opsForValue().set(key, user);
        redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }


    /**
     * 设置角色
     *
     * @param username
     * @param roles
     */
    public void setRoles(String username, Set<String> roles) {
        String key = getRoleKey(username);
        redisTemplate.opsForSet().add(key, roles.toArray());
        redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取权限的redis key
     *
     * @param username
     * @return
     */
    private String getResourceKey(String username) {
        return RESOURCE_KEY_PREFIX + username;
    }

    private String getRoleKey(String username) {
        return ROLE_KEY_PREFIX + username;
    }

}
