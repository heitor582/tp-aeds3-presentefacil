package view.giftlist;

import controller.GiftListController;
import model.GiftList;
import view.View;

public class SearchListView extends View {
    public static final SearchListView INSTANCE = new SearchListView();

    private SearchListView() {
        super("Buscar lista", true);
    }

    @Override
    public void viewDisplay() {
        System.out.println("Digite o código da lista a buscar: ");
        String code = scanner.nextLine().trim();

        if(code == null || code.isBlank()){
            this.alertMessage("Codigo Inválido");
            return;
        }

        GiftList foundList = GiftListController.INSTANCE.findByShareCode(code);
        if(foundList != null) {
            this.nextPage(ListDetailsView.INSTANCE.set(foundList));
        }
        this.alertMessage("List with code %s not found", code);
        return;
    }
}

