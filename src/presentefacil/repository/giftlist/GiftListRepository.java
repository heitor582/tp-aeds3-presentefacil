package repository.giftlist;

import java.util.List;

import model.GiftList;
import repository.DBFile;

public class GiftListRepository extends DBFile<GiftList>  {
    // private BPlusTree<IdIdIndexPair> nameIndex;
    private ExtensibleHash<IdShareCodeIndexPair> indirectIndex;

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
