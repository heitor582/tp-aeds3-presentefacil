package view.giftlist;

import controller.GiftListController;
import model.GiftList;
import shared.StringValidate;
import view.View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public final class EditGiftListView extends View {
    public static final EditGiftListView INSTANCE = new EditGiftListView();
    private int giftListId = -1;

    private EditGiftListView() {
        super("Editar Lista de Presentes", true);
    }

    public EditGiftListView setGiftListId(final int giftListId) {
        this.giftListId = giftListId;
        return this;
    }

    @Override
    public void viewDisplay() {
        GiftList giftList = GiftListController.INSTANCE.findById(giftListId);
        if (giftList == null) {
            System.out.println("Lista não encontrada!");
            return;
        }

        System.out.print("Novo nome (atual: " + giftList.getName() + "): ");
        String name = scanner.nextLine();
        if (StringValidate.isBlank(name)) {
            name = giftList.getName();
        }

        System.out.print("Nova descrição (atual: " + giftList.getDescription() + "): ");
        String description = scanner.nextLine();
        if (StringValidate.isBlank(description)) {
            description = giftList.getDescription();
        }

        System.out.print("Nova data de expiração (DD/MM/YYYY) (atual: " +
                giftList.getExpirationDateFormated() + "): ");
        String expirationInput = scanner.nextLine();
        Optional<LocalDate> expirationDate = giftList.getExpirationDate();

        if (StringValidate.isNotBlank(expirationInput)) {
            try {
                expirationDate = Optional
                        .of(LocalDate.parse(expirationInput, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } catch (final Exception e) {
                System.out.println("Data inválida. Mantendo a anterior.");
            }
        }

        String phrase = giftList.isActive() ? "desativar" : "ativar";
        System.out.printf("Deseja %s: (S/N)", phrase);
        String confirmation = scanner.nextLine();
        boolean newStatus = giftList.isActive();
        if (confirmation.toUpperCase().equals("S")) {
            newStatus = !newStatus;
        }

        GiftListController.INSTANCE.update(
                GiftList.from(
                        name,
                        description,
                        giftList.getCreatedAt(),
                        expirationDate,
                        giftList.getCode(),
                        giftList.getUserId(),
                        giftList.getId(),
                        newStatus));

        this.alertMessage("Lista atualizada com sucesso!");
    }
}
