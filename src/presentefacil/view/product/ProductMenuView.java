package view.product;

import view.View;

public final class ProductMenuView extends View {
    public static final ProductMenuView INSTANCE = new ProductMenuView();
    private ProductMenuView() {
        super("Produtos", true);
    }

    @Override
    public void viewDisplay() {
        String option;

        do {
            String menu = """
                (1) Buscar produtos por GTIN
                (2) Listar todos os produtos
                (3) Cadastrar um novo produto

                (R) Retornar ao menu anterior

                Opção: """;
            System.out.print(menu);

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "1":
                    searchByGTIN();
                    break;
                case "2":
                    listAllProducts();
                    break;
                case "3":
                    newProducts();
                    break;
                case "R":
                    this.back();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            System.out.println();

        } while (!option.equals("S"));
    }

    private void searchByGTIN() {
        this.nextPage(SearchProductView.INSTANCE);
    }

    private void listAllProducts() {
        // TODO: fzr
        this.alertMessage(">> [Products - not implemented yet]");
    }

    private void newProducts() {
        this.nextPage(NewProductView.INSTANCE);
    }
}
