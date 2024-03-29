package co.in.replete.komalindustries.webcontroller;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.in.replete.komalindustries.beans.AddProductTO;
import co.in.replete.komalindustries.beans.ItemInventoryUpdateTO;
import co.in.replete.komalindustries.beans.ProductAllDetailsTO;
import co.in.replete.komalindustries.beans.ProductRequestWrapper;
import co.in.replete.komalindustries.beans.UpdateProductTO;
import co.in.replete.komalindustries.beans.UpdateProductWrapper;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.AdminDAO;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.service.ProductService;

@Controller
public class WProductController {

	@Autowired
	ProductService productService;
	
	@Autowired
	Properties responseMessageProperties;
	
	@Autowired
	AdminDAO adminDAO;
	
	/* ADD, EDIT, VIEW Product Details */
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/wproductdetails", method=RequestMethod.GET)
	public String productDetailsView(Model model) {
		model = getInventryDetailsModel(model, null, null);
		return "products/ProductDetails";
	}
	
	@RequestMapping(value="/wproductdetails", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("productDetails") AddProductTO productDetails, BindingResult result, Model model, 
			RedirectAttributes redAttr) {
		
		ProductRequestWrapper productRequestWrapper = new ProductRequestWrapper();
		productRequestWrapper.setAddProductTO(productDetails);
		try {
			redAttr.addFlashAttribute("num", 0);
			productService.addProductDetails(productRequestWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
			return "redirect:wproductdetails";
		}
		
//		model = getInventryDetailsModel(model, KomalIndustriesConstants.SUCCESS_MSSG_LABEL, responseMessageProperties.getProperty("success.product.add"));
		model.addAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, responseMessageProperties.getProperty("success.product.add"));
		return "redirect:wproductdetails";
	}
	
	@RequestMapping(value="/productDetailsForEdit", method=RequestMethod.GET)
	public String productDetailsProcessEdit(@RequestParam("productId") String productId, Model model) {
		
		try {
			if(null == productId || productId.isEmpty()) {
				throw new Exception("product Id is invalid");
			} else {
				ProductAllDetailsTO productAlldetails = productService.getProductDetailsById(productId);
				productAlldetails.setRefillDetails(productService.getRefillDetails(Integer.parseInt(productId)));
				model.addAttribute("productAllDetails", productAlldetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model = getInventryDetailsModel(model, KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
			return "products/ProductDetails";
		}
		
		return "products/ProductAllDetails";
	}
	
	@RequestMapping(value="/editproductdetails", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("updateProductDetails") UpdateProductTO updateProductTO, BindingResult result, Model model,
			RedirectAttributes redAttr, HttpServletRequest servletRequest) {
		UpdateProductWrapper updateProductRequestWrapper = new UpdateProductWrapper();
		updateProductRequestWrapper.setUpdateProductTO(updateProductTO);
		try {
			redAttr.addFlashAttribute("num", Integer.parseInt(servletRequest.getParameter("pageNumDetail"))-1);
			productService.editProduct(updateProductTO.getProductId(), updateProductRequestWrapper);
//			model = getInventryDetailsModel(model, KomalIndustriesConstants.SUCCESS_MSSG_LABEL, "Product details updated successfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
			return "redirect:wproductdetails";
		}
		
		model.addAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, responseMessageProperties.getProperty("success.product.update"));
		return "redirect:wproductdetails";
	}
	
	@RequestMapping(value="/DeleteProduct", method=RequestMethod.GET)
	public String productDetailsProcessDelete(@RequestParam("id") String id,@RequestParam("num") int pageNum, Model model,
			RedirectAttributes redAttr) {
		try {
			redAttr.addFlashAttribute("num", pageNum-1);
			productService.deleteProduct(id);
			model.addAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, "Product with id : " + id + " Inactivated successfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return "redirect:wproductdetails";
	}
	

	@RequestMapping(value="/activateproduct", method=RequestMethod.GET)
	public String productDetailsProcessActivation(@RequestParam("id") String id,@RequestParam("num") int pageNum, Model model,
			RedirectAttributes redAttr) {
		try {
			redAttr.addFlashAttribute("num", pageNum-1);
			productService.activateProduct(id);
			model.addAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, "Product with id : " + id + " Activated successfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
		}
		return "redirect:wproductdetails";
	}
	
	
	@RequestMapping(value="/productDetailsAll", method=RequestMethod.GET)
	public String doImageTest(ModelMap model) {
		return "products/ProductAllDetails";
	}
	
	
	@RequestMapping(value="/productInventoryDetails", method=RequestMethod.POST)
	public String productInventoryDetails(@ModelAttribute("editInventory") ItemInventoryUpdateTO request, Model model) {
		String returnViewURL = "redirect:wproductdetails";
		
		try {
			if(null == request.getInvProdMrp() || request.getInvProdMrp().isEmpty() || null == request.getInvProdRefillDt() || request.getInvProdRefillDt().isEmpty() ||
					null == request.getInvProdRefillQty() || request.getInvProdRefillQty().isEmpty() || request.getItemMasterDtlsId() == 0) {
				throw new ServicesException("Required Values Are empty");
			}
			productService.editProductInventoryDetails(request);
			model = getInventryDetailsModel(model, KomalIndustriesConstants.SUCCESS_MSSG_LABEL, 
					"Inventory Details for : " + request.getItemName() + " updated successfully");
		} catch(Exception e) {
			e.printStackTrace();
			model = getInventryDetailsModel(model, KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return returnViewURL;
	}
	
	/* ADD, EDIT, VIEW, DELETE Product Details */
	
/**** HELPER METHODS ****/
	
	private Model getInventryDetailsModel(Model model, String messageType, String message) {
		model.addAttribute("productDetails", new AddProductTO());
		model.addAttribute("updateProductDetails", new UpdateProductTO());
		model.addAttribute("categoryList", adminDAO.getCategoryList());
		model.addAttribute("editInventory", new ItemInventoryUpdateTO());
		//TODO Put appropriate data
		model.addAttribute("itemsSubCategoryList", adminDAO.getSubCategoryList());
		model.addAttribute("itemsPackingInfoList", adminDAO.getPackingInfoList());
		if(null != messageType) {
		  if(messageType.equals(KomalIndustriesConstants.ERROR_MSSG_LABEL)) {
		    model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, message);
		  } else if(messageType.equals(KomalIndustriesConstants.SUCCESS_MSSG_LABEL)) {
			model.addAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, message);
		  }
		}
		
		return model;
	}
	
}
