package playground.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import playground.redis.AuthCache;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息拦截器
 */
@Aspect
@Component
public class AuthAspect {
    private final Logger logger = LoggerFactory.getLogger(AuthAspect.class);

    @Autowired
    private AuthCache authCache;
    /**
     * 服务间调用token用户信息,格式为json
     * {
     * "user_name":"必须有"
     * "自定义key:"value"
     * }
     */
    public static final String X_CLIENT_TOKEN_USER = "x-client-token-user";

    /**
     * 服务间调用的认证token
     */
    public static final String X_CLIENT_TOKEN = "x-client-token";

    @Pointcut("@annotation(playground.annotation.HasResource)")
    public void weblog() {
    }

    @Before("weblog()")
    public void doBefore(JoinPoint joinPoint) {
        //获取请求报文头部元数据
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求对象
        HttpServletRequest request = requestAttributes.getRequest();
        //记录控制器执行前的时间毫秒数
        String username = getUsername(request);

        logger.info("前置通知执行：");
        logger.info("url:" + request.getRequestURL());
        logger.info("method:" + request.getMethod());
        logger.info("ip:" + request.getRemoteAddr());
        logger.info("class_method:" + joinPoint.getSignature().getDeclaringTypeName() +
                "." + joinPoint.getSignature().getName());
        logger.info("username: {}", username);

    }

    private String getAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        //获取方法上的注解
        HasResource annotation = method.getAnnotation(HasResource.class);
        if (annotation != null) {
            String resources = annotation.value();
            return resources;
        }
        return null;
    }

    private String getUsername(HttpServletRequest request) {
        return request.getHeader(X_CLIENT_TOKEN_USER);
    }


    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("weblog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取请求报文头部元数据
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求对象
        HttpServletRequest request = requestAttributes.getRequest();

        String resource = getAnnotation(proceedingJoinPoint);
        String username = getUsername(request);
        logger.info("username: {}", username);
        if (!StringUtils.isEmpty(resource) && !authCache.hasResource(username, resource)) {
            throw new AuthException(resource);
        }
        Object result = proceedingJoinPoint.proceed();
        return result;
    }

  /*  作者：犬小哈
    链接：https://juejin.cn/post/6844903832929976328
    来源：掘金
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/


}
