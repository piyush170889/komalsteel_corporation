package co.in.replete.komalindustries.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.ChangePasswordWrapper;
import co.in.replete.komalindustries.beans.DistributorTO;
import co.in.replete.komalindustries.beans.DistributorWrapper;
import co.in.replete.komalindustries.beans.LoginRequestWrapper;
import co.in.replete.komalindustries.beans.LoginResponseWrapper;
import co.in.replete.komalindustries.beans.MResetPasswordTO;
import co.in.replete.komalindustries.beans.NewDistributorWrapper;
import co.in.replete.komalindustries.beans.OTPRequest;
import co.in.replete.komalindustries.beans.RegisterRequestWrapper;
import co.in.replete.komalindustries.beans.SingleValueCommonClass;
import co.in.replete.komalindustries.beans.UpdateRequestWrapper;
import co.in.replete.komalindustries.beans.UpdateUserTO;
import co.in.replete.komalindustries.beans.UserDetailsAllTO;
import co.in.replete.komalindustries.beans.UserDetailsTO;
import co.in.replete.komalindustries.beans.UserDetailsWrapper;
import co.in.replete.komalindustries.beans.UserDetailsWrapper2;
import co.in.replete.komalindustries.beans.UserLoginDtlTO;
import co.in.replete.komalindustries.beans.entity.AddressDetail;
import co.in.replete.komalindustries.beans.entity.AppConfiguration;
import co.in.replete.komalindustries.beans.entity.OtpDetails;
import co.in.replete.komalindustries.beans.entity.UserDetailsAssociatedTO;
import co.in.replete.komalindustries.beans.entity.UserLoginDtl;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.AdminDAO;
import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.utils.CommonUtility;
import co.in.replete.komalindustries.utils.MessageUtility;

@Service
@Transactional(rollbackFor=Throwable.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserManagementDAO userDAO;
	
	@Autowired
	private Properties responseMessageProperties;
	
	@Autowired
	private Properties configProperties;
	
	@Autowired
	private CommonUtility commonUtility;
	
	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	MessageUtility messageUtility;
	
	/**
	 * Validates the user credentials supplied at login screen
	 * 
	 * @param {@link LoginRequestWrapper}
	 * @return  {@link BaseWrapper}
	 * @throws  {@link Exception}
	 */
	@Override
	public BaseWrapper loginUser(LoginRequestWrapper request) throws Exception {
	    
		LoginResponseWrapper response;

		UserLoginDtlTO userLoginDtl=request.getUserLoginDtl();
		
		List<UserDetailsTO> userDetailsList;
		
		//Check if user exists
		try{
		  userDetailsList = userDAO.selectUserDetails(userLoginDtl.getLoginId().trim(), userLoginDtl.getPassword().trim(),userLoginDtl.getCompanyInfoId().trim());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		  throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		}
		
		if(userDetailsList.size() == 1) 
		{
			//Get the users details
			UserDetailsTO userDetailsTO = userDetailsList.get(0);
			
			//Check user type
			List<UserDetailsAssociatedTO> associatedDistributorsList = null;
			
			if(null != userDetailsTO.getUserType()) {
				if(userDetailsTO.getUserType().equals(KomalIndustriesConstants.USER_TYPE_DEALER))
				{
					//Get associated distributors list for the user if the user is a dealer
					try
					{
						associatedDistributorsList = userDAO.selectAssociatedDistributorsList(userDetailsTO.getUserTrackId().trim());
					}
					catch(Exception e)
					{
						e.printStackTrace();
						throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
					}
				}
			}
			
			
			//Prepare response and return
			response = new LoginResponseWrapper();
			
			//Set User details
			//Get default shipping address
			AddressDetail addressDetails = userDAO.selectUsersDefaultShippindAddress(userDetailsTO.getUserTrackId().trim());
			if(null != addressDetails) {
				BeanUtils.copyProperties(addressDetails, userDetailsTO);
			}
			response.setUserDetailsTO(userDetailsTO);
			//Set User associated distributors list
			if(null != associatedDistributorsList)
			{
			  response.getUserDetailsTO().setAssociatedDistributorsList(associatedDistributorsList);
			} else
			{
				response.getUserDetailsTO().setAssociatedDistributorsList(new ArrayList<UserDetailsAssociatedTO>());	
			}  
			
			return response;
		}
		else
		{
			throw new Exception("Invalid Login Credentials");
		}
		
	}
	
	/**
	 * Description: Register new user
	 * @param  {@link RegisterRequestWrapper}
	 * @return {@link success Response}
	 * @throws @{@link Exception}
	 */
	@Override
	public BaseWrapper registerUser(RegisterRequestWrapper request, HttpServletRequest servletRequest) throws Exception {
		// Fetch values from request
		String emailId=request.getRegisterDetails().getLoginId() == null ? " " : request.getRegisterDetails().getLoginId();
		String password=request.getRegisterDetails().getPassword().trim();
		String userType=request.getRegisterDetails().getUserType();
		String cmpnyInfoId=request.getRegisterDetails().getCmpnyInfoId().trim();
		
		String firstName=WordUtils.capitalizeFully(request.getRegisterDetails().getFirstName().trim());
		String lastName= null == request.getRegisterDetails().getLastName() ? " " : 
			WordUtils.capitalizeFully(request.getRegisterDetails().getLastName().trim());
		String contactNum=request.getRegisterDetails().getCntc_num();
		String panNum=null == request.getRegisterDetails().getPanNo() ? " " : request.getRegisterDetails().getPanNo().trim();
		String vatTinNum=null == request.getRegisterDetails().getVatTinNo() ? " " : request.getRegisterDetails().getVatTinNo().trim();
		String displayName=WordUtils.capitalizeFully(request.getRegisterDetails().getDisplayName());
		
	    try 
	    {
		 /* // check if Email ID is already exist
		  int loginId=userDAO.selectEmailIdCount(emailId);
		  if(loginId !=0)
		  {
			throw new Exception(responseMessageProperties.getProperty("error.emailid.alreadyexist"));
		  }*/
		
		  // check if contact number is already exist
		  int contact=userDAO.selectContactNumCount(contactNum);
		  if(contact !=0)
		  {
			throw new Exception(responseMessageProperties.getProperty("error.contactno.alreadyexist"));
		  }
		
		 /* // check if Display name is already exist
		  int disName=userDAO.selectDisplayNameCount(displayName);
	      if(disName !=0)
	    	{
			  throw new Exception(responseMessageProperties.getProperty("error.displayname.alreadyexist"));
		   }*/
		
		  /*//check if PAN number is already exist
	    	int pan=userDAO.selectPanCount(panNum);
		   if(pan !=0)		   {
			throw new Exception(responseMessageProperties.getProperty("error.pannumber.alreadyexist"));
		   }
		
		  // check if VAT TIN is already exist
		   int vat=userDAO.selectVatTinCount(vatTinNum);
		   if(vat !=0)
		   {
			 throw new Exception(responseMessageProperties.getProperty("error.vatnumber.alreadyexist"));
		   }
		*/
		   // insert new user details
		   String activationCode = UUID.randomUUID().toString();
		   String insertedTrackId = userDAO.insertUserDtl(firstName,lastName,contactNum,displayName,panNum,vatTinNum,cmpnyInfoId);
		   userDAO.insertLoginDtl(insertedTrackId, emailId,password,userType,cmpnyInfoId,activationCode);
		 
		   /*String message=createLink("Hi check your verification link : ", servletRequest, activationCode, "/emailverification/");
		   String subject="Verification Link";
		   
		   if(commonUtility.sendEmail(emailId, message, subject)) {
			   return new BaseWrapper();
		   } else {
			   throw new Exception("Email could not be sent at the moment. Please try again");
		   }*/
		   System.out.println("Cntc num: " + contactNum + " First nam:" + firstName + " Last name: " + lastName);
		   commonUtility.sendEmailToAdmin(String.format(configProperties.getProperty("user.registration.new"), contactNum, firstName, lastName, 
				   new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())), configProperties.getProperty("user.registration.subject"));
		   return new BaseWrapper();
	   }
	   catch (DataAccessException e) 
	    {
			e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		}
		
	}

	/**
	 * Description : 
	 * 
	 * @param  activationCode
	 * @param  {@link HttpServletResponse}
	 * @return {@link BaseWrapper}
	 * @throws {@link Exception}
	 */
	@Override
	public void verifyEmail(String activationCode, HttpServletResponse response) throws Exception {

		try {
			if(null != activationCode) {
				//Get user login id from activation code
				List<UserLoginDtl> userLoginDtls = userDAO.selectUserDetailsByActivationCode(activationCode);
				
				if(null == userLoginDtls || userLoginDtls.size() != 1) {
					throw new Exception(responseMessageProperties.getProperty("error.activationCode.invalid"));
				} else {
					//Update the activation details of the user
					userDAO.updateUserActivationDetails(userLoginDtls.get(0).getUserLoginDtlsId());

					String redirectURL =  responseMessageProperties.getProperty("success.page.url") +"?sucessMssg=" + responseMessageProperties.getProperty("success.useractivation");
					response.sendRedirect(redirectURL);
				}
//				return new BaseWrapper();
			} else {
				throw new Exception(responseMessageProperties.getProperty("error.activationCode.required"));
			}
		} catch(DataAccessException e) {
		  e.printStackTrace();
		  throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
	    }
	}
	
	
	/**
	 * Description: Get user details by id
	 * @param {@link emailId}
	 * @return {@link BaseWrapper}
	 * @throws {@link Exception}
	 */
	@Override
	public BaseWrapper userDetails(String id) throws Exception {

    List<UserDetailsTO> userDetailsList=null;
    
    try {
    //  and get user details
		 userDetailsList=userDAO.selectUserDetailsById(id);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
	}
    //check for if user is exist
    if(userDetailsList.size()==1)
    {
    	// create response
    	UserDetailsWrapper response=new UserDetailsWrapper();
    	response.setUserDetailsTo(userDetailsList);
        return response;
     }
    else
    {   
    	throw new Exception(responseMessageProperties.getProperty("error.emailid.invalid"));
    }
 }
    
	/**
	 * Description: update user details
	 * @param {@link trackid request}
	 * @return {@link BaseWrapper }
	 * @throws {@link Exception}
	 */
	@Override
	public BaseWrapper updateUser(String trackid, UpdateRequestWrapper request) throws Exception
	{
       try
       {
    	   UpdateUserTO userDetailsUpdate = request.getUpdateUserTO();
    	   //Update basic details
    	   userDAO.updateUserDetail(trackid,userDetailsUpdate.getFirstName(), userDetailsUpdate.getLastName() ,	userDetailsUpdate.getDisplayName());
    	   //Update Login Id if not empty
    	   if(!userDetailsUpdate.getEmail().isEmpty()) {
    		   List<UserDetailsTO> userDetails = userDAO.selectUserDetailsBytrackIdWoAddrDetails(trackid);
    		   userDAO.updateUserEmailId(userDetails.get(0).getLoginDtlsId(), userDetailsUpdate.getEmail());
    	   }
    	   
    	   //update user default shipping address details
    	   AddressDetail addressdetails = userDAO.selectUserDefaultShippingAddressDetails(trackid);
    	   
    	   //Update Or Insert Address Details
    	   if(null != addressdetails) {
    		   userDAO.updateUserDefaultShippingAddress(addressdetails.getOtherAddressId(), userDetailsUpdate.getAddress(), userDetailsUpdate.getCity(),
    			   userDetailsUpdate.getState(), userDetailsUpdate.getPincode(), userDetailsUpdate.getMark(),
				   userDetailsUpdate.getDestination(),userDetailsUpdate.getTinNo(),userDetailsUpdate.getTranNm());
    	   } else {
    		   userDAO.insertUserDefaultShippingAddressDetails(userDetailsUpdate.getAddress(), userDetailsUpdate.getCity(),
    				   userDetailsUpdate.getState(), userDetailsUpdate.getPincode(), trackid, userDetailsUpdate.getMark(),
    				   userDetailsUpdate.getDestination(),userDetailsUpdate.getTinNo(),userDetailsUpdate.getTranNm());
    	   }
    	  return request;
 
       }
       catch(Exception e)
       {   e.printStackTrace();
    	   throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
       }
		
	}
	
	 /**
     * Description : sends generated otp to the patients cellnumber if number of otp attempts 
     * have not reached the maximum attempts
     * 
     * @param {@link OTPRequest}
     * @return {@link BaseWrapper}
     * @throws {@link Exception }
     */
    @Override
	public BaseWrapper doSendOTP(OTPRequest request) throws Exception {
		
		OtpDetails otpDetails = request.getOtpDetails();

		Map<Object, Object> validatingParams = new HashMap<Object, Object>();
		validatingParams.put(responseMessageProperties.getProperty("error.contactno.required"), otpDetails.getCellnumber());
		validatingParams.put(responseMessageProperties.getProperty("error.deviceinfo.required"), otpDetails.getDeviceInfo());

		//Validate Input params
		Object isValidInput = commonUtility.isInputValid(validatingParams);
		
		if(null != isValidInput) {
			throw new Exception((String) isValidInput);
		}
		
		
		 AppConfiguration appConfiguration=adminDAO.selectConfigurationValue(KomalIndustriesConstants.MAX_DB_ATTEMPT);
		final int maxNoOfAttempts=Integer.parseInt(appConfiguration.getConfigVal());
		
		//Step 1: Check the number of attempts for the device info and cell number
		List<OtpDetails> otpDetailsFromDBList = userDAO.selectOtpDetailsByCellNum(otpDetails.getCellnumber().trim());
		
		//Generate otp
		int otp = (int) ((Math.random()*9000)+1000);
			
		Integer numOfAttempts;
		
		if(null != otpDetailsFromDBList && otpDetailsFromDBList.size() == 1) {
			OtpDetails otpDetailsFromDB = otpDetailsFromDBList.get(0);
			numOfAttempts = otpDetailsFromDB.getNumOfAttempts();
			if(numOfAttempts >= maxNoOfAttempts) {
				throw new Exception(responseMessageProperties.getProperty("error.otpmaxattempts.exceeded"));
			} else {
				// Update the otp details associated with the device info and cell number
				try {
					userDAO.updateOTPRecord(++numOfAttempts, otpDetailsFromDB.getOtpId(), otp);
				} catch (Exception e) {
					throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
				}
			}
		} else {
			//Insert the otp details
			numOfAttempts = 1;
			otpDetails.setNumOfAttempts(numOfAttempts);
			otpDetails.setOtp(otp);
			userDAO.insertOTPDetails(otpDetails.convertToInsertparams());
		} 
		
		//send otp
		boolean isMessageSent = false;
		try {
			isMessageSent = messageUtility.sendMessage(otpDetails.getCellnumber(), Integer.toString(otp));
			isMessageSent = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.sms"));
		}
		
		if(isMessageSent) {
		  return new BaseWrapper();	
		} else {
			throw new Exception(responseMessageProperties.getProperty("error.sms"));
		}
		
	}

    
    /**
     * Description : Verifies the submitted OTP with the OTP details present against the device info and cell number
     * @param {@link OTPRequest}
     * @return {@link ServiceResponse}
     * @throws ServicesException
     */
    @Override
	public BaseWrapper doVerifyOTP(OTPRequest request) throws Exception {
    	
    	try {
    	OtpDetails otpDetails = request.getOtpDetails();
    	Map<Object, Object> validatingParams = new HashMap<Object, Object>();
    	validatingParams.put(responseMessageProperties.getProperty("error.otp.required"), otpDetails.getOtp());

		//Validate Input params
		Object isValidInput = commonUtility.isInputValid(validatingParams);

		if(null != isValidInput) {
			throw new Exception((String)isValidInput);
		}
		
			//Get the record count if any record exists with the otp submitted
			List<OtpDetails> otpDetailsFromDBList = userDAO.selectOTPDetails(otpDetails.getCellnumber().trim(), Integer.toString(otpDetails.getOtp()));
			
			//Check if record exists
			if(null != otpDetailsFromDBList && otpDetailsFromDBList.size() == 1) {
				//TODO Check if the otp is expired
				OtpDetails otpDetailsFromDB = otpDetailsFromDBList.get(0);
				//Update OTP Record. Set OTP to 0
				userDAO.updateOTPRecord(otpDetailsFromDB.getNumOfAttempts(), otpDetailsFromDB.getOtpId(), 0);
				return new BaseWrapper();
			} else {
				throw new Exception(responseMessageProperties.getProperty("error.otp.invalid"));
			}
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		} catch (Exception dae) {
			dae.printStackTrace();
			throw new Exception(dae.getMessage());
		}
    }
    
	/**
	 * Description: insert associated Distributed list of dealer
	 * @param {link trackid NewDistributorWrapper}
	 * @return{@link BaseWrapper}
	 * @throws{@link Exception}
	 */
	@Override
	public BaseWrapper assoDistributorList(String trackid,NewDistributorWrapper request) throws Exception
	{
		List<DistributorTO> distributorList=request.getDistributorList();
		
		//Prepare list to send in resposne
		List<DistributorTO> resposneList = new ArrayList<DistributorTO>();
       try
       {
           //insert Associated Distributed list
    	   for(DistributorTO distributor : distributorList)
    	   {
            int userDistributionId = userDAO.insertAssociatedDistributed(trackid,distributor.getTrackId());
            distributor.setUserDistributionListId(Integer.toString(userDistributionId));
            resposneList.add(distributor);
    	   }
    	   
    	   NewDistributorWrapper resposne = new NewDistributorWrapper();
    	   resposne.setDistributorList(resposneList);
		    return resposne;
	   }
       catch(Exception e)
       {
    	  throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
       }
   }
	/**
	 * DEscription:Delete associated distributor 
	 * @param{@link trackid DistributorWrapper }
	 * @return{@link BaseWrapper}
	 * @throws{@link Exception}
	 */
	@Override
	public BaseWrapper deleteAssociatedDistributorList(String trackid, DistributorWrapper request) throws Exception 
	{
	
		List<SingleValueCommonClass> assoDistributorList;
	    assoDistributorList=request.getAssoDistList().getAssociatedDistributorsList();
	      
       try
       {
           //delete associated distributed list
    	   for(SingleValueCommonClass userDistributorListId : assoDistributorList)
    	   {
            userDAO.deleteAssociatedDistributed(trackid, userDistributorListId.getValue());
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
        * Description : select distributor list to perticular dealer
        * @param dealerTrackId
        * @return
        * @throws Exception
        */
		@Override
		public BaseWrapper getDistributorList(String dealerTrackId) throws Exception {
            
			// select distributor
			List<DistributorTO> distributor=userDAO.selectDistributor(dealerTrackId);
			NewDistributorWrapper response= new NewDistributorWrapper();
			response.setDistributorList(distributor);
			
			return response;
		}
		
        /**
         * Description: change password
         * @param id
         * @param request
         * @return
         * @throws Exception
         */
		@Override
		public BaseWrapper newPassword(String id, ChangePasswordWrapper request) throws Exception 
		{  
		 try
			{
			 String oldPassword=request.getChangePassword().getOldPassWord();
			 String newPassword=request.getChangePassword().getNewPassword();
			 
			 // select userLoginId
			 List<UserLoginDtl> userLoginDtl=userDAO.selectUserLoginDtlId(id, oldPassword);
			 
			  if(userLoginDtl.size()==1)
			  {
				  for(UserLoginDtl userloginId:userLoginDtl){
					  // update password
					  userDAO.updatePassword(userloginId.getUserLoginDtlsId(),newPassword);
				  }
			  }
			 else
			 {
			 	throw new Exception(responseMessageProperties.getProperty("error.oldpassword.notexist"));
			 }
			  //return response
			return request;
         }
		catch(DataAccessException e)
		{
			e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		}
	}

	/**
	 * Description: get user details by track id
	 */
		@Override
		public BaseWrapper userDetailsByTrackId(String trackid) throws Exception {

			List<UserDetailsAllTO> userDetailsList=null;
		    
		    try {
		    //  and get user details
				 userDetailsList=userDAO.selectUserDetailsByTrackId(trackid);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
			}
		    //check for if user is exist
		    if(userDetailsList.size()==1)
		    {
		    	// create response
		    	UserDetailsWrapper2 response=new UserDetailsWrapper2();
		    	response.setUserDetail(userDetailsList);
		        return response;
		     }
		    else
		    {   
		    	throw new Exception(responseMessageProperties.getProperty("error.trackid.invalid"));
		    }
		
		}
		
		@Override
		public BaseWrapper resetPasswordFromForgot(MResetPasswordTO request) throws Exception {
			
			String otp = request.getOtp();
			String cellNum = request.getCellNumber();
			String newPassword = request.getNewPassword();
			
			List<OtpDetails> otpDetailsList = userDAO.selectOTPDetails(cellNum, otp);
			
			if(otpDetailsList.size() == 1) {
				UserLoginDtl userDetails = userDAO.selectUserDetailsByContactNum(cellNum);
				
				userDAO.updateUserPassword(userDetails.getUserLoginDtlsId(), newPassword);
				
				OtpDetails otpDetails = otpDetailsList.get(0);
				userDAO.updateOTPRecord(otpDetails.getNumOfAttempts(), otpDetails.getOtpId(), 0);
				return new BaseWrapper();
			} else {
				throw new Exception("Invalid Otp details provided");
			}
		}

		/**
		 * Description: get password reset link for forget password
		 * @param  {@link loginId}
		 * @return {@link success Response}
		 * @throws {@link Exception}  
		 */
		@Override
		public BaseWrapper resetPassword(String loginId) throws Exception 
		{

			List<UserLoginDtl> userLoginDtlList = null;

			try{
				//Get user login details list
				userLoginDtlList=userDAO.selectUserCount(loginId);

				if(userLoginDtlList.size()==1){
					
					/*// TODO :  Prepare restPass Link  DONE send email successfully
					String message="Hi your new password plz click on link";
					String subject="password reset link";
					commonUtility.sendEmail(loginId, message, subject);*/
					
					//Generate otp
					int otp = (int) ((Math.random()*9000)+1000);
					
					//Check if cellNumber alerady exists
					List<OtpDetails> otpDetaislList = userDAO.selectOtpDetailsByCellNum(loginId);
					
					//If exists update
					if(otpDetaislList.size() == 1) {
						OtpDetails otpDetails = otpDetaislList.get(0);
						int numOfAttempts = otpDetails.getNumOfAttempts();
						userDAO.updateOTPRecord(++numOfAttempts, otpDetails.getOtpId(), otp);
					} else if(otpDetaislList.size() > 1) {
						throw new Exception("More than one record found");
					} else {
						userDAO.insertForgotPasswordOTPDetails(loginId, otp);
					}
					
					messageUtility.sendMessage(loginId, Integer.toString(otp));
					
					//If mail sent successfully then return success response 
					return new BaseWrapper();
				}
				else
				{
				throw new ServicesException(responseMessageProperties.getProperty("error.emailid.invalid"));
			    }
			 }catch(ServicesException se) {
				 se.printStackTrace();
				 throw se;
			 } catch(Exception e) {
				 e.printStackTrace();
				throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
			}

		 }
		
		@Override
		public BaseWrapper getUserProfileInfoAll(String trackid) throws Exception {
			LoginResponseWrapper response;

			List<UserDetailsTO> userDetailsList;
			
			//Check if user exists
			try{
			  userDetailsList = userDAO.selectUserProfileDetails(trackid);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			  throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
			}
			
			if(userDetailsList.size() == 1) 
			{
				//Get the users details
				UserDetailsTO userDetailsTO = userDetailsList.get(0);
				
				//Check user type
				List<UserDetailsAssociatedTO> associatedDistributorsList = null;
				
				if(null != userDetailsTO.getUserType()) {
					if(userDetailsTO.getUserType().equals(KomalIndustriesConstants.USER_TYPE_DEALER))
					{
						//Get associated distributors list for the user if the user is a dealer
						try
						{
							associatedDistributorsList = userDAO.selectAssociatedDistributorsList(userDetailsTO.getUserTrackId().trim());
						}
						catch(Exception e)
						{
							e.printStackTrace();
							throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
						}
					}
				}
				
				
				//Prepare response and return
				response = new LoginResponseWrapper();
				
				//Set User details
				//Get default shipping address
				AddressDetail addressDetails = userDAO.selectUsersDefaultShippindAddress(userDetailsTO.getUserTrackId().trim());
				if(null != addressDetails) {
					BeanUtils.copyProperties(addressDetails, userDetailsTO);
				}
				response.setUserDetailsTO(userDetailsTO);
				//Set User associated distributors list
				if(null != associatedDistributorsList)
				{
				  response.getUserDetailsTO().setAssociatedDistributorsList(associatedDistributorsList);
				} else
				{
					response.getUserDetailsTO().setAssociatedDistributorsList(new ArrayList<UserDetailsAssociatedTO>());	
				}  
				
				return response;
			}
			else
			{
				throw new Exception("Multiple Records Found");
			}
		}
		//*************HELPER METHODS*********************//
		/*private String createLink(String emailTemplateMessage, HttpServletRequest servletRequest, String code, String uri) {
			//TODO Prepare appropriate URL
			String urlToEncodeInMessage = "";
			if(servletRequest.getServerPort() != 8080) {
				urlToEncodeInMessage = servletRequest.getServerName() + servletRequest.getContextPath() + uri + code;
			} else {
				urlToEncodeInMessage = servletRequest.getServerName() + ":" + servletRequest.getServerPort() + servletRequest.getContextPath() + uri + code;
			}
			return emailTemplateMessage + urlToEncodeInMessage;
		}*/

}	
