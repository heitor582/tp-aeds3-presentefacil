package view.product;

import java.util.function.Consumer;

import controller.ProductController;
import model.Product;
import view.View;

public final class SearchByGTINProductView extends View {
    public static final SearchByGTINProductView INSTANCE = new SearchByGTINProductView();
    private Consumer<Product> function = null;

    private SearchByGTINProductView() {
        super("Buscar produto", false);
        this.function = (product) -> handleProduct(product);
    }

    public SearchByGTINProductView setFunction(Consumer<Product> function) {
        this.function = function;
        return this;
    }

    @Override
    protected void viewDisplay() {
        System.out.println("Digite o GTIN do produto a buscar: (ou R para voltar)");
        String code = scanner.nextLine().trim();

        if (code == null || code.isBlank() || (!code.equals("R") && code.length() < 13)) {
            this.alertMessage("GTIN Inválido");
            return;
        } else if (code.equals("R")) {
            return;
        }

        Product foundProduct = ProductController.INSTANCE.findByGTIN(code);
        if (foundProduct != null) {
            this.function.accept(foundProduct);
        } else {
            this.alertMessage("Product with GTIN %s not found", code);
        }
        return;
    }

    private void handleProduct(final Product product) {
        this.nextPage(ProductDetailsView.INSTANCE.set(product.getId()));
    }

}
