package view;

import controller.UserController;

public class LoginView extends View {
    public static final LoginView INSTANCE = new LoginView();
    UserController controller = UserController.INSTANCE;

    private LoginView() {
        super("", false);
    }

    @Override
    public void viewDisplay() {
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
