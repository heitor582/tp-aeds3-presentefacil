package view;

import controller.UserController;
import model.User;

public class SignUpView extends View {
    public static final SignUpView INSTANCE = new SignUpView();
    UserController controller = new UserController();

    private SignUpView() {
        super("", false);
    }

    @Override
    public void viewDisplay() {
        String nome;
        String email;
        String senha;
        String perguntaSecreta;
        String respostaSecreta;

        
        System.out.println("Qual é seu nome ?");
        nome = scanner.nextLine();

        System.out.println("Qual e seu e-mail ?");
        email = scanner.nextLine();

        System.out.println("Qual é sua senha ?");
        senha = scanner.nextLine();

        System.out.println("Qual é sua pergunta secreta ?");
        perguntaSecreta = scanner.nextLine();

        System.out.println("Qual é a reposta da sua pergunta secreta ?");
        respostaSecreta = scanner.nextLine();

        User user = User.from(nome, email, senha, perguntaSecreta, respostaSecreta);

        
        int id = controller.create(user);

        if(id == -1){
            System.out.println("Não foi possivel cadastrar !!!");
        }else{
            System.out.println("Cadastrado com sucesso !!! Para continuar, faça login.");
            this.nextPage(LoginView.INSTANCE);
        }
    }
}
