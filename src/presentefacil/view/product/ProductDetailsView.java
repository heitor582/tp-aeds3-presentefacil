package view.product;

import controller.ProductController;
import model.Product;
import view.View;

public final class ProductDetailsView extends View {
    private int id = -1;
    private Product product;
    public static final ProductDetailsView INSTANCE = new ProductDetailsView();

    private ProductDetailsView() {
        super("Detalhes do produto", false);
    }

    public ProductDetailsView set(final int id) {
        this.product = ProductController.INSTANCE.findById(id);
        this.id = id;
        this.viewName = product.getName();
        return this;
    }

    @Override
    protected void viewDisplay() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewDisplay'");
    }
}
