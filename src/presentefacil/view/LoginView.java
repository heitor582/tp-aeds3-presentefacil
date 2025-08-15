package view;

public class LoginView extends View {
    public LoginView() {
        super("", false);
    }

    @Override
    public void viewDisplay() {
        String option;

        do {             
            String menu = """
                (1) Login
                (2) Novo usuário

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
        System.out.println(">> Tela de login (a implementar)");
    }

    private void signup() {
        System.out.println(">> Tela de cadastro de novo usuário (a implementar)");
    }
}
