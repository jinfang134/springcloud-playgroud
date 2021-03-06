package playground.gateway.gateway.auth;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthCheckFilter extends AbstractGatewayFilterFactory {
    private static final String BEARER = "Bearer ";


    /**
     * jwt token 密钥，主要用于token解析，签名验证
     */
    @Value("${spring.security.jwt.signingKey}")
    private String signingKey;

    public static final String X_CLIENT_TOKEN_USER = "x-client-token-user";


    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            // 1. 获取token
            String token = request.getHeaders().getFirst("token");
            log.info("当前请求的url:{}, method:{}", request.getURI().getPath(), request.getMethodValue());
            if (Strings.isEmpty(token)) {
                return unauthorized(exchange);
            }
            ServerWebExchange build;
            try {
                Claims claims = getJwt(token);
                log.info("jwt claims: {}", claims.toString());
                ServerHttpRequest host = exchange.getRequest().mutate()
                        .header(X_CLIENT_TOKEN_USER, claims.getSubject())
                        // 中文字符需要编码
                        .build();
                build = exchange.mutate().request(host).build();
                return chain.filter(build);
            } catch (SignatureException | ExpiredJwtException | MalformedJwtException ex) {
                log.error("user token error :{}", ex.getMessage());
            }
            return unauthorized(exchange);
        };
    }

    /**
     * 网关拒绝，返回401
     *
     * @param
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(HttpStatus.UNAUTHORIZED.getReasonPhrase().getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    public Claims getJwt(String jwtToken) {
        if (jwtToken.startsWith(BEARER)) {
            jwtToken = StringUtils.substring(jwtToken, BEARER.length());
        }
        return Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(signingKey.getBytes()) //设置签名的秘钥
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public boolean invalidJwtAccessToken(String authentication) {
        // 是否无效true表示无效
        boolean invalid = Boolean.TRUE;
        try {
            getJwt(authentication);
            invalid = Boolean.FALSE;
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException ex) {
            log.error("user token error :{}", ex.getMessage());
        }
        return invalid;
    }
}