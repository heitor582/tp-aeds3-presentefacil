package view;

import shared.IsNumber;

public class MyListView extends View {
    public MyListView() {
        super("Minhas listas", true);
    }

    @Override
    public void viewDisplay() {
        String option;

        do {
            String menu = """
                LISTAS
                (1) nome - data
                .
                .
                .

                (N) Nova lista
                (R) Retornar ao menu anterior

                Opção: """;
            System.out.print(menu);

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "N":
                    createNewList();
                    break;
                case "R":
                    this.back();
                    break;
                default:
                    if (IsNumber.validate(option)) {// verifica se a opcao é um numero e so pode mostrar se o valor retornado for maior que 1
                        int listNumber = Integer.parseInt(option);
                        if (listNumber >= 1 && listNumber <= 4) {
                            handleListSelection(listNumber);
                        }
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                    break;
            }

            System.out.println();

        } while (!option.equals("R"));
    }

    private void handleListSelection(int listNumber) {
        System.out.println(">> [You selected list #" + listNumber + " - not implemented yet]");
        this.nextPage(new ListDetailsView("test"));
    }

    private void createNewList() {
        System.out.println(">> [Create new list - not implemented yet]");
    }
}
