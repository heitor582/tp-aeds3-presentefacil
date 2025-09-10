package repository.giftlist;

import model.GiftList;
import repository.DBFile;

public class GiftListRepository extends DBFile<GiftList>  {
    public GiftListRepository() throws Exception {
        super(GiftList.class);
    }
}
