package repository.productGiftList;

import java.util.ArrayList;
import java.util.List;

import model.ProductGiftList;
import repository.BPlusTree;
import repository.DBFile;
import repository.IdIdIndexPair;

public final class ProductGiftListRepository extends DBFile<ProductGiftList> {
    private BPlusTree<IdIdIndexPair> giftListIndirectIndex;
    private BPlusTree<IdIdIndexPair> productIndirectIndex;

    public ProductGiftListRepository() throws Exception {
        super(ProductGiftList.class);
        this.giftListIndirectIndex = new BPlusTree<IdIdIndexPair>(
                IdIdIndexPair.class.getConstructor(),
                5,
                "productGiftlist/giftListId.productGiftListId");
        this.productIndirectIndex = new BPlusTree<IdIdIndexPair>(
                IdIdIndexPair.class.getConstructor(),
                5,
                "productGiftlist/productId.productGiftListId");
    }

    public int create(final ProductGiftList list) throws Exception {
        int id = super.create(list);
        this.giftListIndirectIndex.create(new IdIdIndexPair(list.getGiftListId(), id));
        this.productIndirectIndex.create(new IdIdIndexPair(list.getProductId(), id));
        return id;
    }

    public List<ProductGiftList> findGiftListsByProductId(int productId) throws Exception {
        List<ProductGiftList> giftListsProduct = new ArrayList<ProductGiftList>();
        IdIdIndexPair searchPair = new IdIdIndexPair(productId, -1);
        List<IdIdIndexPair> pairs = this.productIndirectIndex.read(searchPair);
        for (IdIdIndexPair pair : pairs) {
            ProductGiftList productGiftList = super.read(pair.getID2());
            if (productGiftList != null) {
                giftListsProduct.add(productGiftList);
            }
        }
        return giftListsProduct;
    }

    public List<ProductGiftList> findProductsByGiftListId(int giftListId) throws Exception {
        List<ProductGiftList> giftListsProduct = new ArrayList<ProductGiftList>();
        IdIdIndexPair searchPair = new IdIdIndexPair(giftListId, -1);
        List<IdIdIndexPair> pairs = this.giftListIndirectIndex.read(searchPair);
        for (IdIdIndexPair pair : pairs) {
            ProductGiftList productGiftList = super.read(pair.getID2());
            if (productGiftList != null) {
                giftListsProduct.add(productGiftList);
            }
        }
        return giftListsProduct;
    }
}
