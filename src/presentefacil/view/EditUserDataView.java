package view;

import controller.UserController;
import model.User;
import repository.GlobalMemory;

public class EditUserDataView extends View {
    public static final EditUserDataView INSTANCE = new EditUserDataView();

    private EditUserDataView() {
        super("Alterar dados", true);
    }

    @Override
    public void viewDisplay() {
        User user = UserController.INSTANCE.findUserById(GlobalMemory.getUserId());
        if (user == null) {
            System.out.println("Usuário não encontrado!");
            return;
        }

        System.out.print("Novo nome (atual: " + user.getName() + "): ");
        String name = scanner.nextLine();
        if (name.isBlank()) {
            name = user.getName();
        }

        System.out.print("Novo email (atual: " + user.getEmail() + "): ");
        String email = scanner.nextLine();
        if (email.isBlank()) {
            email = user.getEmail();
        }

        System.out.print("Nova senha: ");
        String password = scanner.nextLine();
        if (password.isBlank()) {
            password = user.getHashPassword();
        }

        System.out.print("Nova pergunta de segurança (atual: " + user.getSecretQuestion() + "): ");
        String secretQuestion = scanner.nextLine();
        if (secretQuestion.isBlank()) {
            secretQuestion = user.getSecretQuestion();
        }

        System.out.print("Nova resposta de segurança: ");
        String secretAnswer = scanner.nextLine();

        UserController.INSTANCE.updateUser(
                user.getId(),
                name,
                email,
                password,
                secretQuestion,
                secretAnswer
        );

        System.out.println("Dados atualizados com sucesso!");
    }
}
