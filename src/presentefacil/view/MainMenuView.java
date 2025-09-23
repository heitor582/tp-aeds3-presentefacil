package view;

import controller.UserController;
import view.giftlist.MyListView;
import view.giftlist.SearchListView;
import view.product.ProductMenuView;
import view.user.MyDataView;

public final class MainMenuView extends View {
    public static final MainMenuView INSTANCE = new MainMenuView();

    private MainMenuView() {
        super("Início", true);
    }

    @Override
    public void viewDisplay() {
        String option;

        do {
            String menu = """
                    (1) Meus dados
                    (2) Minhas listas
                    (3) Produtos
                    (4) Buscar lista

                    (S) Sair

                    Opção: """;
            System.out.print(menu);

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "1":
                    handleMyData();
                    break;
                case "2":
                    handleMyLists();
                    break;
                case "3":
                    handleProducts();
                    break;
                case "4":
                    handleSearchList();
                    break;
                case "S":
                    UserController.INSTANCE.logout();
                    this.logout();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            System.out.println();

        } while (!option.equals("S"));
    }

    private void handleMyData() {
        this.nextPage(MyDataView.INSTANCE);
    }

    private void handleMyLists() {
        this.nextPage(MyListView.INSTANCE);
    }

    private void handleProducts() {
        this.nextPage(ProductMenuView.INSTANCE);
    }

    private void handleSearchList() {
        this.nextPage(SearchListView.INSTANCE);
    }
}
