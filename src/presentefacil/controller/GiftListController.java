package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import model.GiftList;
import repository.giftlist.GiftListRepository;
import repository.GlobalMemory;

public class GiftListController {
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

    public boolean update(GiftList list) {
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
}
