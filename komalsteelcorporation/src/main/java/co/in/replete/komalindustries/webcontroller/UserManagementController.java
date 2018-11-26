package co.in.replete.komalindustries.webcontroller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.in.replete.komalindustries.beans.SmsDtlsWrapper;
import co.in.replete.komalindustries.beans.UserAddTO;
import co.in.replete.komalindustries.beans.UserDetailsAllTO;
import co.in.replete.komalindustries.beans.entity.ContactDtls;
import co.in.replete.komalindustries.beans.entity.SmsDtls;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.exception.PrepareViewModelException;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.service.UserManagementService;
import co.in.replete.komalindustries.utils.CommonUtility;
import co.in.replete.komalindustries.utils.MessageUtility;
import co.in.replete.komalindustries.utils.PrepareViewModelUtilty;
import co.in.replete.komalindustries.utils.UDValues;

@Controller
@Transactional(rollbackFor=Throwable.class)
public class UserManagementController extends KomalIndustriesConstants {

	@Autowired
	CommonUtility commonUtility;

	@Autowired
	UserManagementService  userService;

	@Autowired
	Properties responseMessageProperties;

	@Autowired
	PrepareViewModelUtilty prepareViewModelUtilty;

	@Autowired
	UserManagementDAO userDAO;

	@Autowired
	MessageUtility messageUtility;

	@Autowired
	Properties configProperties;

	@RequestMapping(value="/user",method=RequestMethod.GET)
	public String userPageView(Model model) throws Exception {
		String returnViewURL = VIEW_URL_USER_DTLS;
		try {
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_USER_DTLS, model, null, null);
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_USER_DTLS, model, ERROR_MSSG_LABEL, ERROR_OCCURED);
			returnViewURL = ERROR_PAGE_URL;
		}
		return returnViewURL;
	}

	@RequestMapping(value="/userdetails",method=RequestMethod.GET)
	public String wuserPageView(Model model, @RequestParam(value = "trackid", required = true) String trackid) { 
		try{
			List<UserDetailsAllTO> userDetailsList=userDAO.selectUserDetailsByTrackId(trackid);
			if(userDetailsList.size()==1)
			{
				UserDetailsAllTO userDetailsTO = userDetailsList.get(0);
				model.addAttribute("firstName",userDetailsTO.getFirstName());
				model.addAttribute("lastName",userDetailsTO.getLastName());
				model.addAttribute("contactNo",userDetailsTO.getContactNo());
				model.addAttribute("displayName",userDetailsTO.getDisplayName());
				model.addAttribute("loginId",userDetailsTO.getEmailId());
				model.addAttribute("panNo",userDetailsTO.getPanNo());
				model.addAttribute("status",userDetailsTO.getStatus());
				model.addAttribute("vatNo",userDetailsTO.getVatNo());
				model.addAttribute("userType",userDetailsTO.getUserType());
				model.addAttribute("city",userDetailsTO.getCity());
				model.addAttribute("country",userDetailsTO.getCountry());
				model.addAttribute("postalCode",userDetailsTO.getPostalCode());
				model.addAttribute("state",userDetailsTO.getState());
				model.addAttribute("street1",userDetailsTO.getStreet1());
				model.addAttribute("street2",userDetailsTO.getStreet2());
				model.addAttribute("street3",userDetailsTO.getStreet3());
				model.addAttribute("regDate",userDetailsTO.getRegDate());
				model.addAttribute("trackid", trackid);
				model.addAttribute("distributerTrackId", userDetailsTO.getAssociatedDistributorTrackId());
				model.addAttribute("mark", userDetailsTO.getMark());
				model.addAttribute("destination", userDetailsTO.getDestination());
				model.addAttribute("transportName", userDetailsTO.getTranNm());
			} else {
				model.addAttribute(ERROR_MSSG_LABEL,"failed to get user details ");
			}

		} catch(Exception e) {

			e.printStackTrace();
		}

		return VIEW_URL_USER_DTLS_ALL;

	}

	@RequestMapping(value="/userAdd", method=RequestMethod.POST)
	public String userDetailsProcessAdd(@ModelAttribute("userAdd") UserAddTO request, BindingResult result, Model model, RedirectAttributes redAttr) throws Exception {
		String returnViewURL = VIEW_URL_USER_DTLS;
		//		String returnViewURL = "redirect:user";

		try {
			//Add user basic details
			if(request.getContactNo().isEmpty() || request.getFirstName().isEmpty() || request.getUserType().isEmpty()) {
				throw new ServicesException("Required Fileds are empty");
			}

			String contactNo = request.getContactNo();

			int contactNumCount = userDAO.selectContactNumCount(contactNo);
			if(contactNumCount > 0) {
				throw new ServicesException("Contact Number already exists");
			}


			String userTrackid = userDAO.insertUserDtl(request.getFirstName(), request.getLastName(), contactNo, request.getDisplayName(), request.getPanNo(), 
					request.getVatNo(), CMPNY_INFO_ID, request.getGstNo().trim(), request.getDiscount());

			int addressDtlsId = 0;
			//Add user default shipping details
			if(!request.getStaddress1().isEmpty()) {
				if(validateAddressFields(request)){
					addressDtlsId = userDAO.insertUserDefaultShippingAddressDetails(request.getStaddress1(), request.getCity(), request.getState(), 
							request.getPincode(), userTrackid, request.getMark(),request.getDestination(),request.getVatNo(),request.getTransportName());
				} else {
					throw new ServicesException("Please select valid location details");
				}
			}


			if(request.getUserType().isEmpty() || request.getUserType().equals("Select")) {
				throw new ServicesException("Please select a user type");
			} else {
				if(request.getUserType().equals(UDValues.USER_TYPE_DISTRIBUTOR.toString())) {
					//Add distributor details
					userDAO.insertDistributorDetails(userTrackid, addressDtlsId);
				}
			}

			//Add user login details
			String activationCode = UUID.randomUUID().toString();
			String passsword = UUID.randomUUID().toString().substring(0, 8);
			userDAO.insertWLoginDtl(userTrackid, request.getEmailId(), passsword, request.getUserType(), CMPNY_INFO_ID, activationCode,UDValues.USER_STATUS_ACTIVE.toString());

			//Send Login Credentials to customer
			String userRegMsg = MessageFormat.format(configProperties.getProperty("sms.user.registration"), 
					contactNo, passsword);
			messageUtility.sendMessage(contactNo, userRegMsg);

			System.out.println("userRegMsg :"+userRegMsg);

			/*//Associate Distributor
			if(null != request.getAssociatedDistributor() && !request.getAssociatedDistributor().isEmpty() && !request.getAssociatedDistributor().equalsIgnoreCase("Select")) {
				userDAO.insertAssociatedDistributed(userTrackid, request.getAssociatedDistributor().trim());
			}*/

			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_USER_DTLS, model, SUCCESS_MSSG_LABEL, "User Added Successfully");
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch(DataAccessException dae) {
			dae.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_USER_DTLS, model, ERROR_MSSG_LABEL, "data access error");
		} catch(ServicesException dae) {
			dae.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_USER_DTLS, model, ERROR_MSSG_LABEL, dae.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_USER_DTLS, model, ERROR_MSSG_LABEL, "Somethign went wrong. Please try again adding the user");
		}

		return returnViewURL;
	}

	private boolean validateAddressFields(UserAddTO request) {

		if(request.getState().equals("Select") || request.getCity().equals("Select") || request.getPincode().isEmpty())
		{
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping(value="/editUser", method=RequestMethod.POST)
	public String userDetailsProcessEdit(@ModelAttribute("userEdit") UserAddTO request, BindingResult result, Model model) throws PrepareViewModelException {
		String returnViewURL = VIEW_URL_USER_DTLS;

		try {
			//Update User basic and login details
			userDAO.updateUserDtl(request);

			//Update user default shipping address details
			if(request.getOtherAddressDtlsId() == 0) {
				userDAO.insertUserDefaultShippingAddressDetails(request.getStaddress1(), request.getCity(), request.getState(), request.getPincode(), request.getUserId(),
						request.getMark(),request.getDestination(),request.getVatNo(),request.getTransportName());
			} else if(request.getOtherAddressDtlsId() > 0){
				userDAO.updateUserDefaultShippingAddress(request.getOtherAddressDtlsId(), request.getStaddress1(), request.getCity(), request.getState(), 
						request.getPincode(),request.getMark(),request.getDestination(),request.getVatNo(),request.getTransportName());
			}

			//Update Or Insert Associated Distributor List
			if(null != request.getUserDistributorListId() && request.getUserDistributorListId() == 0 && null != request.getAssociatedDistributor()) {
				userDAO.insertAssociatedDistributed(request.getUserId(), request.getAssociatedDistributor());
			} else if(request.getUserDistributorListId() > 0){
				userDAO.updateAssociatedDistributor(request.getUserDistributorListId(), request.getAssociatedDistributor());
			}

			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_USER_DTLS, model, SUCCESS_MSSG_LABEL, "User Edited Successfully");
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch(DataAccessException dae) {
			dae.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_USER_DTLS, model, ERROR_MSSG_LABEL, "data access error");
		} catch(Exception e) {
			e.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_USER_DTLS, model, ERROR_MSSG_LABEL, "Somethign went wrong. Please try again adding the user");
		}

		return returnViewURL; 
	}


	@RequestMapping(value="/activate-deactivate-user", method=RequestMethod.GET)
	public String userDetailsUpdate(@RequestParam("trackId") String trackId,
			@RequestParam("status") String status, RedirectAttributes redirectAttributes) throws PrepareViewModelException {
		try {
			userDAO.updateUserDtls(trackId,status);

			System.out.println("status - " + status);
			if (status.equalsIgnoreCase("Active")) {
				List<UserDetailsAllTO> userDetailsList = userDAO.selectUserDetailsByTrackId(trackId);
				UserDetailsAllTO userDetails = userDetailsList.get(0);
				String userCntcNum = userDetails.getContactNo();
				String activatedUserName = userDetails.getFirstName() + " " + ((null == userDetails.getLastName() || userDetails.getLastName().isEmpty()) ? "" : userDetails.getLastName());

				String userActivationMssg = MessageFormat.format(configProperties.getProperty("sms.user.activation"), activatedUserName);
				System.out.println("userActivationMssg - " + userActivationMssg + ", userCntcNum - " + userCntcNum);

				String currentDateTime = new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date());
				String userActivationAdminMssg = MessageFormat.format(configProperties.getProperty("sms.admin.activation"), 
						activatedUserName, currentDateTime);
				System.out.println("userActivationAdminMssg - " + userActivationAdminMssg + ", adminContactNum - " + configProperties.getProperty("contact.admin.list"));

				messageUtility.sendMessage(userCntcNum, userActivationMssg);
//				messageUtility.sendMessage(KomalIndustriesConstants.ADMIN_MOBILE_NO, userActivationAdminMssg);
				messageUtility.sendMessage(commonUtility.getAdminContactNumbers(), userActivationAdminMssg);
			}

			redirectAttributes.addFlashAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, "User status Updated Successfully");
		} catch(Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
		}

		return "redirect:user"; 
	}
	/*
	@RequestMapping(value="/sms-history", method=RequestMethod.GET)
	public String smsHistory(Model model) throws PrepareViewModelException {

		model = prepareViewModelUtilty.prepareViewModelMap(KomalIndustriesConstants.VIEW_URL_ORDER, model, null, null);
		return  "master/smsHistory";






	}*/


	@RequestMapping(value="/contact-history", method=RequestMethod.GET)
	public String selectContactDtls(@ModelAttribute("SpringWeb")ContactDtls cnt,   ModelMap model) 

	{
		List<ContactDtls> contactDirectorieList = userDAO.getAllContactDirectories();
		System.out.println("contactDirectorieList : "+contactDirectorieList.toString());
		model.addAttribute("contactDirectorieList",contactDirectorieList);	      
		model.addAttribute("addContctDetails",new ContactDtls());
		return "master/contactHistory";

	}


	@RequestMapping(value="/add-contact", method=RequestMethod.POST)
	public String addContactDtls(@ModelAttribute("addContctDetails") ContactDtls contactDtls, RedirectAttributes rdrct) throws ServicesException  {
		String messageLabel ="";
		String message="";

		try {
			int count = userService.checkContactNumber(contactDtls.getContactNumber());
			if(count==0)
			{
				userService.addContactDirectories(contactDtls);
				messageLabel= KomalIndustriesConstants.SUCCESS_MSSG_LABEL;
				message="Contact Details added successfully.";
			}else {
				messageLabel = KomalIndustriesConstants.ERROR_MSSG_LABEL;
				message="Number Already exist.";
			}
		}
		catch(Exception e)
		{
			messageLabel = KomalIndustriesConstants.ERROR_MSSG_LABEL;
			message="Exception Occured while adding contact details.";
		}

		rdrct.addFlashAttribute(messageLabel,message);		

		return "redirect:contact-history";
	}


	@RequestMapping(value="/edit-contact", method=RequestMethod.POST)
	public String editContactDtls(@ModelAttribute("addContctDetails") ContactDtls contactDtls, RedirectAttributes redirectAttributes) 
	{
		String messageLabel ="";
		String message="";

		try {
			userDAO.editContactDirectories(contactDtls);
			messageLabel=KomalIndustriesConstants.SUCCESS_MSSG_LABEL;
			message="Contact details updated Successfully.";
		}catch (Exception e) {
			messageLabel=KomalIndustriesConstants.ERROR_MSSG_LABEL;
			message="Exception Occured while Updating contact details.";
		}

		redirectAttributes.addFlashAttribute(messageLabel,message);			
		return "redirect:contact-history";

	}


	@RequestMapping(value="/check-contact-number", method=RequestMethod.GET)
	public @ResponseBody String checkContactNumber(HttpServletRequest request) throws ServicesException  {
		int count = userService.checkContactNumber(request.getParameter("contactNumber"));
		System.out.println("Count : : "+count);
		if(count != 0) {
			return "error";
		}
		return "success";
	}





	@RequestMapping(value="/activate-deactivate-contact", method=RequestMethod.GET)
	public String activateDeactivateContactDtls(@RequestParam ("status") int status,@RequestParam ("contactDtlsId") int contactDtlsId, RedirectAttributes redirectAttributes) 
	{
		String messageLabel ="";
		String message="";
		try {
			userDAO.activateDeactivateContactDetails(status,contactDtlsId);
			messageLabel=KomalIndustriesConstants.SUCCESS_MSSG_LABEL;
			if(status==0) {
				message="Contact Number deactivated Successfully.";
			}else {
				message="Contact Number activated Successfully.";
			}
		}catch (Exception e) {
			messageLabel=KomalIndustriesConstants.ERROR_MSSG_LABEL;
			message="Exception Occured while Updating status.";
		}
		redirectAttributes.addFlashAttribute(messageLabel,message);			
		return "redirect:contact-history";

	}




	@RequestMapping(value="/sms-history", method=RequestMethod.GET)
	public String selectSmsDtls(  ModelMap model) {
		List<SmsDtlsWrapper> smsDtlsList = userService.getAllSmsDtls();
		model.addAttribute("smsDtlsList",smsDtlsList);	      
		model.addAttribute("addSmsDetails",new SmsDtls());
		return "master/smsHistory";
	}

	@RequestMapping(value="/activate-deactivate-sms", method=RequestMethod.GET)
	public String activateDeactivateSmsDtls(@RequestParam ("status") int status,@RequestParam ("smsDtlsId") int smsDtlsId) 
	{
		userDAO.activateDeactivateSmsDetails(status,smsDtlsId);
		return "redirect:smsHistory";

	}

	/*@ModelAttribute("stateList")
	public List<LocationDtls> getStateList() throws Exception {
		return userDAO.getStateList();
	}

	@ModelAttribute("DistributorList")
	public List<DistributorDetailsTO> getDistributorDetails() {
		return userDAO.selectDistributorDetailsList();
	}*/

	/*@RequestMapping(value="/deleteUser", method=RequestMethod.GET)
	public String userDetailsProcessDelete(@RequestParam("trackId") String trackId, Model model) {
		String returnViewURL = "customer management/user";
		try {
			userDAO.deleteUserDtl(trackId);
			model = prepareViewModelUtilty.prepareViewModelMap("userDetails", model, SUCCESS_MSSG_LABEL, "User Deleted Successfully");
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap("userDetails", model, ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch(DataAccessException dae) {
			dae.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap("userDetails", model, ERROR_MSSG_LABEL, "data access error");
		} catch(Exception e) {
			e.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap("userDetails", model, ERROR_MSSG_LABEL, "Somethign went wrong. Please try again adding the user");
		}

		return returnViewURL; 
	}
	 */




}
