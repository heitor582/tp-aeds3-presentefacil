package view.product;

import controller.ProductController;
import model.Product;
import shared.StringValidate;
import view.View;

public final class EditProductView extends View {
    public static final EditProductView INSTANCE = new EditProductView();
    private int productId = -1;

    private EditProductView() {
        super("Editar Produto", true);
    }

    public EditProductView setProductId(final int productId) {
        this.productId = productId;
        return this;
    }

    @Override
    public void viewDisplay() {
        Product product = ProductController.INSTANCE.findById(productId);
        if (product == null) {
            System.out.println("Produto não encontrado!");
            return;
        }

        System.out.print("Novo nome (atual: " + product.getName() + "): ");
        String name = scanner.nextLine();
        if (StringValidate.isBlank(name)) {
            name = product.getName();
        }

        System.out.print("Nova descrição (atual: " + product.getDescription() + "): ");
        String description = scanner.nextLine();
        if (StringValidate.isBlank(description)) {
            description = product.getDescription();
        }

        String phrase = product.isActive() ? "desativar" : "ativar";
        System.out.printf("Deseja %s: (S/N)", phrase);
        String confirmation = scanner.nextLine();
        boolean newStatus = product.isActive();
        if (confirmation.toUpperCase().equals("S")) {
            newStatus = !newStatus;
        }

        ProductController.INSTANCE.update(
                Product.from(
                        name,
                        description,
                        product.getGtin(),
                        product.getId(),
                        newStatus));

        this.alertMessage("Lista atualizada com sucesso!");
    }
}
