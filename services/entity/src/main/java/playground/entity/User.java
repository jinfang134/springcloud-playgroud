package playground.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String message;
    private Product product;

    public User(){}

    public User(long l, String test_name, String message,Product p) {
        this.id = l;
        this.name = test_name;
        this.message = message;
        this.product=p;
    }
}
