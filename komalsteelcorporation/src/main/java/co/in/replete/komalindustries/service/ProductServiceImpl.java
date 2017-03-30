package co.in.replete.komalindustries.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import co.in.replete.komalindustries.beans.AllCategoryAndSubCatDetailsTo;
import co.in.replete.komalindustries.beans.AllCategoryAndSubcatDetailsResposne;
import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.CategoryAndSubCategoryTO;
import co.in.replete.komalindustries.beans.CategoryDetailsTO;
import co.in.replete.komalindustries.beans.CategoryDetailsWrapper;
import co.in.replete.komalindustries.beans.CategoryPaginationTO;
import co.in.replete.komalindustries.beans.CategoryTO;
import co.in.replete.komalindustries.beans.ItemInventoryUpdateTO;
import co.in.replete.komalindustries.beans.ProductAllDetailsTO;
import co.in.replete.komalindustries.beans.ProductCatAndSubCatDetailsResponseWrapper;
import co.in.replete.komalindustries.beans.ProductCatAndSubCatDetailsTO;
import co.in.replete.komalindustries.beans.ProductDetailsByCatAndSubCatResponse;
import co.in.replete.komalindustries.beans.ProductDetailsByCatAndSubCatTO;
import co.in.replete.komalindustries.beans.ProductDetailsTO;
import co.in.replete.komalindustries.beans.ProductDetailsWrapper;
import co.in.replete.komalindustries.beans.ProductRequestWrapper;
import co.in.replete.komalindustries.beans.RefilInventoryWrapper;
import co.in.replete.komalindustries.beans.RefillTO;
import co.in.replete.komalindustries.beans.SingleValueCommonClass;
import co.in.replete.komalindustries.beans.SubCategoryTo;
import co.in.replete.komalindustries.beans.SubCategoryWrapper;
import co.in.replete.komalindustries.beans.UpdateProductTO;
import co.in.replete.komalindustries.beans.UpdateProductWrapper;
import co.in.replete.komalindustries.beans.entity.AppConfiguration;
import co.in.replete.komalindustries.beans.entity.CategoryMaster;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.AdminDAO;
import co.in.replete.komalindustries.dao.ProductDAO;
import co.in.replete.komalindustries.exception.ServicesException;

@Service
@Transactional(rollbackFor=Throwable.class)
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductDAO productDAO;
	
	@Autowired
	Properties responseMessageProperties;
	
	@Autowired
	AdminDAO adminDAO;
	
	/**
	 * Description : Gets random all the product details
	 * 
	 * @param  pageNum
	 * @param  companyInfoId
	 * @return  {@link BaseWrapper}
	 * @throws  {@link Exception}
	 */
	@Override
	public BaseWrapper getProductDetails(int pageNum, String companyInfoId) throws Exception {
		
		//Validate input parameters
		if(pageNum <=0 || (null == companyInfoId || companyInfoId.isEmpty())) {
			throw new Exception(responseMessageProperties.getProperty("error.requiredfields.empty"));
		} else {
			try {
				int maxLimit=0;
			    AppConfiguration appConfiguration=adminDAO.selectConfigurationValue(KomalIndustriesConstants.MAX_LIMIT);
		    	maxLimit=Integer.parseInt(appConfiguration.getConfigVal());
		    	
				//Get the list of products as per the pagenum and company id
				List<ProductDetailsTO> productDetailsList = productDAO.selectProductDetails(pageNum, companyInfoId,maxLimit);
				
				//Prepare the response and return
				ProductDetailsWrapper response = new ProductDetailsWrapper();
				response.setProductDetails(productDetailsList);
				
				return response;
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
			}
		}
	}
	
	
	/**
	 * Description : Gets the category and associated sub category list for products of all categories
	 * 
	 * @param  companyInfoId
	 * @return  {@link BaseWrapper}
	 * @throws  {@link Exception}
	 */
	@Override
	public BaseWrapper getProductCategoryAndSubCategoryDetails(String companyInfoId) throws Exception {
	
		//Validate input parameters
		if(null == companyInfoId || companyInfoId.isEmpty()) 
		{
			throw new Exception(responseMessageProperties.getProperty("error.requiredfields.empty"));
		}
		else {
			
			try {
				//Prepare Response 
				ProductCatAndSubCatDetailsResponseWrapper response = new ProductCatAndSubCatDetailsResponseWrapper();
				List<ProductCatAndSubCatDetailsTO> productCategoryAndSubcategoryList = new ArrayList<ProductCatAndSubCatDetailsTO>();
				
				//Get all product categories
				List<String> categoriesList = productDAO.selectProductCategory(companyInfoId);
				
				//Get sub-category list for each category and add the category and associated sub categories to the response
				for(String category : categoriesList) {
					
				  ProductCatAndSubCatDetailsTO productCatAndSubCatDetailsTO = new ProductCatAndSubCatDetailsTO();
				  List<SingleValueCommonClass> subcategoryList = productDAO.selectProductSubcategoriesByCategory(category, companyInfoId);
				  
				  productCatAndSubCatDetailsTO.setCategory(category);
				  productCatAndSubCatDetailsTO.setSubCategory(subcategoryList);
				  
				  productCategoryAndSubcategoryList.add(productCatAndSubCatDetailsTO);
				}
				
				//Return the response
				response.setProductCatAndSubCatDetailsTOList(productCategoryAndSubcategoryList);
				return response;				
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
			}
		}
	}
	
	/**
	 * Description : Gets the products details by category and sub category specified
	 * 
	 * @param category
	 * @param subCategory
	 * @param companyInfoId
	 * @return  {@link BaseWrapper}
	 * @throws  {@link Exception}
	 */
	@Override
	public BaseWrapper getProductDetailsByCategoryAndSubCategory(String companyInfoId, int category,
			int subCategory, boolean isImageRequired) throws Exception {
		
		//validate input parameters
		if(null == companyInfoId || category<=0 || subCategory<=0 || companyInfoId.isEmpty() ) {
			throw new Exception(responseMessageProperties.getProperty("error.requiredfields.empty"));
		} else {
			//Prepare response and return
			ProductDetailsByCatAndSubCatResponse response = new ProductDetailsByCatAndSubCatResponse();
			List<ProductDetailsByCatAndSubCatTO> itemsListByCatAndSubCat = new ArrayList<ProductDetailsByCatAndSubCatTO>();
			
			try {
				if(isImageRequired) 
				{
					itemsListByCatAndSubCat = productDAO.selectProductdetailsByCatAndSubCatWithImage(category, subCategory, companyInfoId);
				} else {
					itemsListByCatAndSubCat = productDAO.selectProductdetailsByCatAndSubCatWithoutImage(category, subCategory, companyInfoId);
				}
			} 
			catch (DataAccessException dae) 
			{
				dae.printStackTrace();
				throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
			}
			response.setItemsList(itemsListByCatAndSubCat);
			// return response
			return response;
		}
	}

    /**
     * Description: insert product details
     * @param {@link ProductRequestWrapper}
     * @return {@link BaseWrapper}
     * @throws {@link Exception}
     */
	@Override
	public BaseWrapper addProductDetails(ProductRequestWrapper request) throws Exception
	{
		try
		{       // fetch value from request
			    String itemSubCategory=request.getAddProductTO().getItemSubCategory();
			    double thresholdValue=request.getAddProductTO().getThrhldVal();
			    double bookedQuantity=request.getAddProductTO().getBookedQty();
  			    double rejectedScrap=request.getAddProductTO().getRejectedScrapQty();
			    double rejectedRework=request.getAddProductTO().getRejectedReworkQty();
			    double initialQuantity=request.getAddProductTO().getInitialQuantity();
		        String cmpnyInfoId=request.getAddProductTO().getCmpnyInfoId();
		        double mrp=request.getAddProductTO().getMrp();
		        int vendorId=request.getAddProductTO().getVendorId();
		        double perUnitPrice=request.getAddProductTO().getPerUnitCostPrice();
		        double refilPrice=request.getAddProductTO().getRefilPrice();
		        MultipartFile file = request.getAddProductTO().getItemImage();
//		        Float masterCartonPrice = request.getAddProductTO().getMasterCartonPrice();
		        int itemsInMasterCarton = request.getAddProductTO().getItemsInMasterCarton();
		        
		       /* double thresholdValue=0;
			    double bookedQuantity=0;
  			    double rejectedScrap=0;
			    double rejectedRework=0;
			    double initialQuantity=0;
			    double mrp=0;
		        int vendorId=0;
		        double perUnitPrice=0;
		        double refilPrice=0;*/
		        Float masterCartonPrice=0F;
		        String masterCartonQtyRange = request.getAddProductTO().getMasterCartonQtyRange();
		        String masterCartonQtyIncVal = request.getAddProductTO().getMasterCartonQtyIncVal();
		        
		        if(null == masterCartonQtyIncVal || masterCartonQtyIncVal.isEmpty()) {
		        	throw new Exception("Please specify the increment value for master carton quantity");
		        }
		        
		        if(null == masterCartonQtyRange || masterCartonQtyRange.isEmpty()) {
		        	throw new Exception("Please specify the Range that can be ordered for master carton quantity");
		        }
		        
		       // check request for zero integer values
		        if(thresholdValue <= 0)
		        {
		        	throw new Exception(responseMessageProperties.getProperty("error.thresholdValue.required"));
		        }
		        if(initialQuantity <= 0)
		        {
		        	throw new Exception(responseMessageProperties.getProperty("error.initialQuantity.required"));
		        }
		        if(mrp <= 0)
		        {
		        	throw new Exception(responseMessageProperties.getProperty("error.mrp.required"));
		        }
		        if(perUnitPrice <= 0)
		        {
		        	throw new Exception(responseMessageProperties.getProperty("error.perUnitPrice.required"));
		        }
		        if(refilPrice <= 0)
		        {
		        	throw new Exception(responseMessageProperties.getProperty("error.refilPrice.required"));
		        }
		        if(file.isEmpty()) {
		        	throw new Exception("Product Image is required");
		        }
		        /*if(masterCartonPrice <= 0){
		        	throw new Exception("Master Carton Price is required");
		        }*/
		        /*if(itemsInMasterCarton == 0) {
		        	throw new Exception("Items in master carton is required");
		        }*/

		        // ADD Products Details 
				int itemMasterId=productDAO.insertProduct(cmpnyInfoId, request.getAddProductTO().getItemNm(), request.getAddProductTO().getItemCategory(),
						itemSubCategory ,request.getAddProductTO().getItemContentInfo(),request.getAddProductTO().getItemDesc(),request.getAddProductTO().getItemImage(),
						request.getAddProductTO().getItemManufacturer(),request.getAddProductTO().getItemPckgInfo(), request.getAddProductTO().getItemPckgType(), 
						request.getAddProductTO().getOfferDtlsId(), itemsInMasterCarton, masterCartonPrice,"0-" + masterCartonQtyRange.trim(), 
						masterCartonQtyIncVal.trim(), request.getAddProductTO().getItemNo());

				productDAO.insertItemsInventoryDetails(itemMasterId,initialQuantity,mrp,thresholdValue, cmpnyInfoId,bookedQuantity);

				productDAO.insertInventoryRefilDetails(itemMasterId,initialQuantity,mrp, rejectedScrap,rejectedRework, vendorId, refilPrice, perUnitPrice,cmpnyInfoId);
				
				return request;
			
		}
		catch(DataAccessException e)
		{
			e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		}
	
	}

    /**
     * Description: update product details
     * @param {@link UpdateProductWrapper id}
     * @return {@link BaseWrapper}
     * @throws {@link Exception}
     */
	@Override
	public BaseWrapper editProduct(int id,UpdateProductWrapper request) throws Exception 
	{
		int offerDetailsId=request.getUpdateProductTO().getOfferDetailsId();
		
		// check if item is present or not
		try
		{
        	  //update product master details
        	  UpdateProductTO productDetailsToUpdate = request.getUpdateProductTO();
        	  
        	  MultipartFile file = productDetailsToUpdate.getItemImage();
        	  
        	  String itemCategory=  productDetailsToUpdate.getItemCategory();
    		  String itemSubCategory = productDetailsToUpdate.getItemSubCategory();
    		  
        	  if(null == file || file.isEmpty()) {
        		  productDAO.updateProductDetails(id,productDetailsToUpdate.getItemContentInfo(), itemCategory, productDetailsToUpdate.getItemDesc(),
        			  null, productDetailsToUpdate.getItemNm(), productDetailsToUpdate.getManufacturer(), offerDetailsId,
        			  productDetailsToUpdate.getItemPckgInfo(), productDetailsToUpdate.getItemPckgType(), itemSubCategory,
        			  productDetailsToUpdate.getItemsInMasterCarton(),productDetailsToUpdate.getMasterCartonPrice(),productDetailsToUpdate.getMasterCartonQtyRange(),
        			  productDetailsToUpdate.getMasterCartonQtyIncVal(), productDetailsToUpdate.getItemNo());
        	  } else {
        		  byte[] itemImageBytes = file.getBytes();
        		  productDAO.updateProductDetails(id,productDetailsToUpdate.getItemContentInfo(), productDetailsToUpdate.getItemCategory(), productDetailsToUpdate.getItemDesc(),
        				  itemImageBytes, productDetailsToUpdate.getItemNm(), productDetailsToUpdate.getManufacturer(), offerDetailsId,
            			  productDetailsToUpdate.getItemPckgInfo(), productDetailsToUpdate.getItemPckgType(), productDetailsToUpdate.getItemSubCategory(),
            			  productDetailsToUpdate.getItemsInMasterCarton(),productDetailsToUpdate.getMasterCartonPrice(),productDetailsToUpdate.getMasterCartonQtyRange(),
            			  productDetailsToUpdate.getMasterCartonQtyIncVal(), productDetailsToUpdate.getItemNo());
        	  }
        	  //Update Subcategory cache holding this Product 
        	  productDAO.updateCacheWithImage(Integer.parseInt(itemCategory), Integer.parseInt(itemSubCategory), "56");
        	  productDAO.updateCacheWithoutImage(Integer.parseInt(itemCategory), Integer.parseInt(itemSubCategory), "56");
        	  /*Integer productInventoryDetailsId = productDAO.selectItemInventoryId(itemMasterDtlId);
        	  if(null == productInventoryDetailsId) {
        		  throw new Exception(responseMessageProperties.getProperty("error.itemMasterDtlsId.required"));
        	  }
        	  //update product inventory details
        	  productDAO.updateInventoryDetailsById(productInventoryDetailsId, request.getUpdateProductTO().getThresholdVal(), mrp);
        	  */
        	  /*double refilPrice = request.getUpdateProductTO().getRefillPrice();
        	  
        	  if(refilPrice > 0) {
            	  //update product inventory refill details
            	  productDAO.insertRefilDetails(refillQty, 0D, 0D, 0, refilPrice, itemMasterDtlId, request.getUpdateProductTO().getPerUnitCostPrice(),
            			  mrp, "", companyInfoId);
        	  }*/
              return request;
		}
		catch(DataAccessException e)
		{
			e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
			
		}
   }


	/**
	 * Description: update inventory item and insert refil details
	 * @param{@link id RefilInventoryWrapper}
	 * @return{@link BaseWrapper}
	 * @throws{@link Exception}
	 */
	@Override
	public BaseWrapper refilInventory(int id, RefilInventoryWrapper request) throws Exception 
	{

		// fetch values from request
		int itemMasterDtlId=request.getRefilInventoryTO().getItemMasterDtlsId();
		String cmpnyInfoId=request.getRefilInventoryTO().getCmpnyInfoId();
		double avlQty=request.getRefilInventoryTO().getAvlQty();
		double bookedQty=request.getRefilInventoryTO().getBookedQty();
		double threshol=request.getRefilInventoryTO().getThrhldVal();
		double mrp=request.getRefilInventoryTO().getMrp();
//		String shelfCode=request.getRefilInventoryTO().getShelfCode();
		double rejectedScrapQty=request.getRefilInventoryTO().getRejectedScrapQty();
		double rejectedReworkQty= request.getRefilInventoryTO().getRejectedReworkQty();
		int vendorId=request.getRefilInventoryTO().getVendorId();
		double refilPrice=request.getRefilInventoryTO().getRefilPrice();
		double perUnitCostPrice= request.getRefilInventoryTO().getPerUnitCostPrice();
		
		// check if input values zero 
		if(itemMasterDtlId <=0)
		{
			throw new Exception(responseMessageProperties.getProperty("error.itemMAsterDtlId.greaterthanzero"));
		}
		if(avlQty <=0)
		{
			throw new Exception(responseMessageProperties.getProperty("error.avlQty.greaterthanzero"));
		}
		if(bookedQty <=0)
		{
			throw new Exception(responseMessageProperties.getProperty("error.bookedQty.greaterthanzero"));
		}
		if(threshol <=0)
		{
			throw new Exception(responseMessageProperties.getProperty("error.thrshlVal.greaterthanzero"));
		}
		if(mrp <=0)
		{
			throw new Exception(responseMessageProperties.getProperty("error.mrp.greaterthanzero"));
		}
		if(vendorId <=0)
		{
			throw new Exception(responseMessageProperties.getProperty("error.vendorId.greaterthanzero"));
		}
		if(rejectedScrapQty <=0)
		{
			throw new Exception(responseMessageProperties.getProperty("error.rejectedScrapQty.greaterthanzero"));
		}
		if(rejectedReworkQty <=0)
		{
			throw new Exception(responseMessageProperties.getProperty("error.rejectedReworkQty.greaterthanzero"));
		}
		if(refilPrice <=0)
		{
			throw new Exception(responseMessageProperties.getProperty("error.refilPrice.greaterthanzero"));
		}
		if(perUnitCostPrice <=0)
		{
			throw new Exception(responseMessageProperties.getProperty("error.perUnitCostPrice.greaterthanzero"));
		}
		
		try
		{
		   // check if ITEM is exist or not
		   int itemCount=productDAO.selectItem(itemMasterDtlId);
	       if(itemCount !=1)
	       {
	    	   throw new Exception(responseMessageProperties.getProperty("error.item.notexist"));
	       } 
	       else
	       {    // update item inventory 
                productDAO.updateInventoryItem(id,itemMasterDtlId,cmpnyInfoId,avlQty,threshol,mrp);
                // insert refil details 
                productDAO.insertRefilDetails(avlQty,rejectedScrapQty,rejectedReworkQty,vendorId,refilPrice,itemMasterDtlId,perUnitCostPrice,mrp,request.getRefilInventoryTO().getComments(),cmpnyInfoId);
	       }
		
		   return request;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		}
		
	}

    /**
     * Description: Get Refill History of item
     * @param {@link id}
     * @return{@link BaseWrapper}
     * @throws{@link Exception}
     */
	@Override
	public List<RefillTO> getRefillDetails(int id) throws Exception 
	{
		
		try
		{
			// select item id refill count
			int itemCount=productDAO.getRefilCount(id);
			if(itemCount == 0)
			{
				throw new Exception(responseMessageProperties.getProperty("error.item.notrefil"));
			}
			else
			{
		     List<RefillTO> refillTOList;
		     refillTOList=productDAO.selectRefilHistory(id);
		     return refillTOList;
			}
	 }
	 catch(Exception e)
		{
		e.printStackTrace();
		throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		}
	}


	@Override
	public BaseWrapper productCategoryList(int pageNum) throws Exception {
		//Validate input parameters
		if(pageNum <=0 ) {
			throw new Exception(responseMessageProperties.getProperty("error.pagenum.required"));
		} else {
			try {
				int maxLimit=0;
			    AppConfiguration appConfiguration=adminDAO.selectConfigurationValue(KomalIndustriesConstants.MAX_LIMIT);
		    	maxLimit=Integer.parseInt(appConfiguration.getConfigVal());
		    	
		    	// get category count
		    	int categoryCount=productDAO.selectCategoryCount();
				//Get the list of products category as per the pagenum 
				List<CategoryTO> categoryList = productDAO.selectCategoryDetails(pageNum,maxLimit);
				
				//Prepare the response and return
				CategoryDetailsWrapper response = new CategoryDetailsWrapper();
				response.setCategoryList(categoryList);
				
				// set pagination details
				CategoryPaginationTO pagination=new CategoryPaginationTO();
				pagination.setPageNumber(pageNum);
				pagination.setNumOfCategory(categoryCount);
				pagination.setRecordPerPage(maxLimit);
				response.setPagination(pagination);
				
				return response;
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
			}
		}
	}


	/**
	 * Description: get subcategory list
	 */
	@Override
	public BaseWrapper productSubCategoryList(int categoryid) throws Exception {
		//Validate input parameters
				if(categoryid <=0 ) {
					throw new Exception(responseMessageProperties.getProperty("error.category.required"));
				} else {
					try {
				    	
						//Get the list of products category as per the category Id 
						List<SubCategoryTo> subCategoryList = productDAO.selectSubCategoryDetails(categoryid);
						
						//Prepare the response and return
						SubCategoryWrapper response = new SubCategoryWrapper();
						response.setSubCategoryList(subCategoryList);
						
						return response;
					} catch (Exception e) {
						e.printStackTrace();
						throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
					}
				}
	}
	
	@Override
	public void deleteProduct(String id) throws Exception {
		
		ItemMasterDtl itemMasterDtl = productDAO.selectProductDetailsByItemId(id);
		productDAO.deleteProductById(id);	//	Inactivate Product
		
		//Update cache
		productDAO.updateCacheWithImage(Integer.parseInt(itemMasterDtl.getItemCategory()), Integer.parseInt(itemMasterDtl.getItemSubCategory()), "56");
		productDAO.updateCacheWithoutImage(Integer.parseInt(itemMasterDtl.getItemCategory()), Integer.parseInt(itemMasterDtl.getItemSubCategory()), "56");
	}
	

	@Override
	public ProductAllDetailsTO getProductDetailsById(String productId) {
	
		return productDAO.selectProductDetailsById(productId);
	}
	
	@Override
	public byte[] getItemImage(String id) {
		
		return productDAO.selectProductImage(id);
	}
	
	@Override
	public void editProductInventoryDetails(ItemInventoryUpdateTO request) throws ServicesException {
		
		productDAO.updateInventoryDetails(request);
	}
	
	@Override
	public BaseWrapper getCategoryAndSubCategoryDetails(String companyInfoId) throws ServicesException {
		
		//Get all categories
		List<CategoryDetailsTO> categoryDetails = productDAO.selectAllCategoryDetails(companyInfoId);
		
		//Get Active Category from category details
		CategoryDetailsTO activeCategory = categoryDetails.get(0);
		
		//Get all sub categories for a category
		List<CategoryMaster> subCategoryDetails = productDAO.selectSubCategoryDetailsByCategoryId(activeCategory.getId());
		
		//Prepare the response and return
		CategoryAndSubCategoryTO categoryAndSubCategoryDetailsList = new CategoryAndSubCategoryTO(categoryDetails, subCategoryDetails, activeCategory);
		return categoryAndSubCategoryDetailsList;
	}
	
	@Override
	public BaseWrapper getCategoryAndSubCategoryDetailsByCategoryId(int categoryId) throws ServicesException {
		
		//Validate input params
		if(categoryId == 0) {
			throw new ServicesException("Inavlid Category Id");
		}
		
		//Get Active Category from category details
		CategoryDetailsTO activeCategory = productDAO.selectCategoryDetailsById(categoryId);
		
		//Get all sub categories for a category
		List<CategoryMaster> subCategoryDetails = productDAO.selectSubCategoryDetailsByCategoryId(categoryId);
		
		//Prepare the response and return
		CategoryAndSubCategoryTO categoryAndSubCategoryDetailsList = new CategoryAndSubCategoryTO(null, subCategoryDetails, activeCategory);
		return categoryAndSubCategoryDetailsList;
	}
	
	@Override
	public BaseWrapper getCategoryAndSubCategoryAllDetails() {
		
		//Get all category details
		List<CategoryDetailsTO> allCategoryDetails = productDAO.selectAllCategoryDetails(KomalIndustriesConstants.CMPNY_INFO_ID);
		
		List<AllCategoryAndSubCatDetailsTo> allCategoryAndSubCatDetailsTo = new ArrayList<>();
		
		for(CategoryDetailsTO category : allCategoryDetails) {
			//Get all sub categories for a category
			List<CategoryMaster> subCategoryDetails = productDAO.selectSubCategoryDetailsByCategoryId(category.getId());
			
			AllCategoryAndSubCatDetailsTo responseObject = new AllCategoryAndSubCatDetailsTo(category, subCategoryDetails);
			
			allCategoryAndSubCatDetailsTo.add(responseObject);
		}
		
		AllCategoryAndSubcatDetailsResposne response = new AllCategoryAndSubcatDetailsResposne(allCategoryAndSubCatDetailsTo);
		return response;
	}
	
	@Override
	public void activateProduct(String id) {
		
		ItemMasterDtl itemMasterDtl = productDAO.selectProductDetailsByItemId(id);
		productDAO.updateProductDetailsForActivation(id);
		
		//Update cache
		productDAO.updateCacheWithImage(Integer.parseInt(itemMasterDtl.getItemCategory()), Integer.parseInt(itemMasterDtl.getItemSubCategory()), "56");
		productDAO.updateCacheWithoutImage(Integer.parseInt(itemMasterDtl.getItemCategory()), Integer.parseInt(itemMasterDtl.getItemSubCategory()), "56");
	}
}
