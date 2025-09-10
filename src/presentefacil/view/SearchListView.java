package view;

import model.GiftList;

public class SearchListView extends View {
    public static final SearchListView INSTANCE = new SearchListView();
    private GiftList foundList;

    private SearchListView() {
        super("Buscar lista", true);
    }

    public SearchListView set(final GiftList giftList) {
        this.foundList = giftList;
        return this;
    }

    @Override
    public void viewDisplay() {
        String option;

        if (foundList == null) {
            System.out.print("Digite o código da lista a buscar: ");
            String code = scanner.nextLine().trim();

            System.out.println(">> [Buscar lista por código '" + code + "' - not implemented yet]");
            this.back();
            return;
        }

        do {
            String menu = """
                LISTA ENCONTRADA

                CÓDIGO:
                NOME:
                DESCRIÇÃO:
                DATA DE CRIAÇÃO:
                DATA LIMITE:

                (R) Retornar ao menu anterior

                Opção: """;
            System.out.print(menu);

            option = scanner.nextLine().trim().toUpperCase();

            if (option.equals("R")) {
                this.back();
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }

            System.out.println();

        } while (!option.equals("R"));
    }
}

