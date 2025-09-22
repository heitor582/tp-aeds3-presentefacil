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

    public Product findById(int id) {
        try {
            return this.repository.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
