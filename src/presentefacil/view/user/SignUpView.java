package view.user;

import controller.UserController;
import model.User;
import view.View;

public class SignUpView extends View {
    public static final SignUpView INSTANCE = new SignUpView();
    UserController controller = UserController.INSTANCE;

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

        if (name.isBlank() || email.isBlank() || password.isBlank() || secretQuestion.isBlank() || secretAnswer.isBlank()) {
            this.alertMessage("Todos os campos são obrigatórios!");
            return;
        }

        User user = User.create(name, email, password, secretQuestion, secretAnswer);

        int id = controller.create(user);

        if(id == -1){
            this.alertMessage("Não foi possivel cadastrar !!!");
        }else{
            this.alertMessage("Cadastrado com sucesso !!! Para continuar, faça login.");
        }
    }
}
