package view.productGiftList;

import controller.ProductGiftListController;
import model.Product;
import view.View;
import view.product.SearchByGTINProductView;
import view.product.SearchByProductView;

public final class AddProductView extends View {
    public static final AddProductView INSTANCE = new AddProductView();

    private AddProductView() {
        super("Acrescentar produto", true);
    }

    private int giftListId = -1;

    public AddProductView set(final int giftListId) {
        this.giftListId = giftListId;
        return this;
    }

    @Override
    public void viewDisplay() {
        String option;

        do {
            String menu = """
                    (1) Buscar produtos por GTIN
                    (2) Buscar produtos por Nome
                    (3) Listar todos os produtos

                    (R) Retornar ao menu anterior

                    Opção: """;
            System.out.print(menu);

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "1":
                    searchByGTIN();
                    break;
                case "2":
                    searchByName();
                    break;
                case "3":
                    listAll();
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

    private void searchByGTIN() {
        this.nextPage(SearchByGTINProductView.INSTANCE.setFunction(product -> create(product)));
    }

    private void listAll() {
        this.nextPage(ListAddProductView.INSTANCE.setGiftListId(giftListId));
    }

    private void searchByName() {
        this.nextPage(SearchByProductView.INSTANCE.setFunction(product -> create(product)));
    }

    private void create(final Product product) {
        int newId = ProductGiftListController.INSTANCE.create(giftListId, product.getId());
        if (newId != -1) {
            this.nextPage(ProductGiftListDetailsView.INSTANCE.set(newId));
        } else {
            this.alertMessage("Esse produto já está na lista.");
        }
    }
}
