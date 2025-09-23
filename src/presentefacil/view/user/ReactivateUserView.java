package view.user;

import java.util.List;

import controller.UserController;

import shared.StringValidate;
import view.View;

public final class ReactivateUserView extends View {
    public static final ReactivateUserView INSTANCE = new ReactivateUserView();

    private ReactivateUserView() {
        super("Reativar usuário", false);
    }

    @Override
    public void viewDisplay() {
        String email;
        String password;
        String answer;

        System.out.println("Digite o seu email : ");
        email = scanner.nextLine();

        System.out.println("Digite sua senha : ");
        password = scanner.nextLine();

        if (StringValidate.isBlank(email) || StringValidate.isBlank(password)) {
            this.alertMessage("Todos os campos são obrigatórios!");
            return;
        }

        List<String> secret = UserController.INSTANCE.getUserQuestion(email, password);

        if (secret.isEmpty()) {
            this.alertMessage("Email ou senha incorretos!!!");
            return;
        }

        System.out.println("Confirme a resposta para a pergunta secreta a seguir:");
        System.out.println(secret.get(0) + ": ");
        answer = scanner.nextLine();
        if (!secret.get(1).equals(answer)) {
            this.alertMessage("Incorreto");
            return;
        }

        boolean resp = UserController.INSTANCE.changeStatus(true);
        if (!resp) {
            this.alertMessage("Ocorreu um erro");
            return;
        }

        this.alertMessage("Usuário reativado com sucesso");
    }
}
