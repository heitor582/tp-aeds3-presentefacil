package view.productGiftList;

import controller.ProductController;
import controller.ProductGiftListController;
import model.Product;
import model.ProductGiftList;
import shared.IsNumber;
import view.View;

public final class ProductGiftListDetailsView extends View {
    private int productGiftListId = -1;
    private Product product;
    private ProductGiftList productGiftList;
    public static final ProductGiftListDetailsView INSTANCE = new ProductGiftListDetailsView();

    private ProductGiftListDetailsView() {
        super("Detalhes do produto", true);
    }

    public ProductGiftListDetailsView set(final int productGiftListId) {
        this.productGiftList = ProductGiftListController.INSTANCE.findById(productGiftListId);
        this.productGiftListId = productGiftListId;

        if (this.productGiftList == null) {
            System.out.println("Produto não encontrado nesta lista (já pode ter sido removido).");
            this.product = null;
            this.viewName = "Produto inexistente";
            return this;
        }

        this.product = ProductController.INSTANCE.findById(productGiftList.getProductId());

        if (this.product == null) {
            System.out.println("Produto não encontrado no catálogo.");
            this.viewName = "Produto desconhecido";
        } else {
            this.viewName = product.getName();
        }

        return this;
    }

    @Override
    protected void viewDisplay() {
        String option;

        do {
            this.set(productGiftListId);
            this.reload();

            System.out.printf("""
                    NOME: %s
                    GTIN-13: %s
                    DESCRIÇÃO: %s
                    QUANTIDADE: %d
                    OBSERVAÇÕES: %s

                    (1) Alterar a quantidade
                    (2) Alterar as observações
                    (3) Remover o produto desta lista

                    (R) Retornar ao menu anterior

                    Opção: """,
                    product.getGtin(),
                    product.getName(),
                    product.getDescription(),
                    productGiftList.getQuantity(),
                    productGiftList.getDescription()
            );

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "1":
                    changeQuantity();
                    break;
                case "2":
                    editObservation();
                    break;
                case "3":
                    remove();
                    break;
                case "R":
                    this.back();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            System.out.println();

        } while (!option.equals("R"));
    }

    private void editObservation() {
        System.out.println("Digite a nova obs: ");
        String newOBS = scanner.nextLine().trim();
        productGiftList.changeDescription(newOBS);
        ProductGiftListController.INSTANCE.update(productGiftList);
    }

    private void remove() {
        boolean ok = ProductGiftListController.INSTANCE.delete(productGiftListId);
        if (ok) {
            System.out.println("Produto removido com sucesso.");
            this.back();
        } else {
            System.out.println("Falha ao remover o produto.");
        }
    }

    private void changeQuantity() {
        System.out.println("Digite a nova quantidade: ");
        String newQ = scanner.nextLine().trim().toUpperCase();
        if (IsNumber.validate(newQ)) {
            int nq = Integer.parseInt(newQ);
            if (nq < 0)
                System.out.println("Tentar novamente com um valor válido");
            productGiftList.changeQuantity(nq);
            ProductGiftListController.INSTANCE.update(productGiftList);
        } else {
            System.out.println("Tentar novamente com um valor válido");
        }
    }
}
