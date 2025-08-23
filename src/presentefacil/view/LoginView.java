package view;

import dao.UserDAO;
import model.User;

public class LoginView extends View {
    public static final LoginView INSTANCE = new LoginView();
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
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Senha: ");
            String password = scanner.nextLine().trim();

            User user = UserDAO.findByEmail(email);

            if (user != null) { // != temporário
                System.out.println("Usuário não encontrado.");
                return;
            }

            String hash = UserDAO.hashPassword(password);

            String userHash = ""; // user.getHashPassword();

            if (hash.equals(userHash)) {
                this.handleMainMenu();
            } else {
                System.out.println("Senha incorreta.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void signup() {
        try {
            System.out.print("Nome: ");
            String name = scanner.nextLine().trim();
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Senha: ");
            String password = scanner.nextLine().trim();
            System.out.print("Pergunta secreta: ");
            String question = scanner.nextLine().trim();
            System.out.print("Resposta secreta: ");
            String answer = scanner.nextLine().trim();

            if (UserDAO.findByEmail(email) != null) {
                System.out.println("Já existe um usuário com esse email.");
                return;
            }

            String hash = UserDAO.hashPassword(password);
            User user = User.from(name, email, hash, question, answer);
            UserDAO.save(user);
            System.out.println("Usuário criado com sucesso!\n");
            this.viewDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMainMenu() {
        this.nextPage(MainMenuView.INSTANCE);
    }
}
