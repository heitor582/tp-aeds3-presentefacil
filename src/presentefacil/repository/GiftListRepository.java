package repository;

import java.util.List;

import model.GiftList;

public class GiftListRepository extends DBFile<GiftList>  {
    // private BPlusTree<IdIdIndexPair> nameIndex;

    public GiftListRepository() throws Exception {
        super(GiftList.class);
        /* this.nameIndex = new BPlusTree<IdIdIndexPair>(
            IdIdIndexPair.class.getConstructor(), 
            3, 
            "giftlist/id", 
            "giftlist/id"
        ); */
    }

    public int create(final GiftList list) throws Exception{
        int id = super.create(list);
        // this.nameIndex.create(IdIdIndexPair.create(list.getUserId(), id));
        return id;
    }

    public List<GiftList> findAllByUserId(int userId) throws Exception {
        /* List<GiftList> giftLists = new ArrayList<GiftList>();
        IdIdIndexPair searchPair = new IdIdIndexPair(userId, -1);
        List<IdIdIndexPair> pairs = this.nameIndex.read(searchPair);
        for(IdIdIndexPair pair : pairs){
            GiftList giftList = super.read(pair.getId2());
            if(giftList != null){
                giftLists.add(giftList);
            }
        }
        return giftLists; */
        return null;
    }

    public GiftList findByShareCode(String shareCode) throws Exception {
        /* GiftList giftList = null;
        try {
            ShareCodeIndexPair pair = this.shareCodeIndex.read(shareCode.hashCode());
            if (pair == null)
                return null;
            giftList = super.read(pair.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return giftList; */
        return null;
    }
}
