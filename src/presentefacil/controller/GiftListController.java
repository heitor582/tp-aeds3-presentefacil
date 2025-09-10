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

    public GiftListController() {
        try {
            this.repository = new GiftListRepository();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<GiftList> findGiftListsByUser(int userId) {
        try {
            return repository.findAllByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public GiftList findByShareCode(String shareCode) {
        try {
            return repository.findByShareCode(shareCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int create(String name, String description, Optional<LocalDate> expirationDate) {
        try {
            GiftList list = GiftList.create(
                name,
                description,
                LocalDate.now(),
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

    public boolean delete(int listId) {
        try {
            return repository.delete(listId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
