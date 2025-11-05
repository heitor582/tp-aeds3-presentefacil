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
                (2) Buscar produtos por Nome
                (3) Listar todos os produtos
                (4) Cadastrar um novo produto

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
                    listAllProducts();
                    break;
                case "4":
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

    private void searchByName() {
        this.nextPage(SearchByProductView.INSTANCE);
    }

    private void searchByGTIN() {
        this.nextPage(SearchByGTINProductView.INSTANCE);
    }

    private void listAllProducts() {
        this.nextPage(ListProductView.INSTANCE);
    }

    private void newProducts() {
        this.nextPage(NewProductView.INSTANCE);
    }
}
