package view.productGiftList;

import java.util.List;

import controller.ProductController;
import controller.ProductGiftListController;
import model.Product;
import shared.IsNumber;
import view.View;

public final class ListAddProductView extends View {
    public static final ListAddProductView INSTANCE = new ListAddProductView();
    private int OFFSET = 0;
    private int MAX = 10;
    private List<Product> list = List.of();
    private int maxPage = 0;
    private int page = 0;
    private int giftListId = -1;

    private ListAddProductView() {
        super("Listagem", true);
        OFFSET = 0;
        list = ProductController.INSTANCE.findAll();
        MAX = 10;
        maxPage = list.size() > 0 ? (int) Math.ceil((list.size() / (double) MAX)) : 0;
        page = maxPage > 0 ? 1 : 0;
    }

    public void setGiftListId(final int giftListId) {
        this.giftListId = giftListId;
    }

    @Override
    protected void viewDisplay() {
        String option;

        do {
            StringBuilder menuBuilder = new StringBuilder(String.format(
                    "Página %d de %d \n\n", page, maxPage));

            if (list.isEmpty()) {
                menuBuilder.append("Nenhum Produto encontrado.\n");
            } else {
                for (int i = OFFSET; i < Math.min(OFFSET + MAX, list.size()); i++) {
                    Product product = list.get(i);
                    menuBuilder.append(
                            String.format("(%d) %s %s\n", i + 1,
                                    product.getName(),
                                    product.isActive() ? "" : "(Desativado)"));
                }
            }

            menuBuilder.append(String.format(
                    "%s%s",
                    (page > 1) ? "\n(A) Página anterior\n" : "",
                    (page < maxPage) ? "\n(P) Próxima página" : ""));

            System.out.printf(
                    """
                            %s
                            (R) Retornar ao menu anterior

                            Opção: """, menuBuilder.toString());

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "A":
                    previousPageList();
                    break;
                case "P":
                    nextPageList();
                    break;
                case "R":
                    this.back();
                    break;
                default:
                    if (IsNumber.validate(option)) {
                        int listNumber = Integer.parseInt(option);
                        if (listNumber >= 1 && listNumber <= list.size()) {
                            handleListSelection(list.get(listNumber - 1));
                        }
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                    break;
            }

            System.out.println();

        } while (!option.equals("R"));

    }

    private void handleListSelection(final Product product) {
        int newId = ProductGiftListController.INSTANCE.create(giftListId, product.getId());
        if(newId != -1){
            this.nextPage(ProductGiftListDetailsView.INSTANCE.set(newId));
        } else {
            this.alertMessage("Error while adding the product");
        }
    }

    private void previousPageList() {
        if (OFFSET >= MAX) {
            OFFSET -= MAX;
            page--;
        }
    }

    private void nextPageList() {
        if (OFFSET + MAX < list.size()) {
            OFFSET += MAX;
            page++;
        }
    }

}
