package view;

public class ListDetailsView extends View{

    public ListDetailsView(final String name) {
        super(name,true);
    }

    public void viewDisplay() {
        String option;

        do {
            String menu = """
                CÓDIGO: 
                NOME: 
                DESCRIÇÃO: 
                DATA DE CRIAÇÃO: 
                DATA LIMITE: 

                (1) Gerenciar produtos da lista
                (2) Alterar dados da lista
                (3) Excluir lista

                (R) Retornar ao menu anterior

                Opção: """;
            System.out.print(menu);

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "1":
                    manageProducts();
                    break;
                case "2":
                    editListData();
                    break;
                case "3":
                    deleteList();
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

    private void manageProducts() {
        System.out.println(">> [Manage products - not implemented yet]");
    }

    private void editListData() {
        System.out.println(">> [Edit list data - not implemented yet]");
    }

    private void deleteList() {
        System.out.println(">> [Delete list - not implemented yet]");
    }
}
