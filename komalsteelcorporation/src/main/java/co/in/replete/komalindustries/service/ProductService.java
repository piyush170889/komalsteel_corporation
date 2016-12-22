package co.in.replete.komalindustries.service;


import java.util.List;
import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.ItemInventoryUpdateTO;
import co.in.replete.komalindustries.beans.ProductAllDetailsTO;
import co.in.replete.komalindustries.beans.ProductRequestWrapper;
import co.in.replete.komalindustries.beans.RefilInventoryWrapper;
import co.in.replete.komalindustries.beans.RefillTO;
import co.in.replete.komalindustries.beans.UpdateProductWrapper;
import co.in.replete.komalindustries.exception.ServicesException;

public interface ProductService {

	BaseWrapper getProductDetails(int pageNum, String companyInfoId) throws Exception;

	BaseWrapper getProductCategoryAndSubCategoryDetails(String companyInfoId) throws Exception;
	
	BaseWrapper getProductDetailsByCategoryAndSubCategory(String companyInfoId, int categoryid, int subcategoryid, boolean isImageRequired) throws Exception;

	BaseWrapper addProductDetails(ProductRequestWrapper request) throws Exception;

	BaseWrapper editProduct(int id,UpdateProductWrapper request) throws Exception;

	BaseWrapper refilInventory(int id, RefilInventoryWrapper request) throws Exception;

	List<RefillTO> getRefillDetails(int id) throws Exception;

	BaseWrapper productCategoryList(int pageNum) throws Exception;

	BaseWrapper productSubCategoryList(int categoryid) throws Exception;

	void deleteProduct(String id) throws Exception;

	ProductAllDetailsTO getProductDetailsById(String productId);

	byte[] getItemImage(String id);

	void editProductInventoryDetails(ItemInventoryUpdateTO request) throws ServicesException;

	BaseWrapper getCategoryAndSubCategoryDetails(String companyInfoId) throws ServicesException;

	BaseWrapper getCategoryAndSubCategoryDetailsByCategoryId(int categoryId) throws ServicesException;

	BaseWrapper getCategoryAndSubCategoryAllDetails();

	void activateProduct(String id);

}
