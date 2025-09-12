package repository.product;

import model.Product;
import repository.DBFile;

public final class ProductRepository extends DBFile<Product>{

    public ProductRepository() throws Exception {
        super(Product.class);
    }
    
}