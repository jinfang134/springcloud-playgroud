# springcloud-playgroud


## Start
### start common service
```
cd docker
docker-compose -f docker-compose.config.yml -d up
docker-compose -f docker-compose.yml -d up
```

## refs
- [Service Registration and Discovery](https://spring.io/guides/gs/service-registration-and-discovery/)
- [Centralized Configuration](https://spring.io/guides/gs/centralized-configuration/)
- [api-gateway](https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.0.0.RELEASE/single/spring-cloud-gateway.html#gateway-starter)
- [openfeign](https://www.fangzhipeng.com/springcloud/2017/06/03/sc03-feign.html)
- [微服务追踪：Spring Cloud Sleuth](https://www.jianshu.com/p/4303385b7512)
- [gradle docker](https://github.com/palantir/gradle-docker)

## zipkin:
```sh
docker run -d -p 9411:9411 openzipkin/zipkin-slim
```