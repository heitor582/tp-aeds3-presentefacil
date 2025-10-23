package view.product;

import controller.ProductController;
import model.Product;
import view.View;

public final class SearchByGTINProductView extends View {
    public static final SearchByGTINProductView INSTANCE = new SearchByGTINProductView();

    private SearchByGTINProductView() {
        super("Buscar produto", false);
    }

    @Override
    protected void viewDisplay() {
        System.out.println("Digite o GTIN do produto a buscar: (ou R para voltar)");
        String code = scanner.nextLine().trim().toUpperCase();

        if(code == null || code.isBlank() || (!code.equals("R") && code.length()<13)){
            this.alertMessage("GTIN Inválido");
            return;
        } else if(code.equals("R")){
            return;
        }

        Product foundProduct = ProductController.INSTANCE.findByGTIN(code);
        if(foundProduct != null) {
            this.nextPage(ProductDetailsView.INSTANCE.set(foundProduct.getId()));
        }else{
            this.alertMessage("Product with GTIN %s not found", code);
        }
        return;
    }

    
}
