package view;

import controller.UserController;
import model.User;
import repository.GlobalMemory;

public class MyDataView extends View {
    public static final MyDataView INSTANCE = new MyDataView();

    private MyDataView() {
        super("Meus dados", true);
    }

    @Override
    public void viewDisplay() {
        User user = UserController.INSTANCE.findUserById(GlobalMemory.getUserId());

        String option;

        do {
            String menu = String.format("""
                MEUS DADOS
                ID: %s
                Nome: %s
                Email: %s
                Pergunta secreta: %s

                (1) Alterar dados
                (2) Excluir conta

                (R) Retornar ao menu anterior

                Opção: """,
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getSecretQuestion()
                );

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
