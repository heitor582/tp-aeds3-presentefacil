package view.giftlist;

import controller.GiftListController;
import model.GiftList;
import view.View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EditGiftListView extends View {
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
        if (name.isBlank()) {
            name = giftList.getName();
        }

        System.out.print("Nova descrição (atual: " + giftList.getDescription() + "): ");
        String description = scanner.nextLine();
        if (description.isBlank()) {
            description = giftList.getDescription();
        }

        System.out.print("Nova data de expiração (DD/MM/YYYY) (atual: " + 
            giftList.getExpirationDate().map(v-> v.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("sem data") + "): ");
        String expirationInput = scanner.nextLine();
        Optional<LocalDate> expirationDate = giftList.getExpirationDate();

        if (!expirationInput.isBlank()) {
            try {
                expirationDate = Optional.of(LocalDate.parse(expirationInput, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } catch (Exception e) {
                System.out.println("Data inválida. Mantendo a anterior.");
            }
        }

        GiftListController.INSTANCE.update(
            GiftList.from(
                name,
                description,
                giftList.getCreatedAt(),
                expirationDate,
                giftList.getCode(),
                giftList.getUserId(),
                giftList.getId()
            )
        );

        this.alertMessage("Lista atualizada com sucesso!");
    }
}
