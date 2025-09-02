package view;

import controller.UserController;
import model.User;

public class LoginView extends View {
    public static final LoginView INSTANCE = new LoginView();
    UserController controller = new UserController();
    private LoginView() {
        super("", false);
    }

    @Override
    public void viewDisplay() {
        String option;
        do {             
            String menu = """
                (1) Login
                (2) Novo usuário
                (3) Pular Login (only used in dev)

                (S) Sair

                Opção: """;
            System.out.print(menu);
            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "1":
                    login();
                    break;
                case "2":
                    signup();
                    break;
                case "3":
                    this.handleMainMenu();
                    break;
                case "S":
                    this.exit();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            System.out.println();

        } while (!option.equals("S"));
    }

    private void login() {
        String email;
        String senha;

        System.out.println("Digite o seu email : ");
        email = scanner.nextLine();

        System.out.println("Digite sua senha : ");
        senha = scanner.nextLine();

        boolean login = controller.login(email, senha);

        if(login){
            super.nextPage(MainMenuView.INSTANCE);
        }else{
            System.out.println("Senha ou Email incorretos ou inexistentes !!! ");
        }

    }

    private void signup()  {
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
            System.out.println("Cadastrado com sucesso !!!");
        }
    }
  
    private void handleMainMenu() {
        this.nextPage(MainMenuView.INSTANCE);
    }
}
