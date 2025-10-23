package view.product;

import java.util.List;

import controller.ProductController;
import model.Product;
import shared.IsNumber;
import view.View;

public final class ListProductView extends View {
    public static final ListProductView INSTANCE = new ListProductView();
    private int OFFSET = 0;
    private int MAX = 10;
    private List<Product> list = List.of();
    private int maxPage = 0;
    private int page = 0;

    private ListProductView() {
        super("Listagem", true);
        set();
    }

    private void set() {
        OFFSET = 0;
        list = ProductController.INSTANCE.findAll();
        MAX = 10;
        maxPage = list.size() > 0 ? (int) Math.ceil((list.size() / (double) MAX)) : 0;
        page = maxPage > 0 ? 1 : 0;
    }

    @Override
    protected void viewDisplay() {
        String option;
        set();
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
        this.nextPage(ProductDetailsView.INSTANCE.set(product.getId()));
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
