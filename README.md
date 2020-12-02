# springcloud-playgroud


## Start
### config repo
https://github.com/jinfang134/springcloud-playground-config


### start common service
```
# compile and build docker image
./gradlew clean build :common:docker
cd docker
docker-compose -f docker-compose.config.yml up -d
# after config-server up, run below code
docker-compose -f docker-compose.yml up -d
```

## prod deploy
```sh
./gradlew dockerBuild
cd docker
docker-compose up --scale user=3
```

## refs
- [Service Registration and Discovery](https://spring.io/guides/gs/service-registration-and-discovery/)
- [Centralized Configuration](https://spring.io/guides/gs/centralized-configuration/)
- [api-gateway](https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.0.0.RELEASE/single/spring-cloud-gateway.html#gateway-starter)
- [openfeign](https://www.fangzhipeng.com/springcloud/2017/06/03/sc03-feign.html)
- [微服务追踪：Spring Cloud Sleuth](https://www.jianshu.com/p/4303385b7512)
- [gradle docker](https://github.com/palantir/gradle-docker)
- [Spring Cloud Security：Oauth2使用入门](https://juejin.cn/post/6844903987137740813#heading-7)
- [](https://juejin.cn/post/6844903832929976328)

## zipkin:
```sh
docker run -d -p 9411:9411 openzipkin/zipkin-slim
```