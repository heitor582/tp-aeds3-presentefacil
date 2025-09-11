package view.giftlist;

import controller.GiftListController;
import model.GiftList;
import view.View;

public class ListDetailsView extends View {
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
        // TODO: resolver isso
        this.set(id);
        this.reload();

        String option;

        do {
            String expirationDate = giftList.getExpirationDate().isPresent()
                    ? giftList.getExpirationDate().get().toString()
                    : "(sem data)";
            String menu = String.format("""
                            CÓDIGO: %s
                            NOME: %s
                            DESCRIÇÃO: %s
                            DATA DE CRIAÇÃO: %s
                            DATA LIMITE: %s
                            STATUS: %s
                            
                            (1) Gerenciar produtos da lista
                            (2) Alterar dados da lista
                            (3) Excluir lista
                            
                            (R) Retornar ao menu anterior
                            
                            Opção: """,
                    giftList.getCode(),
                    giftList.getName(),
                    giftList.getDescription(),
                    giftList.getCreatedAt(),
                    expirationDate,
                    giftList.isActive() ? "" : "Desativado"
            );
            System.out.print(menu);

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "1":
                    manageProducts();
                    break;
                case "2":
                    editListData();
                    break;
                case "3":
                    deleteList();
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
        this.alertMessage(">> [Manage products - not implemented yet]");
    }

    private void editListData() {
        this.nextPage(EditGiftListView.INSTANCE.setGiftListId(giftList.getId()));
    }

    private void deleteList() {
        giftList.changeStatus(false);
        GiftListController.INSTANCE.update(giftList);
    }
}
