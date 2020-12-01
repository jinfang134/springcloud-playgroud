package playground.redis;

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
public class AuthCache {

    public static final String X_CLIENT_TOKEN_USER = "x-client-token-user";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String ROLE_KEY_PREFIX = "role_";
    private static final String RESOURCE_KEY_PREFIX = "resource_";

    /**
     * unit: ms
     */
//    @Value("${spring.security.jwt.expire}")
    private long expire = 3600 * 1000;

    public User getCurrentUser() {
        String userId = getUserId();
        return (User) redisTemplate.opsForValue().get(userId);
    }

    protected String getUserId() {
        //获取请求报文头部元数据
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求对象
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader(X_CLIENT_TOKEN_USER);
    }


    /**
     * 是否有操作权限
     *
     * @param resource
     * @return
     */
    public boolean hasResource(String resource) {
        String key = getResourceKey(getUserId());
        return redisTemplate.opsForSet().isMember(key, resource);
    }

    public boolean hasRole(String role) {
        String key = getRoleKey(getUserId());
        return redisTemplate.opsForSet().isMember(key, role);
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
