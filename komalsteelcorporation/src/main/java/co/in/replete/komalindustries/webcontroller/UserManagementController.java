package co.in.replete.komalindustries.webcontroller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.in.replete.komalindustries.beans.UserAddTO;
import co.in.replete.komalindustries.beans.UserDetailsAllTO;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.exception.PrepareViewModelException;
import co.in.replete.komalindustries.exception.ServicesException;
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
			if(request.getStatus().isEmpty() || request.getContactNo().isEmpty() || request.getFirstName().isEmpty() || request.getUserType().isEmpty()) {
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
			String passsword = UUID.randomUUID().toString().substring(0, 5);
			userDAO.insertWLoginDtl(userTrackid, request.getEmailId(), passsword, request.getUserType(), CMPNY_INFO_ID, activationCode, request.getStatus().trim());
			
			//Send Login Credentials to customer
			try {
				messageUtility.sendMessage(contactNo, "Your login credentials for our app are. Usrename : " + contactNo + ". Password : " + passsword);
			} catch (Exception tre) {}
			
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
					System.out.println("userActivationAdminMssg - " + userActivationAdminMssg + ", adminContactNum - " + KomalIndustriesConstants.ADMIN_MOBILE_NO);
					
					messageUtility.sendMessage(userCntcNum, userActivationMssg);
					messageUtility.sendMessage(KomalIndustriesConstants.ADMIN_MOBILE_NO, userActivationAdminMssg);
				}
				
				redirectAttributes.addFlashAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, "User status Updated Successfully");
		} catch(Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return "redirect:user"; 
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
