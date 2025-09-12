package repository.giftlist;

import java.util.ArrayList;
import java.util.List;

import model.GiftList;
import repository.BPlusTree;
import repository.DBFile;
import repository.ExtensibleHash;

public class GiftListRepository extends DBFile<GiftList>  {
    private BPlusTree<IdIdIndexPair> userIndirectIndex;
    private ExtensibleHash<IdShareCodeIndexPair> indirectIndex;

    public GiftListRepository() throws Exception {
        super(GiftList.class);
        this.userIndirectIndex = new BPlusTree<IdIdIndexPair>(
            IdIdIndexPair.class.getConstructor(), 
            5, 
            "giftlist/userId.giftListId"
        );
        this.indirectIndex = new ExtensibleHash<IdShareCodeIndexPair>(
            IdShareCodeIndexPair.class.getConstructor(), 
            5,
            "giftlist/id.sharecode",
            "giftlist/id.sharecode"
        );
    }

    public int create(final GiftList list) throws Exception{
        int id = super.create(list);
        this.indirectIndex.create(IdShareCodeIndexPair.create(list.getId(), list.getCode()));
        this.userIndirectIndex.create(new IdIdIndexPair(list.getUserId(), id));
        return id;
    }

    public List<GiftList> findAllByUserId(int userId) throws Exception {
        List<GiftList> giftLists = new ArrayList<GiftList>();
        IdIdIndexPair searchPair = new IdIdIndexPair(userId, -1);
        List<IdIdIndexPair> pairs = this.userIndirectIndex.read(searchPair);
        for(IdIdIndexPair pair : pairs){
            GiftList giftList = super.read(pair.getListId());
            if(giftList != null){
                giftLists.add(giftList);
            }
        }
        return giftLists;
    }

    public GiftList findByShareCode(final String shareCode) throws Exception {
        int id = -1;
        GiftList giftList = null;

        try {
            IdShareCodeIndexPair pair = this.indirectIndex.read(shareCode.hashCode());
            
            if (pair == null) return null;
            id = pair.getId();

            giftList = super.read(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return giftList;
    }
}
