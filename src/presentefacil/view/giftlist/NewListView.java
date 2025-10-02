package view.giftlist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import controller.GiftListController;
import shared.StringValidate;
import view.View;

public final class NewListView extends View {
    public static final NewListView INSTANCE = new NewListView();

    private NewListView() {
        super("Criar Nova Lista", false);
    }

    @Override
    protected void viewDisplay() {
        System.out.println("=== Criar Nova Lista de Presentes ===");

        System.out.print("Nome da lista: ");
        String name = scanner.nextLine().trim();

        System.out.print("Descrição detalhada: ");
        String description = scanner.nextLine().trim();

        Optional<LocalDate> expirationDate = Optional.empty();
        System.out.print("Deseja definir uma data de expiração? (S/N): ");
        String defineExpiration = scanner.nextLine().trim().toUpperCase();

        if (defineExpiration.equals("S")) {
            System.out.print("Digite a data de expiração (dd/mm/aaaa): ");
            String dateInput = scanner.nextLine().trim();
            try {
                LocalDate expDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                expirationDate = Optional.of(expDate);
            } catch (final Exception e) {
                System.out.println("Data inválida. Nenhuma data de expiração será definida.");
            }
        }

        if (StringValidate.isBlank(name) || StringValidate.isBlank(description)) {
            this.alertMessage("Nome e descrição são obrigatórios!");
            return;
        }

        int resultId = GiftListController.INSTANCE.create(name, description, expirationDate);

        if (resultId == -1) {
            System.out.println("Erro ao criar a lista.");
        } else {
            System.out.println("Lista criada com sucesso!");
        }
    }
}
