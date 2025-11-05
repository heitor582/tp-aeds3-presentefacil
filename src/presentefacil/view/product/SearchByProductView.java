package view.product;

import java.util.List;
import java.util.function.Consumer;
import controller.ProductController;
import model.Product;
import shared.IsNumber;
import view.View;

public final class SearchByProductView extends View {
    public static final SearchByProductView INSTANCE = new SearchByProductView();
    private Consumer<Product> function = null;

    private SearchByProductView() {
        super("Buscar produto por nome", false);
        this.function = (product) -> handleListSelection(product);
    }

    public SearchByProductView setFunction(Consumer<Product> function) {
        this.function = function;
        return this;
    }

    @Override
    protected void viewDisplay() {
        System.out.println("Digite o nome do produto a buscar: (ou R para voltar)");
        String name = scanner.nextLine().trim().toLowerCase();

        if (name == null || name.isBlank()) {
            this.alertMessage("Nome Inválido");
            return;
        } else if (name.equals("R")) {
            return;
        }

        List<Product> products = ProductController.INSTANCE.findByName(name);
        if (products != null && !products.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("\nForam encontrados %d produtos com o nome %s:\n", products.size(), name));

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                sb.append(
                        String.format("(%d) %s %s\n",
                                i + 1,
                                p.getName(),
                                p.isActive() ? "" : "(Desativado)"));
            }
            String option;
            do {
                System.out.printf(
                        """
                                %s
                                (R) Retornar ao menu anterior

                                Opção: """, sb.toString());

                option = scanner.nextLine().trim().toUpperCase();

                switch (option) {
                    case "R":
                        this.back();
                        break;
                    default:
                        if (IsNumber.validate(option)) {
                            int listNumber = Integer.parseInt(option);
                            if (listNumber >= 1 && listNumber <= products.size()) {
                                this.function.accept(products.get(listNumber - 1));
                            }
                        } else {
                            System.out.println("Opção inválida. Tente novamente.");
                        }
                        break;
                }

                System.out.println();

            } while (!option.equals("R"));
        } else {
            this.alertMessage("Product with name %s not found", name);
        }
    }

    private void handleListSelection(final Product product) {
        this.nextPage(ProductDetailsView.INSTANCE.set(product.getId()));
    }

}
