package view;

public class MyDataView extends View {
    public static final MyDataView INSTANCE = new MyDataView();

    private MyDataView() {
        super("Meus dados", true);
    }

    @Override
    public void viewDisplay() {
        String option;

        do {
            String menu = """
                MEUS DADOS
                ID:
                Nome:
                Email:
                Pergunta secreta:

                (1) Alterar dados
                (2) Excluir conta

                (R) Retornar ao menu anterior

                Opção: """;

            System.out.print(menu);

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "1":
                    editUserData();
                    break;
                case "2":
                    deleteAccount();
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

    private void editUserData() {
        System.out.println(">> [Edit user data - not implemented yet]");
    }

    private void deleteAccount() {
        System.out.println(">> [Delete account - not implemented yet]");
    }
}
