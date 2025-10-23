package controller;

import java.util.List;

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
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> findAll() {
        try {
            return this.repository.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public int create(final String name, final String description, final String gtin) {
        try {
            Product p = this.findByGTIN(gtin);
            if (p != null)
                return -1;
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

    public boolean activate(int id) {
        return this.changeStatus(id, true);
    }

    public boolean deactivate(int id) {
        return this.changeStatus(id, false);
    }

    public boolean update(final Product product) {
        try {
            return repository.update(product);
        } catch (Exception e) {
            return false;
        }
    }

    public List<Product> findByName(final String name) {
        try {
            return this.repository.searchByName(name);
        } catch (Exception e) {
            return List.of();
        }
    }
}
