package view.giftlist;

import controller.GiftListController;
import model.GiftList;
import view.View;
import view.productGiftList.ProductManagementView;

public final class ListDetailsView extends View {
    public static final ListDetailsView INSTANCE = new ListDetailsView();
    private int id = -1;
    private GiftList giftList;

    private ListDetailsView() {
        super("Detalhes da lista", true);
    }

    public ListDetailsView set(final int id) {
        this.giftList = GiftListController.INSTANCE.findById(id);
        this.id = id;
        this.viewName = giftList.getName();
        return this;
    }

    public void viewDisplay() {
        String option;

        do {
            this.set(id);
            this.reload();

            System.out.printf("""
                    CÓDIGO: %s
                    NOME: %s
                    DESCRIÇÃO: %s
                    DATA DE CRIAÇÃO: %s
                    DATA LIMITE: %s
                    STATUS: %s

                    (1) Gerenciar produtos da lista
                    (2) Alterar dados da lista
                    (3) Desativar lista

                    (R) Retornar ao menu anterior

                    Opção: """,
                    giftList.getCode(),
                    giftList.getName(),
                    giftList.getDescription(),
                    giftList.getCreatedAt(),
                    giftList.getExpirationDateFormated(),
                    giftList.isActive() ? "Ativado" : "Desativado");

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "1":
                    manageProducts();
                    break;
                case "2":
                    editListData();
                    break;
                case "3":
                    deactive();
                    break;
                case "R":
                    this.back();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            System.out.println();

        } while (!option.equals("R"));
    }

    private void manageProducts() {
        this.nextPage(ProductManagementView.INSTANCE.set(id));
    }

    private void editListData() {
        this.nextPage(EditGiftListView.INSTANCE.set(id));
    }

    private void deactive() {
        GiftListController.INSTANCE.deactivate(id);
    }
}
