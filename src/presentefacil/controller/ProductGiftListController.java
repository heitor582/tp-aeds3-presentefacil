package controller;

import java.util.List;
import java.util.stream.Stream;

import model.GiftList;
import model.Product;
import model.ProductGiftList;
import repository.giftlist.GiftListRepository;
import repository.product.ProductRepository;
import repository.productGiftList.ProductGiftListRepository;
import shared.Pair;

public final class ProductGiftListController {
    public static final ProductGiftListController INSTANCE = new ProductGiftListController();
    private ProductGiftListRepository repository;
    private ProductRepository productRepository;
    private GiftListRepository giftListRepository;

    private ProductGiftListController() {
        try {
            this.repository = new ProductGiftListRepository();
            this.productRepository = new ProductRepository();
            this.giftListRepository = new GiftListRepository();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public List<Pair<ProductGiftList, GiftList>> findAllByProductId(final int productId) {
        try {
            return repository.findGiftListsByProductId(productId).stream()
                    .flatMap(it -> {
                        try {
                            GiftList giftList = giftListRepository.read(it.getGiftListId());
                            return Stream.of(new Pair<>(it, giftList));
                        } catch (Exception e) {
                            return Stream.empty();
                        }
                    })
                    .toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<Pair<ProductGiftList, Product>> findAllByGiftListId(final int giftListId) {
        try {
            return repository.findProductsByGiftListId(giftListId).stream()
                    .flatMap(it -> {
                        try {
                            Product product = productRepository.read(it.getProductId());
                            return Stream.of(new Pair<>(it, product));
                        } catch (Exception e) {
                            return Stream.empty();
                        }
                    })
                    .toList();
        } catch (final Exception e) {
            return List.of();
        }
    }

    public ProductGiftList findById(final int productGiftListId) {
        try {
            return repository.read(productGiftListId);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(final ProductGiftList productGiftList) {
        try {
            return repository.update(productGiftList);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(final int productGiftListId) {
        try {
            return repository.delete(productGiftListId);
        } catch (Exception e) {
            return false;
        }
    }

    public int create(final int giftListId, final int productId) {
        try {
            ProductGiftList list = ProductGiftList.create(
                    productId, giftListId);
            return repository.create(list);
        } catch (final Exception e) {
            return -1;
        }
    }

}
