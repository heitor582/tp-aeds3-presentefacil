package view.product;

import controller.ProductController;
import view.View;

public final class NewProductView extends View {
    public static final NewProductView INSTANCE = new NewProductView();

    private NewProductView() {
        super("Criar Novo Produto", false);
    }

    @Override
    protected void viewDisplay() {
        System.out.println("=== Criar Novo Produto ===");

        System.out.print("Nome do Produto: ");
        String name = scanner.nextLine().trim();

        System.out.print("Descrição detalhada: ");
        String description = scanner.nextLine().trim();

        System.out.print("GTIN-13: ");
        String gtin = scanner.nextLine().trim();

        int resultId = ProductController.INSTANCE.create(name, description, gtin);

        if (resultId == -1) {
            this.alertMessage("Erro ao criar o Produto.");
        } else {
            this.alertMessage("Produto criado com sucesso!");
        }
    }
}
