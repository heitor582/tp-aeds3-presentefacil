package repository.productGiftList;

import model.ProductGiftList;
import repository.BPlusTree;
import repository.DBFile;
import repository.IdIdIndexPair;

public final class ProductGiftListRepository extends DBFile<ProductGiftList>{
    private BPlusTree<IdIdIndexPair> giftListIndirectIndex;
    private BPlusTree<IdIdIndexPair> productIndirectIndex;
    public ProductGiftListRepository() throws Exception {
        super(ProductGiftList.class);
        this.giftListIndirectIndex = new BPlusTree<IdIdIndexPair>(
            IdIdIndexPair.class.getConstructor(), 
            5, 
            "productGiftlist/giftListId.productGiftListId"
        );
        this.productIndirectIndex = new BPlusTree<IdIdIndexPair>(
            IdIdIndexPair.class.getConstructor(), 
            5, 
            "productGiftlist/productId.productGiftListId"
        );
    }
     public int create(final ProductGiftList list) throws Exception{
        int id = super.create(list);
        this.giftListIndirectIndex.create(new IdIdIndexPair(list.getGiftListId(), id));
        this.productIndirectIndex.create(new IdIdIndexPair(list.getProductId(), id));
        return id;
    }
}
