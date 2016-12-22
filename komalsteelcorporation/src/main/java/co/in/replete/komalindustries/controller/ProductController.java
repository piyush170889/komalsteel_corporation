package co.in.replete.komalindustries.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.ProductRequestWrapper;
import co.in.replete.komalindustries.beans.RefilInventoryWrapper;
import co.in.replete.komalindustries.beans.UpdateProductWrapper;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.service.ProductService;

@RestController
@RequestMapping(value="/")
public class ProductController { 
	
	@Autowired
	ProductService productService;
	
	/**
	 * Description : Gets the random product details for the page number specified
	 * 
	 * @param pageNum
	 * @param companyInfoId
	 * @return  {@link BaseWrapper}
	 * @throws  {@link Exception}
	 */
	@RequestMapping(value="/productdetails/{pagenum}/{companyinfoid}", method=RequestMethod.GET, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper getProductDetails(@PathVariable("pagenum") int pageNum, @PathVariable("companyinfoid") String companyInfoId) throws Exception{
		
		return productService.getProductDetails(pageNum, companyInfoId);
	}
	
	
	/**
	 * Description : Gets the category and associated sub category list for products of all categories
	 * @param   companyInfoId
	 * @return  {@link BaseWrapper}
	 * @throws  {@link Exception}
	 */
	@RequestMapping(value="/productdetails/{companyinfoid}", method=RequestMethod.GET, produces=KomalIndustriesConstants.APPLICATION_JSON) 
	public BaseWrapper getProductCategoryAndSubcategoryDetails(@PathVariable("companyinfoid") String companyInfoId) throws Exception{
		
		return productService.getProductCategoryAndSubCategoryDetails(companyInfoId);
	}
	
	
	/**
	 * Description : Gets the products details by categoryID and sub categoryID specified
	 * 
	 * @param companyInfoId
	 * @param categoryid
	 * @param subCategoryid
	 * @return  {@link BaseWrapper}
	 * @throws  {@link Exception}
	 */
	@RequestMapping(value="/mproductdetails", method=RequestMethod.GET, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper getProductDetailsByCategoryAndSubcategory(@RequestParam(value="companyinfoid", required=true) String companyInfoId,
			@RequestParam(value="categoryid", required=true) int categoryid, @RequestParam(value="subcategoryid",required=true) int subcategoryid) throws Exception{
		
		return productService.getProductDetailsByCategoryAndSubCategory(companyInfoId, categoryid, subcategoryid, false);
	}
	
	/**
	 * Description : Gets the products details by categoryID and sub categoryID specified
	 * 
	 * @param companyInfoId
	 * @param categoryid
	 * @param subCategoryid
	 * @return  {@link BaseWrapper}
	 * @throws  {@link Exception}
	 */
	@RequestMapping(value="/productdetails", method=RequestMethod.GET, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper getWProductDetailsByCategoryAndSubcategory(@RequestParam(value="companyinfoid", required=true) String companyInfoId,
			@RequestParam(value="categoryid", required=true) int categoryid, @RequestParam(value="subcategoryid",required=true) int subcategoryid) throws Exception{
		
		return productService.getProductDetailsByCategoryAndSubCategory(companyInfoId, categoryid, subcategoryid, true);
	}
	
	/**
	 * Description: Add product to item_master_Dtl Table
	 * @return  {@link BaseWrapper}
	 * @throws {@link Exception} 
	 */
   @RequestMapping(value="/productdetails",method=RequestMethod.POST,consumes=KomalIndustriesConstants.APPLICATION_JSON, produces=KomalIndustriesConstants.APPLICATION_JSON)
    public BaseWrapper addProduct(@Valid @RequestBody ProductRequestWrapper request) throws Exception
    {
	   
      return productService.addProductDetails(request);
	
    }
   
   /**
    * Description: update product details
    * @param {@link id}
    * @return  {@link BaseWrapper}
    * @throws {@link Exception} 
    */
   @RequestMapping(value="/productdetails/{id}",method=RequestMethod.PUT,consumes=KomalIndustriesConstants.APPLICATION_JSON, produces=KomalIndustriesConstants.APPLICATION_JSON)
   public BaseWrapper editProductDetails(@PathVariable ("id") int id,@Valid @RequestBody UpdateProductWrapper request) throws Exception
   {
	  return productService.editProduct(id,request);
   }
   
   /**
    * Description : Refil inventory
    * @param{@link id RefilInventoryWrapper}
    * @return{@link BaseWrapper}
    * @throws{@link Exception}
    */
   @RequestMapping(value="/refilinventory/{id}", method=RequestMethod.PUT,consumes=KomalIndustriesConstants.APPLICATION_JSON, produces=KomalIndustriesConstants.APPLICATION_JSON)
   public BaseWrapper refilProduct(@PathVariable ("id") int id, @Valid @RequestBody RefilInventoryWrapper request ) throws Exception
   {
	   return productService.refilInventory(id,request);
   }
   
   /**
    * Description: Get item Refill history
    * @param{@link id}
    * @return{@link BaseWrapper}
    * @throws{@link Exception}
    * 
    *//*
   @RequestMapping(value="/refilinventory/{id}",method=RequestMethod.GET,produces=KomalIndustriesConstants.APPLICATION_JSON)
   public BaseWrapper getRefilHistory(@PathVariable ("id") int id) throws Exception
   {
	   return productService.getRefillDetails(id);
   }*/
   
   /**
    * Description get product category list
    * @param{@link pagenum}
    * @throws{@link Exception}
    * @return{@link BaseWrapper}
    */
   @RequestMapping(value="/getcategory/{pagenum}",method=RequestMethod.GET,produces=KomalIndustriesConstants.APPLICATION_JSON)
   public BaseWrapper getProductCategory(@PathVariable ("pagenum") int pageNum) throws Exception
   {
	   return productService.productCategoryList(pageNum);
   }
   
   /**
    * Description: get subcategory list
    * @param{@link categoryid}
    * @throws{@link Exception}
    * @return{@link BaseWrapper}
    */
   @RequestMapping(value="/getsubcategory/{categoryid}",method=RequestMethod.GET,produces=KomalIndustriesConstants.APPLICATION_JSON)
   public BaseWrapper getProductSubCategory(@PathVariable ("categoryid") int categoryid) throws Exception
   {
	   return productService.productSubCategoryList(categoryid);
   }
   
   @RequestMapping(value="/categoryandsubcategory/{companyInfoId}", method=RequestMethod.GET)
   public BaseWrapper getCategoryAndSubcategoryDetailsForHome(@PathVariable("companyInfoId") String companyInfoId) throws ServicesException {
	   
	   return productService.getCategoryAndSubCategoryDetails(companyInfoId);
   }
   
   @RequestMapping(value="/categoryandsubcategorydetailsbycategoryid/{categoryId}", method=RequestMethod.GET)
   public BaseWrapper getCategoryAndSubcategoryDetailsByCategoryId(@PathVariable("categoryId") int categoryId) throws ServicesException {
	   
	   return productService.getCategoryAndSubCategoryDetailsByCategoryId(categoryId);
   }
   
   @RequestMapping(value="/categoryandsubcategoryalldetails", method=RequestMethod.GET)
   public BaseWrapper getCategoryAndSubCategoryAllDetails() {
	   
	   return productService.getCategoryAndSubCategoryAllDetails();
   }
}
