package playground.user;

import playground.entity.Product;

public class ProductFallback implements  ProductService {
    @Override
    public Product getProduct() {
        return new Product();
    }
}
