package view.user;

import controller.UserController;
import model.User;
import repository.GlobalMemory;

import shared.StringValidate;
import view.View;

public final class EditUserDataView extends View {
    public static final EditUserDataView INSTANCE = new EditUserDataView();

    private EditUserDataView() {
        super("Alterar dados", true);
    }

    @Override
    public void viewDisplay() {
        User user = UserController.INSTANCE.findUserById(GlobalMemory.getUserId());
        if (user == null) {
            this.alertMessage("Usuário não encontrado!");
            return;
        }
        System.out.println("Ao não digitar nada novo será utilizado o anterior");
        System.out.print("Novo nome (atual: " + user.getName() + "): ");
        String name = scanner.nextLine();
        if (StringValidate.isBlank(name)) {
            name = user.getName();
        }

        System.out.print("Novo email (atual: " + user.getEmail() + "): ");
        String email = scanner.nextLine();
        if (StringValidate.isBlank(email)) {
            email = user.getEmail();
        }

        System.out.print("Nova senha: ");
        String password = scanner.nextLine();
        if (StringValidate.isBlank(password)) {
            password = user.getHashPassword();
        }

        System.out.print("Nova pergunta de segurança (atual: " + user.getSecretQuestion() + "): ");
        String secretQuestion = scanner.nextLine();
        if (StringValidate.isBlank(secretQuestion)) {
            secretQuestion = user.getSecretQuestion();
        }

        System.out.print("Nova resposta de segurança: ");
        String secretAnswer = scanner.nextLine();
        if (StringValidate.isBlank(secretAnswer)) {
            secretAnswer = user.getSecretAnswer();
        }

        UserController.INSTANCE.updateUser(
                user.getId(),
                name,
                email,
                password,
                secretQuestion,
                secretAnswer);

        this.alertMessage("Dados atualizados com sucesso!");
    }
}
