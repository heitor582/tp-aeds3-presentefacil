package repository.product;

import model.Product;
import repository.DBFile;
import repository.ExtensibleHash;

public final class ProductRepository extends DBFile<Product>{
    private ExtensibleHash<IdGTINCodeIndexPair> indirectIndex;
    public ProductRepository() throws Exception {
        super(Product.class);
    }
      public int create(final Product product) throws Exception{
        int id = super.create(product);
        this.indirectIndex.create(IdGTINCodeIndexPair.create(product.getId(), product.getGtin()));
        return id;
    }
}