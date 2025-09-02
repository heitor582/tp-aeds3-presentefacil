package view;

import controller.UserController;
import model.User;

public class SignUpView extends View {
    public static final SignUpView INSTANCE = new SignUpView();
    UserController controller = UserController.INSTANCE;

    private SignUpView() {
        super("", false);
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

        User user = User.from(name, email, password, secretQuestion, secretAnswer);

        
        int id = controller.create(user);

        if(id == -1){
            System.out.println("Não foi possivel cadastrar !!!");
        }else{
            System.out.println("Cadastrado com sucesso !!! Para continuar, faça login.");
            this.nextPage(LoginView.INSTANCE);
        }
    }
}
