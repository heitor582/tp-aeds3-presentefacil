package view.productGiftList;

import controller.ProductController;
import controller.ProductGiftListController;
import model.Product;
import shared.StringValidate;
import view.View;

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
                    (2) Listar todos os produtos

                    (R) Retornar ao menu anterior

                    Opção: """;
            System.out.print(menu);

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "1":
                    searchByGTIN();
                    break;
                case "2":
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
        //TODO: Criar uma view depois
        try {
            System.out.println("Digite o GTIN-13: ");
            String gtin = StringValidate.requireMinSize(scanner.nextLine().trim(), 13);

            Product product = ProductController.INSTANCE.findByGTIN(gtin);
            int newId = ProductGiftListController.INSTANCE.create(giftListId, product.getId());
            if(newId != -1){
                this.nextPage(ProductGiftListDetailsView.INSTANCE.set(newId));
            } else {
                throw new Exception();
            }
        } catch (final Exception e) {
            this.alertMessage("Digite um GTIN valido");
        }

    }

    private void listAll() {
        this.nextPage(ListAddProductView.INSTANCE.setGiftListId(giftListId));
    }
}
