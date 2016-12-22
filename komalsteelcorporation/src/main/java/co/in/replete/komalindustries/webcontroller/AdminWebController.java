package co.in.replete.komalindustries.webcontroller;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import co.in.replete.komalindustries.beans.entity.AdminLoginDtls;
import co.in.replete.komalindustries.beans.entity.LocationDtls;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.AdminDAO;
import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.service.ProductService;
import co.in.replete.komalindustries.utils.CommonUtility;
import co.in.replete.komalindustries.utils.MessageUtility;
import co.in.replete.komalindustries.utils.PrepareViewModelUtilty;
import co.in.replete.komalindustries.utils.UDValues;

@Controller
public class AdminWebController extends KomalIndustriesConstants {

	@Autowired
	ProductService productService;
	
	@Autowired
	CommonUtility commonUtility;
	
	@Autowired
	Properties responseMessageProperties;
	
	@Autowired 
	UserManagementDAO userDAO;
	
	@Autowired
	PrepareViewModelUtilty prepareViewModelUtilty;
	
	@Autowired
	AdminDAO adminDAO;
	
	@Autowired
	MessageUtility messageUtility;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String loginView(Model model) {
		
		return "login/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginAdmin(Model model, HttpServletRequest servletRequest) throws Exception {
		
		String emailId = servletRequest.getParameter("emaiId");
		String password = servletRequest.getParameter("password");
		
		if(null == emailId || emailId.isEmpty() || null == password || password.isEmpty()) {
			model.addAttribute(ERROR_MSSG_LABEL, "Required Fields Are empty");
			return "redirect:/";
		} else {
			List<AdminLoginDtls> userDetailsList = adminDAO.selectUserDetailsByEmailIdAndPass(emailId, password);
			try {
				if(userDetailsList.size() != 1) {
					throw new ServiceException("Invalid credentials");
				} else {
					messageUtility.sendMessage(userDetailsList.get(0).getCntcNum(), "Successful login to your dashboard");
					HttpSession session = servletRequest.getSession(true);
					session.setAttribute("isUserLoggedIn", true);
					return "redirect:dashboard";
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute(ERROR_MSSG_LABEL, e.getMessage());
				return "redirect:/";
			}
		}
	}
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String dashBoardView(Model model) {
		
		return "dashboard/dashboard";
	}
	
	/**
	 * Description :
	 */
	@RequestMapping(value="/default", method=RequestMethod.GET)
	public String defaultPageView(Model model) {
		
		return "defaultpage/default";
	}
	
	@RequestMapping(value="/configuration",method=RequestMethod.GET)
	public String configurationPageView(Model model)
	{
		return "configuration/configuration";
	}
	
	@RequestMapping(value="/configuration",method=RequestMethod.POST)
	public String configurationPageProcessAdd(Model model, HttpServletRequest servletRequest) {
		
		try {
			String configName = servletRequest.getParameter("configurationName");
			String configVal = servletRequest.getParameter("configValue");
			String configDesc = servletRequest.getParameter("configDesc");
			adminDAO.insertConfigValues(configName, configVal, configDesc);
			
			model.addAttribute(SUCCESS_MSSG_LABEL, "Configuration Added Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, e.getMessage());
		}
		return "redirect:configuration";
	}
	
	@RequestMapping(value="/editconfiguration",method=RequestMethod.POST)
	public String configurationPageProcessEdit(Model model, HttpServletRequest servletRequest) {
		
		try {
			String configName = servletRequest.getParameter("configName");
			String configVal = servletRequest.getParameter("configValue");
			String configDesc = servletRequest.getParameter("configDesc");
			adminDAO.updateConfigValues(configName, configVal, configDesc);
			
			model.addAttribute(SUCCESS_MSSG_LABEL, "Configuration Updated Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, e.getMessage());
		}
		return "redirect:configuration";
	}
	
	@RequestMapping(value="/invoice",method=RequestMethod.GET)
	public String invoicePageView(Model model)
	{
		return "invoice/invoice";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logoutPageView(Model model, HttpSession session)
	{
		session.setAttribute("isUserLoggedIn", false);
		return "redirect:/";
	}
	
	@RequestMapping(value="/categorymaster",method=RequestMethod.GET)
	public String categoryPageView(Model model)
	{
		return "products/categoryDetails";
	}
	
	@RequestMapping(value="/categorymaster",method=RequestMethod.POST)
	public String categoryPageProcessAdd(Model model, HttpServletRequest servletRequest)
	{
		String isCategory = "";
		try {
			isCategory = servletRequest.getParameter("isCategory");
			String catName = servletRequest.getParameter("catName");
			String url = servletRequest.getParameter("url");
			String catDesc = servletRequest.getParameter("catDesc");
			String parentId = servletRequest.getParameter("parentId"); 
			if(null == parentId || parentId.isEmpty() || parentId.equals("Select")) {
				throw new Exception("Please specify category");
			}
			adminDAO.insertCategoryDetails(catName, url, catDesc, Integer.parseInt(parentId));
			if(isCategory.equals(UDValues.BOOLEAN_TRUE.toString())) {
				model.addAttribute(SUCCESS_MSSG_LABEL, "Category Added Successfully");
			} else {
				model.addAttribute(SUCCESS_MSSG_LABEL, "Sub Category Added Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, e.getMessage());
		}
		
		if(isCategory.equals(UDValues.BOOLEAN_TRUE.toString())) {
			return "redirect:categorymaster";
		} else {
			return "redirect:subcategorymaster";
		}
	}
	
	@RequestMapping(value="/editcatDetails",method=RequestMethod.POST)
	public String categoryPageProcessEdit(Model model, HttpServletRequest servletRequest)
	{
		try {
			String catId  = servletRequest.getParameter("catId");
			String catName = servletRequest.getParameter("catName");
			String url = servletRequest.getParameter("catUrl");
			String catDesc = servletRequest.getParameter("catDesc");
			
			adminDAO.updateCategoryDetails(catId, catName, url, catDesc);
			model.addAttribute(SUCCESS_MSSG_LABEL, "Category Edited Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, e.getMessage());
		}
		return "redirect:categorymaster";
	}
	
	@RequestMapping(value="/editSubCatDetails",method=RequestMethod.POST)
	public String subCategoryPageProcessEdit(Model model, HttpServletRequest servletRequest)
	{
		try {
			String catId  = servletRequest.getParameter("catId");
			String catName = servletRequest.getParameter("catName");
			String parentId = servletRequest.getParameter("parentId");
			String catDesc = servletRequest.getParameter("catDesc");
			String url = servletRequest.getParameter("url");
			
			adminDAO.updateSubCategoryDetails(catId, catName, parentId, catDesc, url);
			model.addAttribute(SUCCESS_MSSG_LABEL, "Sub Category Edited Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, e.getMessage());
		}
		return "redirect:subcategorymaster";
	}
	
	@RequestMapping(value="/DeleteCategoryMaster",method=RequestMethod.GET)
	public String categoryPageProcessEdit(@RequestParam("id") String id, Model model, HttpServletRequest servletRequest)
	{
		try {
			adminDAO.deleteCategoryDetails(id);
			model.addAttribute(SUCCESS_MSSG_LABEL, "Category with id: " + id + " Deleted Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, e.getMessage());
		}
		return "redirect:categorymaster";
	}
	
	@RequestMapping(value="/subcategorymaster",method=RequestMethod.GET)
	public String subcategoryPageView(Model model)
	{
		return "products/subcategoryDetails";
	}
	
	@RequestMapping(value="/DeleteSubCategoryMaster",method=RequestMethod.GET)
	public String subCategoryPageProcessEdit(@RequestParam("id") String id, Model model, HttpServletRequest servletRequest)
	{
		try {
			adminDAO.deleteCategoryDetails(id);
			model.addAttribute(SUCCESS_MSSG_LABEL, "Sub Category with id: " + id + " Deleted Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, e.getMessage());
		}
		return "redirect:subcategorymaster";
	}
	
	@RequestMapping(value="/packagingInfoMaster", method=RequestMethod.GET)
	private String packageInfoView(Model model) {
		
		return "products/PackagingInfo";
	}
	
	@RequestMapping(value="/addPackagingInfo", method=RequestMethod.POST)
	public String packageInfoProcessAdd(Model model, HttpServletRequest servletRequest) {
		
		String returnViewURL = "products/PackagingInfo";
		
		try {
			String packagingInfoName = servletRequest.getParameter("packagingInfoName");
			String packagingInfoDesc = servletRequest.getParameter("packagingDesc");
			
			adminDAO.addPackagingInfo(packagingInfoDesc, packagingInfoName);
			
			model.addAttribute(SUCCESS_MSSG_LABEL, "Packaging Info Added Successfully");
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, "Error Accessing data");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, "Error Occured");
		}
		
		return returnViewURL;
	}
	
	@RequestMapping(value="/editPackagingInfo", method=RequestMethod.POST)
	public String packageInfoProcessEdit(Model model, HttpServletRequest servletRequest) {
		
		String returnViewURL = "products/PackagingInfo";
		
		try {
			String packaginInfoId = servletRequest.getParameter("packagingInfoId");
			String packagingInfoName = servletRequest.getParameter("packagingInfoName");
			String packagingInfoDesc = servletRequest.getParameter("packagingDesc");
			
			adminDAO.editPackagingInfo(packaginInfoId, packagingInfoDesc, packagingInfoName);
			
			model.addAttribute(SUCCESS_MSSG_LABEL, "Packaging Info Edited Successfully");
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, "Error Accessing data");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, "Error Occured");
		}
		
		return returnViewURL;
	}
	
	@RequestMapping(value="/testImg", method=RequestMethod.GET)
	public void doImageDisplayTest(@RequestParam("productId") String id, HttpServletRequest servletRequest, HttpServletResponse response) {
		try{
			System.out.println(id);
			byte[] dbUserImage=productService.getItemImage(id);
			 
			 // Blob imgLen = pd.getUser_img();
	//		 System.out.println(dbUserImage);
			 // int j=imgLen.setBytes(1,bb);
			  int len = 0;
			   if(dbUserImage !=null)
				{
					   len =  dbUserImage.length;
					   response.reset();
				        response.setContentType("image/jpg");
				        response.getOutputStream().write(dbUserImage,0,len);
				        response.getOutputStream().flush();
				        
				}
				   else{
					   len = 0;
				}
	//		System.out.println(len);
	//		byte [] rb = new byte[len];
	       
			/*InputStream readImg=null; //=  (InputStream) imgLen;
			readImg.read(dbUserImage);
	*/
			//System.out.println(j );
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="/locationdetails", method=RequestMethod.GET)
	public String locationMasterView(@RequestParam(value="stateid", required=false) String stateId, @RequestParam(value="statename", required=false) String statename, 
			Model model) throws Exception {
		String returnUrl = null;
		try {
			if(null == stateId) {
				model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_LOCATION, model, null, null);
				returnUrl = VIEW_URL_LOCATION;
			} else {
				List<LocationDtls> cityList = adminDAO.selectCityListByStateId(stateId);
				model.addAttribute("cityList", cityList);
				model.addAttribute("statename", statename);
				model.addAttribute("stateid", stateId);
				model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_LOCATION, model, null, null);
				returnUrl = VIEW_URL_LOCATION_CITY;
			}
		} catch (Exception e) {
			model.addAttribute(ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return returnUrl;
	}
	
	
	@RequestMapping(value="/locationdetailsdelete", method=RequestMethod.GET)
	public String locationProcessDelete(@RequestParam(value="locationId", required=true) String locationId, Model model) throws Exception {
		
		try {
			adminDAO.wDeleteLocation(locationId);
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_LOCATION, model, SUCCESS_MSSG_LABEL, 
					"Loaction Inactivated Successfully");
		} catch (Exception e) {
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_LOCATION, model, ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return VIEW_URL_LOCATION;
	}
	
	@RequestMapping(value="/locationdetailsedit", method=RequestMethod.GET)
	public String processLocationEditGet(Model model) throws Exception {
		
		try {
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_LOCATION, model, null, null);
		} catch (Exception e) {
			model.addAttribute(ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return VIEW_URL_LOCATION;
	}
	
	@RequestMapping(value="/locationdetailsadd", method=RequestMethod.GET)
	public String processLocationEditAdd(Model model) throws Exception {
		
		try {
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_LOCATION, model, null, null);
		} catch (Exception e) {
			model.addAttribute(ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return VIEW_URL_LOCATION;
	}
	
	
	@RequestMapping(value="/locationdetailsedit", method=RequestMethod.POST)
	public String locationProcessEdit(@ModelAttribute("locationEdit") LocationDtls request, BindingResult result, Model model) throws Exception {
		
		try {
			adminDAO.editWLocation(request);
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_LOCATION, model, SUCCESS_MSSG_LABEL, 
					"Loaction Edited Successfully");
		} catch (Exception e) {
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_LOCATION, model, ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return VIEW_URL_LOCATION;
	}
	
	@RequestMapping(value="/locationdetailsadd", method=RequestMethod.POST)
	public String locationProcessAdd(@ModelAttribute("locationAdd") LocationDtls request, BindingResult result, Model model) throws Exception {
		
		try {
			if(request.getLocationParentId() == 99999) {
				throw new Exception("Please Select A State");
			}
			adminDAO.addWLocation(request);
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_LOCATION, model, SUCCESS_MSSG_LABEL, 
					"Loaction Added Successfully");
		} catch (Exception e) {
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_LOCATION, model, ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return VIEW_URL_LOCATION;
	}
}

