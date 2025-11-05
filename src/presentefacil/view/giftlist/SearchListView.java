package view.giftlist;

import controller.GiftListController;
import model.GiftList;
import shared.StringValidate;
import view.View;

public final class SearchListView extends View {
    public static final SearchListView INSTANCE = new SearchListView();

    private SearchListView() {
        super("Buscar lista", true);
    }

    @Override
    public void viewDisplay() {
        System.out.println("Digite o código da lista a buscar: (ou R para voltar)");
        String code = scanner.nextLine().trim();

        if (StringValidate.isBlank(code) || (!code.equals("R") && code.length() < 10)) {
            this.alertMessage("Codigo Inválido");
            return;
        } else if (code.equals("R")) {
            return;
        }

        GiftList foundList = GiftListController.INSTANCE.findByShareCode(code);
        if (foundList != null) {
            this.nextPage(ListDetailsView.INSTANCE.set(foundList.getId()));
        } else {
            this.alertMessage("List with code %s not found", code);
        }
        return;
    }
}
