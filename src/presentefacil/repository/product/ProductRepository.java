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

    public Product findByGTIN(final String gtin) throws Exception {
        int id = -1;
        Product product = null;

        try {
            IdGTINCodeIndexPair pair = this.indirectIndex.read(gtin.hashCode());

            if (pair == null) return null;
            id = pair.getId();

            product = super.read(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return product;
    }
}