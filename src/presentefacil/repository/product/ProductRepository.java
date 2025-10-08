package repository.product;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import model.Product;
import repository.DBFile;
import repository.ExtensibleHash;
import repository.InvertedList;
import repository.Search;

public final class ProductRepository extends DBFile<Product> {
    private ExtensibleHash<IdGTINCodeIndexPair> indirectIndex;
    private InvertedList invertedList;
    private Search search;

    public ProductRepository() throws Exception {
        super(Product.class);
        this.indirectIndex = new ExtensibleHash<IdGTINCodeIndexPair>(
                IdGTINCodeIndexPair.class.getConstructor(),
                5,
                "product/id.gtin",
                "product/id.gtin");
        this.invertedList = new InvertedList(
                5, "product/name.d", "product/name.b");
        this.search = new Search(this.invertedList);
    }

    public boolean update(final Product product) {
        try {
            Product oldProduct = this.read(product.getId());
            if (oldProduct == null)
                return false;
            if (!oldProduct.getName().equals(product.getName())) {
                this.search.delete(oldProduct.getName(), product.getId());
                this.search.delete(product.getName(), product.getId());
            }
            return super.update(product);
        } catch (Exception e) {
            return false;
        }
    }

    public int create(final Product product) throws Exception {
        int id = super.create(product);

        this.indirectIndex.create(IdGTINCodeIndexPair.create(id, product.getGtin()));
        this.search.create(product.getName().toLowerCase(), id);

        return id;
    }

    public Product findByGTIN(final String gtin) throws Exception {
        int id = -1;
        Product product = null;

        try {
            IdGTINCodeIndexPair pair = this.indirectIndex.read(gtin.hashCode());

            if (pair == null)
                return null;
            id = pair.getId();

            product = super.read(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return product;
    }

    public int count() throws Exception {
        return this.read().size();
    }

    public boolean delete(final int id) throws Exception {
        Product product = this.read(id);
        if (product == null)
            return false;
        this.indirectIndex.delete(product.getGtin().hashCode());
        this.search.delete(product.getName().toLowerCase(), id);
        return super.delete(id);
    }

    public List<Product> searchByName(final String name) throws Exception {
        List<Integer> ids = this.search.search(name);

        return ids.stream()
                .map(t -> {
                    try {
                        return super.read(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}