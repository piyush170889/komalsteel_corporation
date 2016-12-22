package co.in.replete.komalindustries.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;

import co.in.replete.komalindustries.beans.CategoryDetailsTO;
import co.in.replete.komalindustries.beans.CategoryTO;
import co.in.replete.komalindustries.beans.ItemInventoryUpdateTO;
import co.in.replete.komalindustries.beans.ProductAllDetailsTO;
import co.in.replete.komalindustries.beans.ProductDetailsByCatAndSubCatTO;
import co.in.replete.komalindustries.beans.ProductDetailsTO;
import co.in.replete.komalindustries.beans.RefillTO;
import co.in.replete.komalindustries.beans.SingleValueCommonClass;
import co.in.replete.komalindustries.beans.SubCategoryTo;
import co.in.replete.komalindustries.beans.entity.CategoryMaster;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.PackagingInfo;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.webcontroller.beans.CategoryMasterTO;
import co.in.replete.komalindustries.webcontroller.beans.WItemDetailsTO;
public interface ProductDAO{

	List<ProductDetailsTO> selectProductDetails(int pageNum, String companyInfoId,int maxLimit) throws DataAccessException, Exception;

	List<String> selectProductCategory(String companyInfoId) throws DataAccessException;
	
	List<SingleValueCommonClass> selectProductSubcategoriesByCategory(String category, String companyInfoId) throws DataAccessException;
	
	List<ProductDetailsByCatAndSubCatTO> selectProductdetailsByCatAndSubCatWithImage(int category, int subCategory, String companyInfoId) throws DataAccessException;

	List<ProductDetailsByCatAndSubCatTO> selectProductdetailsByCatAndSubCatWithoutImage(int category, int subCategory, String companyInfoId) throws DataAccessException;
	
//	void updateSubCategoryCache(int category, int subCategoryId, String companyInfoId);
	
	List<ProductDetailsByCatAndSubCatTO> updateCacheWithImage(int category, int subCategoryId, String companyInfoId);
	
	List<ProductDetailsByCatAndSubCatTO> updateCacheWithoutImage(int category, int subCategoryId, String companyInfoId);
	
	int selectItemCount(String itemName) throws DataAccessException;
	
	int insertProduct(final String cmpnyInfoId,final String itemNm,final String itemCategoryCd,final String itemSubCategoryCd,
			final String itemContentInfo, final String itemDesc, final MultipartFile itemImage, final String itemManufacturer, final String itemPckgInfo,
			final String itemPckgTypeCd, int offerId, int itemsInMasterCarton, Float masterCartonPrice, String masterCartonQtyRange, String masterCartonQtyIncVal, 
			String itemNo)throws DataAccessException, IOException;

	void insertItemsInventoryDetails(int itemMasterId, double initialQuantity, double mrp, double threshholdValue,String cmpnyinfoId, double bookedQty) throws Exception;

	void insertInventoryRefilDetails(int itemMasterId, double initialQuantity, double mrp, double rejectedScrap, double rejectedRework, int vendorId, double refilPrice, double perUnitPrice,String cmpnyInfoId) throws Exception;

	int selectItem(int id) throws DataAccessException ;

	void updateProductDetails(int id, String contentInfo, String itemCategory, String itemDesc, byte[] itemImage,
			String itemName, String manufacturer, int offerDetailsId, String packInfo, String packType,
			String subCategory, int itemInMasterCarton, Float masterCartonPrice, String masterCartonQtyRange, 
			String masterCartonQtyIncVal, String itemNo) throws DataAccessException;

	void updateInventoryItem(int id, int itemMasterDtlId, String cmpnyInfoId, double avlQty,
			double threshol, double mrp) throws DataAccessException;

	void insertRefilDetails(double avlQty, double rejectedScrapQty, double rejectedReworkQty, int vendorId,double refilPrice,
			int itemMasterDtlId, double perUnitCostPrice, double mrp, String comments, String cmpnyInfoId)throws DataAccessException;

	List<RefillTO> selectRefilHistory(int id) throws DataAccessException;

	int getRefilCount(int id) throws DataAccessException;
	
	List<WItemDetailsTO> selectAllItemsInfo();

	Integer selectItemInventoryId(int itemMasterDtlId);

	List<CategoryTO> selectCategoryDetails(int pageNum, int maxLimit) throws Exception;

	int selectCategoryCount() throws DataAccessException;

	List<SubCategoryTo> selectSubCategoryDetails(int categoryid) throws DataAccessException;

    List<CategoryMasterTO>  selectCategoryMasterDetails();
    
    List<CategoryMasterTO>  selectSubCategoryMasterDetails();

	List<PackagingInfo> selectPackagingInfoDetails();

	void deleteProductById(String id);

	ProductAllDetailsTO selectProductDetailsById(String productId);

	byte[] selectProductImage(String id);

	void updateInventoryDetailsById(Integer productInventoryDetailsId, double thresholdVal, double mrp);

	void updateInventoryDetails(ItemInventoryUpdateTO request) throws ServicesException;

	List<CategoryDetailsTO> selectAllCategoryDetails(String companyInfoId);

	List<CategoryMaster> selectSubCategoryDetailsByCategoryId(int categoryid);

	CategoryDetailsTO selectCategoryDetailsById(int categoryId);

	void updateProductDetailsForActivation(String id);

	ItemMasterDtl selectProductDetailsByItemId(String id);

}
