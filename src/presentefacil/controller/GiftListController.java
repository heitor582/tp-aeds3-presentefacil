package controller;

import repository.giftlist.GiftListRepository;

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

    // public List<GiftList> findGiftListByUserId(int userId) {
    //     return null;
    // }

    // public create(){}
    // public delete(){}
    // public update(){}
    // public findByShareCode(String shareCode){}

}
