package view.product;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import controller.GiftListController;
import controller.ProductController;
import model.GiftList;
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
        String option;
        List<GiftList> found = GiftListController.INSTANCE.findGiftListsByProductId(id);
        Stream<GiftList> mine = found.stream().filter(gift -> gift.getId() == id);

        String list = mine.map(l -> "- " + l.getName() + " (" + (l.isActive() ? "Ativado" : "Desativado") + ")")
    .collect(Collectors.joining("\n"));

        do {
            this.set(id);
            this.reload();
            
            System.out.printf("""
                    NOME: %s
                    GTIN-13: %s
                    DESCRIÇÃO: %s
                    STATUS: %s

                    Aparece nas minhas listas:
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
                    found.size() - mine.count()
            );

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
        // TODO: Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editProductData'");
    }

    private void deactive() {
        ProductController.INSTANCE.deactivate(product.getId());
    }
}
