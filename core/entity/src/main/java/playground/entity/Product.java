package playground.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Product{
    private Long id;
    private String productName;

    public Product(){}

    public Product(long id,String productName){
        this.id=id;
        this.productName=productName;
    }
}