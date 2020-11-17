package playgroud.user;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableEurekaClient
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @GetMapping("/api/user")
    public User getUser() {
        return new User(12L, "test name");
    }

}


@Data
class User {
    private Long id;
    private String name;

    public User(long l, String test_name) {
        this.id = l;
        this.name = test_name;
    }
}
