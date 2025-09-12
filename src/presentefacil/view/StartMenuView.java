package view;

import controller.UserController;
import view.user.LoginView;
import view.user.ReactivateUserView;
import view.user.SignUpView;

public class StartMenuView extends View {
    public static final StartMenuView INSTANCE = new StartMenuView();
    UserController controller = UserController.INSTANCE;

    private StartMenuView() {
        super("Start Menu", false);
    }

    @Override
    public void viewDisplay() {
        String option;
        do {             
            String menu = """
                (1) Login
                (2) Novo usuário
                (3) Ativar usuário novamente

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
                    this.reactivate();
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
    private void reactivate() {
        this.nextPage(ReactivateUserView.INSTANCE);
    }
    private void signup()  {
        this.nextPage(SignUpView.INSTANCE);
    } 
}
