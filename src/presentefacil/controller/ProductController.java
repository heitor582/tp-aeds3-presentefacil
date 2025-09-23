package controller;

import model.Product;
import repository.product.ProductRepository;

public final class ProductController {
    public static final ProductController INSTANCE = new ProductController();
    private ProductRepository repository;

    private ProductController() {
        try {
            this.repository = new ProductRepository();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public Product findByGTIN(final String gtin) {
        try {
            return repository.findByGTIN(gtin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product findById(final int id) {
        try {
            return this.repository.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int create(final String name, final String description, final String gtin) {
        try {
            Product product = Product.create(name, description, gtin);
            return repository.create(product);
        } catch (final Exception e) {
            return -1;
        }
    }

    public boolean changeStatus(final int id, final boolean active) {
        try {
            Product product = this.findById(id);
            product.changeStatus(active);
            return repository.update(product);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deactivate(int id) {
        return this.changeStatus(id, false);
    }
}
