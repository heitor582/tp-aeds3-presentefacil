package view.user;

import controller.UserController;
import model.User;
import repository.GlobalMemory;
import view.View;

public class MyDataView extends View {
    public static final MyDataView INSTANCE = new MyDataView();

    private MyDataView() {
        super("Meus dados", true);
    }

    @Override
    public void viewDisplay() {
        String option;

        do {
            User user = UserController.INSTANCE.findUserById(GlobalMemory.getUserId());
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

    private void editUserData() { this.nextPage(EditUserDataView.INSTANCE);}

    private void deleteAccount() {
        this.alertMessage(">> [Delete account - not implemented yet]");
    }
}
