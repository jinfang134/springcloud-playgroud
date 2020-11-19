package playground.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product{
    private Long id;
    private String productName;

    Product(long id,String productName){
        this.id=id;
        this.productName=productName;
    }
}