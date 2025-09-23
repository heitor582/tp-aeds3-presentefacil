package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import model.GiftList;
import model.ProductGiftList;
import repository.giftlist.GiftListRepository;
import repository.productGiftList.ProductGiftListRepository;
import repository.GlobalMemory;

public final class GiftListController {
    public final static GiftListController INSTANCE = new GiftListController();
    private GiftListRepository repository;
    private ProductGiftListRepository productGiftListRepository;

    private GiftListController() {
        try {
            this.repository = new GiftListRepository();
            this.productGiftListRepository = new ProductGiftListRepository();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public List<GiftList> findGiftListsByUser(final int userId) {
        try {
            return this.repository.findAllByUserId(userId);
        } catch (final Exception e) {
            return List.of();
        }
    }

    public GiftList findById(final int id) {
        try {
            return this.repository.read(id);
        } catch (final Exception e) {
            return null;
        }
    }

    public GiftList findByShareCode(final String shareCode) {
        try {
            return repository.findByShareCode(shareCode);
        } catch (final Exception e) {
            return null;
        }
    }

    public int create(
            final String name,
            final String description,
            final Optional<LocalDate> expirationDate) {
        try {
            GiftList list = GiftList.create(
                    name,
                    description,
                    expirationDate,
                    GlobalMemory.getUserId());
            return repository.create(list);
        } catch (final Exception e) {
            return -1;
        }
    }

    public boolean update(final GiftList list) {
        try {
            return repository.update(list);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(final int listId) {
        try {
            return repository.delete(listId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean changeStatus(final int id, final boolean active) {
        try {
            GiftList giftList = this.findById(id);
            giftList.changeStatus(active);
            return repository.update(giftList);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deactivate(final int id) {
        return this.changeStatus(id, false);
    }

    public boolean changeStatusByUserId(final boolean active) {
        try {
            List<GiftList> giftList = this.findGiftListsByUser(GlobalMemory.getUserId());
            return giftList.stream().allMatch(v -> {
                v.changeStatus(active);
                try {
                    return repository.update(v);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            });
        } catch (final Exception e) {
            return false;
        }
    }

    public List<GiftList> findGiftListsByProductId(final int productId){
        try {
            List<Integer> list = productGiftListRepository.findGiftListsByProductId(productId)
            .stream().map(ProductGiftList::getGiftListId).toList();

            return this.repository.findAllByIdIn(list);
        } catch (final Exception e) {
            return List.of();
        }
    }
}
