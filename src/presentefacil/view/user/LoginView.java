package view.user;

import controller.UserController;
import view.MainMenuView;
import view.View;

public final class LoginView extends View {
    public static final LoginView INSTANCE = new LoginView();

    private LoginView() {
        super("Login", false);
    }

    @Override
    public void viewDisplay() {
        String email;
        String senha;

        System.out.println("Digite o seu email : ");
        email = scanner.nextLine();

        System.out.println("Digite sua senha : ");
        senha = scanner.nextLine();

        boolean login = UserController.INSTANCE.login(email, senha);

        if(login){
            super.nextPage(MainMenuView.INSTANCE);
        }else{
            this.alertMessage("Email ou senha incorretos!!!");
        }
    }
}
