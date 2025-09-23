package view.user;

import controller.UserController;

import shared.StringValidate;
import view.View;

public final class SignUpView extends View {
    public static final SignUpView INSTANCE = new SignUpView();

    private SignUpView() {
        super("Cadastrar", false);
    }

    @Override
    public void viewDisplay() {
        String name;
        String email;
        String password;
        String secretQuestion;
        String secretAnswer;

        System.out.println("Qual é seu nome ?");
        name = scanner.nextLine();

        System.out.println("Qual e seu e-mail ?");
        email = scanner.nextLine();

        System.out.println("Qual é sua senha ?");
        password = scanner.nextLine();

        System.out.println("Qual é sua pergunta secreta ?");
        secretQuestion = scanner.nextLine();

        System.out.println("Qual é a reposta da sua pergunta secreta ?");
        secretAnswer = scanner.nextLine();

        if (StringValidate.isBlank(name) ||
                StringValidate.isBlank(email) ||
                StringValidate.isBlank(password) ||
                StringValidate.isBlank(secretQuestion) ||
                StringValidate.isBlank(secretAnswer)) {
            this.alertMessage("Todos os campos são obrigatórios!");
            return;
        }

        int id = UserController.INSTANCE.create(name, email, password, secretQuestion, secretAnswer);

        if (id == -1) {
            this.alertMessage("Não foi possivel cadastrar !!!");
        } else {
            this.alertMessage("Cadastrado com sucesso !!! Para continuar, faça login.");
        }
    }
}
