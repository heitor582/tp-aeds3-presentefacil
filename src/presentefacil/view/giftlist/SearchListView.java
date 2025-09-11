package view.giftlist;

import controller.GiftListController;
import model.GiftList;
import view.View;

public class SearchListView extends View {
    public static final SearchListView INSTANCE = new SearchListView();
    private GiftList foundList;

    private SearchListView() {
        super("Buscar lista", true);
    }

    public SearchListView set(final GiftList giftList) {
        this.foundList = giftList;
        return this;
    }

    @Override
    public void viewDisplay() {
        System.out.println("Digite o código da lista a buscar: ");
        String code = scanner.nextLine().trim();

        if(code == null){
            this.alertMessage("Codigo Inválido");
            return;
        }

        foundList = GiftListController.INSTANCE.findByShareCode(code);
        if(foundList != null) {
            this.nextPage(ListDetailsView.INSTANCE.set(foundList));
        }
        this.alertMessage("List with code %s not found", code);
        return;
    }
}

