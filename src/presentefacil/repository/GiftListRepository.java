package repository;

import model.GiftList;

public class GiftListRepository extends DBFile<GiftList>  {
    public GiftListRepository() throws Exception {
        super(GiftList.class);
    }
}
