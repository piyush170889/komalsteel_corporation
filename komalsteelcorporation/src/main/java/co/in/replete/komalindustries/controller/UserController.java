package co.in.replete.komalindustries.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.ChangePasswordWrapper;
import co.in.replete.komalindustries.beans.DistributorWrapper;
import co.in.replete.komalindustries.beans.LoginRequestWrapper;
import co.in.replete.komalindustries.beans.MResetPasswordTO;
import co.in.replete.komalindustries.beans.NewDistributorWrapper;
import co.in.replete.komalindustries.beans.OTPRequest;
import co.in.replete.komalindustries.beans.RegisterRequestWrapper;
import co.in.replete.komalindustries.beans.UpdateRequestWrapper;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.service.UserService;

@RestController
public class UserController extends KomalIndustriesConstants {

	@Autowired
	UserService userService;
	
	/**
	 * Validates the user credentials supplied at login screen
	 * 
	 * @param   {@link LoginRequestWrapper}
	 * @return  {@link BaseWrapper}
	 * @throws  {@link Exception}
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST, consumes=KomalIndustriesConstants.APPLICATION_JSON, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper loginUser(@Valid @RequestBody LoginRequestWrapper request) throws Exception {
		
		return userService.loginUser(request);
	}
	
	/**
	 * Description: Get the reset password link if password is forget
	 * @param loginId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/forgetpassword/{cntcNum}",method=RequestMethod.GET, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper forgetPassword(@PathVariable("cntcNum") String loginId) throws Exception{
		
		return userService.resetPassword(loginId);
	}
	
	/**
	 * Description: Register new user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/userdetails",method=RequestMethod.POST, consumes=KomalIndustriesConstants.APPLICATION_JSON, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper registerUser(@Valid @RequestBody RegisterRequestWrapper request, HttpServletRequest servletRequest) throws Exception
	{
		return userService.registerUser(request, servletRequest);
	}
	
	/**
	 * Description: Get user details by email Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/userdetails/{loginid}", method=RequestMethod.GET, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper getUserDetails(@PathVariable("loginid") String loginid) throws Exception
	{
		return userService.userDetails(loginid);
	}
	
	/**
	 * Description: Update user details
	 * @param trackid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/userdetails/{trackid}",method=RequestMethod.PUT,  consumes=KomalIndustriesConstants.APPLICATION_JSON, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper updateUserDetails(@PathVariable("trackid") String trackid, @Valid @RequestBody UpdateRequestWrapper request) throws Exception
	{
		return userService.updateUser(trackid,request);
	}
	
	/**
	 * Description: userDistributorList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delearassociateddistributors/{trackid}",method=RequestMethod.POST,consumes=KomalIndustriesConstants.APPLICATION_JSON, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper addAssociatedDistributor(@PathVariable("trackid") String trackid, @Valid  @RequestBody NewDistributorWrapper request) throws Exception
	{

		return userService.assoDistributorList(trackid,request);
	}
	
	/**
	 * Description: delete Associated distributed list
	 * @param request
	 * @param Dealer trackid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deletedelearassociateddistributors/{trackid}",method=RequestMethod.POST,consumes=KomalIndustriesConstants.APPLICATION_JSON, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper deleteAssociatedDistributor(@PathVariable("trackid") String trackid, @Valid @RequestBody DistributorWrapper request) throws Exception
	{

		return userService.deleteAssociatedDistributorList(trackid, request);
	}
	
	/**
	 * Description: get all distributor list which are not associated to particular dealer
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/distributors/{id}",method=RequestMethod.GET, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper getDistributor(@PathVariable ("id") String id) throws Exception
	{
		return userService.getDistributorList(id);
	}
	
	/**
	  * Description : Webservice to send otp to a number
	  * @param {@link OTPRequest}
	  * @return {@link ServiceResponse}
	  * @throws {@link ServicesException}
	  */
	 @RequestMapping(value="/sendotp", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	 public BaseWrapper sendOTP(@RequestBody OTPRequest request) throws Exception {
		 
		 return userService.doSendOTP(request);
	 }
	 
	 /**
	  * Description : Verified the OTP submitted with the otp details against the device info and cell number
	  * @param {@link OTPRequest}
	  * @return {@link ServiceResponse}
	  * @throws {@link ServicesException}
	  */
	 @RequestMapping(value="/verifyotp", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	 public BaseWrapper verifyOTP(@RequestBody OTPRequest request) throws Exception {
		 
		 return userService.doVerifyOTP(request);
	 }
	 
	 @RequestMapping(value="/changepassword/{id}",method=RequestMethod.POST,consumes=KomalIndustriesConstants.APPLICATION_JSON, produces=KomalIndustriesConstants.APPLICATION_JSON)
	 public BaseWrapper changePassword(@PathVariable ("id") String id,@Valid @RequestBody ChangePasswordWrapper request) throws Exception
	 {
		 return userService.newPassword(id,request);
	 }
	 
	 /**
	  * Description: get user details by track id
	  * @param trackid
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value="/getuserdetails/{trackid}", method=RequestMethod.GET, produces=KomalIndustriesConstants.APPLICATION_JSON)
		public BaseWrapper getUserDetailsByTrackId(@PathVariable("trackid") String trackid) throws Exception
		{
			return userService.userDetailsByTrackId(trackid);
		}
	 
	 
	/**
	  * Description : 
	  * 
	  * @param activationCode
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value="/emailverification/{activationCode}", method=RequestMethod.GET)
	 public void verifyEmail(@PathVariable("activationCode") String activationCode, HttpServletResponse response) throws Exception {
		 
		 userService.verifyEmail(activationCode, response);
	 }

	@RequestMapping(value="/resetpassword", method=RequestMethod.POST, consumes=APPLICATION_JSON, produces=APPLICATION_JSON)
	public BaseWrapper resetpassword(@RequestBody MResetPasswordTO request) throws Exception {
		
		return userService.resetPasswordFromForgot(request);
	}
	
	@RequestMapping(value="getuserprofileinfo/{trackId}", method=RequestMethod.GET)
	public BaseWrapper getUserProfileDetails(@PathVariable("trackId") String trackId) throws Exception {
		
		return userService.getUserProfileInfoAll(trackId);
	}
	 
}
