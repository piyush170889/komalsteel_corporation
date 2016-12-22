package co.in.replete.komalindustries.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.LocationDtls;
import co.in.replete.komalindustries.service.AdminService;

@RestController
public class Autocomplete_DD {

	@Autowired
	AdminService adminService;
	
	
	@RequestMapping(value="/getCity", method=RequestMethod.GET)
	public List<LocationDtls> getCityListFromStateId(@RequestParam("stateId") String stateId) {
		return adminService.getCityListByStateName(stateId);
	}
	
	@RequestMapping(value="/getProductBySubcategoryId", method=RequestMethod.GET)
	public List<ItemMasterDtl> getProductDetailsBySubCategoryId(@RequestParam("subCatId") String subCatId) {
		return adminService.getProductsListBySubCategoryId(subCatId);
	}


	@RequestMapping(value="/getProductPriceById", method=RequestMethod.GET)
	public ItemMasterDtl getProductMasterCartonPriceById(@RequestParam("productId") String productId) {
		return adminService.getProductmaterCartonPrice(productId);
	}
	
}
