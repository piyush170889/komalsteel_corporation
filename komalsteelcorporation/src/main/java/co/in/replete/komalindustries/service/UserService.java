package co.in.replete.komalindustries.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.ChangePasswordWrapper;
import co.in.replete.komalindustries.beans.DistributorWrapper;
import co.in.replete.komalindustries.beans.LoginRequestWrapper;
import co.in.replete.komalindustries.beans.MResetPasswordTO;
import co.in.replete.komalindustries.beans.NewDistributorWrapper;
import co.in.replete.komalindustries.beans.OTPRequest;
import co.in.replete.komalindustries.beans.RegisterRequestWrapper;
import co.in.replete.komalindustries.beans.UpdateRequestWrapper;

public interface UserService {

	BaseWrapper loginUser(LoginRequestWrapper request) throws Exception;
	
	BaseWrapper resetPasswordFromForgot(MResetPasswordTO request) throws Exception;

	BaseWrapper resetPassword(String loginId) throws Exception;
	
	BaseWrapper registerUser(RegisterRequestWrapper request, HttpServletRequest servletRequest) throws Exception;

	BaseWrapper userDetails(String id) throws Exception;

	BaseWrapper updateUser(String trackid, UpdateRequestWrapper request) throws Exception;
	
	BaseWrapper doSendOTP(OTPRequest request) throws Exception;
	
	BaseWrapper doVerifyOTP(OTPRequest request) throws Exception;

	BaseWrapper assoDistributorList(String trackid, NewDistributorWrapper request)throws Exception;
	
	BaseWrapper deleteAssociatedDistributorList(String trackid, DistributorWrapper request)throws Exception;

	BaseWrapper getDistributorList(String dealerTrackId ) throws Exception;

	BaseWrapper newPassword(String id,ChangePasswordWrapper request) throws Exception;

	BaseWrapper userDetailsByTrackId(String trackid) throws Exception;

	void verifyEmail(String activationCode, HttpServletResponse response) throws Exception;

	BaseWrapper getUserProfileInfoAll(String trackid) throws Exception;
}
