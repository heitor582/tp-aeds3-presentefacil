package view.user;

import java.util.List;

import controller.UserController;
import view.View;

public class ReactivateUserView extends View {
    public static final ReactivateUserView INSTANCE = new ReactivateUserView();

    private ReactivateUserView() {
        super("Reativar usuário", false);
    }

    @Override
    public void viewDisplay() {
        String email;
        String senha;
        String answer;

        System.out.println("Digite o seu email : ");
        email = scanner.nextLine();

        System.out.println("Digite sua senha : ");
        senha = scanner.nextLine();

        List<String> secret = UserController.INSTANCE.getUserQuestion(email, senha);

        if(secret.isEmpty()){
            this.alertMessage("Email ou senha incorretos!!!");
            return;
        }

        System.out.println("Confirme a resposta para a pergunta secreta a seguir:");
        System.out.println(secret.get(0) + ": ");
        answer = scanner.nextLine();
        if(!secret.get(1).equals(answer)){
            this.alertMessage("Incorreto");
            return;
        }

        UserController.INSTANCE.changeStatus(true);

        this.alertMessage("Usuário reativado com sucesso");
    }
}
