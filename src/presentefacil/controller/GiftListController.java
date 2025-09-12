package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import model.GiftList;
import repository.giftlist.GiftListRepository;
import repository.GlobalMemory;

public final class GiftListController {
    public final static GiftListController INSTANCE = new GiftListController();
    private GiftListRepository repository;

    private GiftListController() {
        try {
            this.repository = new GiftListRepository();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public List<GiftList> findGiftListsByUser(final int userId) {
        try {
            return this.repository.findAllByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public GiftList findById(final int id) {
        try {
            return this.repository.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GiftList findByShareCode(final String shareCode) {
        try {
            return repository.findByShareCode(shareCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int create(
        final String name, 
        final String description, 
        final Optional<LocalDate> expirationDate
    ) {
        try {
            GiftList list = GiftList.create(
                name,
                description,
                expirationDate,
                GlobalMemory.getUserId()
            );
            return repository.create(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean update(final GiftList list) {
        try {
            return repository.update(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean delete(final int listId) {
        try {
            return repository.delete(listId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean changeStatus(final int id, final boolean active) {
        try {
            GiftList giftList = this.findById(id);
            giftList.changeStatus(active);
            return repository.update(giftList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deactivate(final int id){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
