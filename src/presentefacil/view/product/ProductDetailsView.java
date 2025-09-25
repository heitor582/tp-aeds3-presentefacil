package view.product;

import java.util.List;
import java.util.stream.Collectors;
import controller.ProductController;
import controller.ProductGiftListController;
import model.GiftList;
import model.Product;
import repository.GlobalMemory;
import shared.Pair;
import view.View;

public final class ProductDetailsView extends View {
    private int id = -1;
    private Product product;
    public static final ProductDetailsView INSTANCE = new ProductDetailsView();

    private ProductDetailsView() {
        super("Detalhes do produto", true);
    }

    public ProductDetailsView set(final int id) {
        this.product = ProductController.INSTANCE.findById(id);
        this.id = id;
        this.viewName = product.getName();
        return this;
    }

    @Override
    protected void viewDisplay() {
        String option;
        List<GiftList> found = ProductGiftListController.INSTANCE.findAllByProductId(id).stream().map(Pair::getSecond)
                .toList();
        List<GiftList> mine = found.stream().filter(gift -> gift.getUserId() == GlobalMemory.getUserId()).toList();
        String list = "";
        if (mine.size() > 0) {
            list = "Aparece nas minhas listas:\n" +
                    mine.stream().map(l -> "- " + l.getName() + " (" + (l.isActive() ? "Ativado" : "Desativado") + ")")
                            .collect(Collectors.joining("\n"));
        }

        do {
            this.set(id);
            this.reload();
            System.out.printf("""
                    GTIN-13: %s
                    NOME: %s
                    DESCRIÇÃO: %s
                    STATUS: %s

                    %s
                    Aparece também em mais %d listas de outras pessoas.

                    (1) Alterar os dados do produto
                    (2) Inativar o produto

                    (R) Retornar ao menu anterior

                    Opção: """,
                    product.getGtin(),
                    product.getName(),
                    product.getDescription(),
                    product.isActive() ? "Ativado" : "Desativado",
                    list,
                    found.size() - mine.size());

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "1":
                    editProductData();
                    break;
                case "2":
                    deactive();
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

    private void editProductData() {
        this.nextPage(EditProductView.INSTANCE.setProductId(id));
    }

    private void deactive() {
        ProductController.INSTANCE.deactivate(product.getId());
    }
}
