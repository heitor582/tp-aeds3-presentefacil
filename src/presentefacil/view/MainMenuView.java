package view;

public class MainMenuView extends View {
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
                    this.back();
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
        System.out.println(">> [Products - not implemented yet]");
    }

    private void handleSearchList() {
        this.nextPage(SearchListView.INSTANCE);
    }
}
