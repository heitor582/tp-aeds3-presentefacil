package view.productGiftList;

import java.util.List;

import controller.ProductGiftListController;
import model.Product;
import model.ProductGiftList;
import shared.IsNumber;
import shared.Pair;
import view.View;

public final class ProductManagementView extends View{
public static final ProductManagementView INSTANCE = new ProductManagementView();
    private int giftListId = -1;
    private List<Pair<ProductGiftList, Product>> list = List.of();
    private ProductManagementView() {
        super("Produtos", true);
        list = ProductGiftListController.INSTANCE.findAllByGiftListId(giftListId);
    }

    public void setGiftListId(final int id){
        this.giftListId = id;
    }

    @Override
    protected void viewDisplay() {
        String option;

        do {
            StringBuilder menuBuilder = new StringBuilder();
            if (list.isEmpty()) {
                menuBuilder.append("Nenhum Produto encontrado.\n");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    ProductGiftList productGiftList = list.get(i).getFirst();
                    Product product = list.get(i).getSecond();
                    menuBuilder.append(
                            String.format("(%d) %s (x%d)\n", i + 1,
                                    product.getName(),
                                    productGiftList.getQuantity()));
                }
            }

            System.out.printf(
                    """
                            %s
                            (A) Acrescentar produto
                            (R) Retornar ao menu anterior

                            Opção: """, menuBuilder.toString());

            option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "A":
                    addProduct();
                    break;
                case "R":
                    this.back();
                    break;
                default:
                    if (IsNumber.validate(option)) {
                        int listNumber = Integer.parseInt(option);
                        if (listNumber >= 1 && listNumber <= list.size()) {
                            handleListSelection(list.get(listNumber - 1).getFirst());
                        }
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                    break;
            }

            System.out.println();

        } while (!option.equals("R"));
    }

    private void addProduct() {
        //TODO:
    }

    private void handleListSelection(final ProductGiftList product) {
        this.nextPage(ProductGiftListDetailsView.INSTANCE.set(product.getId()));
    }
}
