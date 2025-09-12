package view.giftlist;

import java.util.List;
import controller.GiftListController;
import model.GiftList;
import repository.GlobalMemory;
import shared.IsNumber;
import view.View;

public class MyListView extends View {
    public static final MyListView INSTANCE = new MyListView();
    private MyListView() {
        super("Minhas listas", true);
    }

    @Override
    public void viewDisplay() {
        List<GiftList> list = GiftListController.INSTANCE.findGiftListsByUser(GlobalMemory.getUserId());

        StringBuilder menuBuilder = new StringBuilder("LISTAS\n");

        if (list.isEmpty()) {
            menuBuilder.append("Nenhuma lista encontrada.\n");
        } else {
            for (int i = 0; i < list.size(); i++) {
                GiftList giftList = list.get(i);
                menuBuilder.append(
                    String.format("(%d) %s %s %s\n", i + 1, 
                        giftList.getName(), 
                        giftList.getExpirationDateFormated("-"),
                        giftList.isActive() ? "" : "(Desativado)"
                    )
                );
            }
        }
        
        String option;

        do {
            System.out.printf(
                """
                %s

                (N) Nova lista
                (R) Retornar ao menu anterior

                Opção: """, menuBuilder.toString()
            );

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "N":
                    createNewList();
                    break;
                case "R":
                    this.back();
                    break;
                default:
                    if (IsNumber.validate(option)) {
                        int listNumber = Integer.parseInt(option);
                        if (listNumber >= 1 && listNumber <= list.size()) {
                            handleListSelection(list.get(listNumber-1));
                        }
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                    break;
            }

            System.out.println();

        } while (!option.equals("R"));
    }

    private void handleListSelection(GiftList list) {
        this.nextPage(ListDetailsView.INSTANCE.set(list.getId()));
    }

    private void createNewList() {
        this.nextPage(NewListView.INSTANCE);
    }
}
