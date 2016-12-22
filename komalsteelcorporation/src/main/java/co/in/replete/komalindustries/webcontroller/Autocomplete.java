package co.in.replete.komalindustries.webcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.in.replete.komalindustries.beans.entity.CategoryMaster;
import co.in.replete.komalindustries.service.AdminService;

@RestController
public class Autocomplete {

	@Autowired
	AdminService adminService;
	
	@RequestMapping(value="/getProductSubcategory", method=RequestMethod.GET)
	public List<CategoryMaster> getProductSubCategoryOnCatId(@RequestParam("catId") String catId) {
		
		return adminService.getSubCategoryOnCatId(catId);
	}
}
