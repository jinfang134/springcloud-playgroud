package playground.annotation;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * open feign interceptor to add request header.
 */
@Component
public class MyRequestInterceptor implements RequestInterceptor {
    public static final String X_CLIENT_TOKEN_USER = "x-client-token-user";

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求对象
        HttpServletRequest request = requestAttributes.getRequest();
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put(X_CLIENT_TOKEN_USER, Collections.singletonList(request.getHeader(X_CLIENT_TOKEN_USER)));
        template.headers(headers);
    }

}
