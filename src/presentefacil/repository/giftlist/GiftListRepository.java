package repository.giftlist;

import java.util.List;

import model.GiftList;
import repository.DBFile;
import repository.ExtensibleHash;

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
        return List.of();
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
