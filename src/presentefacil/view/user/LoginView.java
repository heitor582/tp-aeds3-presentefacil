package view.user;

import controller.UserController;
import shared.NonBlank;
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
        String password;

        System.out.println("Digite o seu email : ");
        email = scanner.nextLine();

        System.out.println("Digite sua senha : ");
        password = scanner.nextLine();

        if (NonBlank.isNotValid(email) || NonBlank.isNotValid(password)) {
            this.alertMessage("Todos os campos são obrigatórios!");
            return;
        }

        boolean login = UserController.INSTANCE.login(email, password);

        if(login){
            super.nextPage(MainMenuView.INSTANCE);
        }else{
            this.alertMessage("Email ou senha incorretos!!!");
        }
    }
}
