package view;

import controller.UserController;

public class LoginMenuView extends View {
    public static final LoginMenuView INSTANCE = new LoginMenuView();
    UserController controller = UserController.INSTANCE;

    private LoginMenuView() {
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
                    this.nextPage(MainMenuView.INSTANCE); //TODO: deletar dps
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
        this.nextPage(LoginView.INSTANCE);
    }

    private void signup()  {
        this.nextPage(SignUpView.INSTANCE);
    }
  
    private void handleMainMenu() {
        this.nextPage(MainMenuView.INSTANCE);
    }
}
