package co.in.replete.komalindustries.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.beans.DistributorDetailsTO;
import co.in.replete.komalindustries.beans.DistributorTO;
import co.in.replete.komalindustries.beans.SmsDtlsWrapper;
import co.in.replete.komalindustries.beans.UserAddTO;
import co.in.replete.komalindustries.beans.UserDetailsAllTO;
import co.in.replete.komalindustries.beans.UserDetailsTO;
import co.in.replete.komalindustries.beans.UserOrderDetailsTO;
import co.in.replete.komalindustries.beans.entity.AddressDetail;
import co.in.replete.komalindustries.beans.entity.ContactDtls;
import co.in.replete.komalindustries.beans.entity.LocationDtls;
import co.in.replete.komalindustries.beans.entity.OtpDetails;
import co.in.replete.komalindustries.beans.entity.SmsDtls;
import co.in.replete.komalindustries.beans.entity.UserDetailsAssociatedTO;
import co.in.replete.komalindustries.beans.entity.UserLoginDtl;
import co.in.replete.komalindustries.webcontroller.beans.WUserDetailsTO;

public interface UserManagementDAO {


	List<UserDetailsTO> selectUserDetails(String emailId, String password, String cmpnyInfoId);
	
	List<UserDetailsAssociatedTO> selectAssociatedDistributorsList(String userTrackId);

	List<UserLoginDtl> selectUserCount(String loginId);

	int selectEmailIdCount(String emailId);

	int selectContactNumCount(String contactNum);

	int selectDisplayNameCount(String displayName);

	int selectPanCount(String panNum);

	int selectVatTinCount(String vatTinNum);

	String insertUserDtl(String firstName, String lastName, String contactNum, String displayName, String panNum,
			String vatTinNum, String cmpnyInfoId, String gstNo, Float discount);

	String insertLoginDtl(String userTrackid, String emailId, String password, String userType, String cmpnyInfoId,String activationCode);

	List<UserDetailsTO> selectUserDetailsById(String id);

	int insertAssociatedDistributed(String trackid, String distributorTrackId) throws Exception;
	
	void deleteAssociatedDistributed(String dealerTrackId, String userDistributorListId) throws Exception;

	List<DistributorTO> selectDistributor(String dealerTrackId) throws DataAccessException;
	
	void updateUserDetail(String trackid, String firstName, String lastName, String cntc_num, String gstNo/*, String vatNo, String panNo*/);
	
	Integer insertOTPDetails(Object... otpDetailsParams);

	//OTP Related DAO
//	OtpDetails selectOTPDetails(String deviceInfo, String cellNum, Integer otpId);
	
	void updateOTPRecord(int numofAttempts, int otpId, int otp);

	List<UserLoginDtl> selectUserLoginDtlId(String id, String oldPassWord) throws DataAccessException;

	void updatePassword(String userLoginDtlId, String newPassword) ;
	
	List<WUserDetailsTO> selectWuserDetails();

	List<UserDetailsAllTO> selectUserDetailsByTrackId(String trackid) throws Exception;

	List<UserOrderDetailsTO> selectUserOrderDetails(int orderId) throws Exception;

	void updateUserDtl(UserAddTO request);

	List<UserLoginDtl> selectUserDetailsByActivationCode(String activationCode);

	void updateUserActivationDetails(String userLoginDtlsId);

	List<OtpDetails> selectOTPDetails(String cellNum, String otp);

	UserLoginDtl selectUserDetailsByContactNum(String cellNum) throws Exception;

	void updateUserPassword(String loginId, String newPassword);

	void insertForgotPasswordOTPDetails(String loginId, int otp);

	List<OtpDetails> selectOtpDetailsByCellNum(String loginId);

	void updateOtpDetails(int otp, String loginId);

	List<WUserDetailsTO> selectWuserDetailsByCriteria(String sql);

	AddressDetail selectUsersDefaultShippindAddress(String trackId) throws Exception;

	List<UserDetailsTO> selectUserProfileDetails(String trackid);

	List<LocationDtls> getStateList() throws Exception;

	void updateUserEmailId(String loginDtlsId, String loginId);

	AddressDetail selectUserDefaultShippingAddressDetails(String trackid);

	void updateUserDefaultShippingAddress(int addressId, String address, String city, String state, String pincode, String mark,
			String destination, String tinNo, String tranNm);

	int insertUserDefaultShippingAddressDetails(String address, String city, String state, String pincode,String trackid, String mark,
			String destination, String tinNo, String tranNm);

	List<UserDetailsTO> selectUserDetailsBytrackIdWoAddrDetails(String id);

	List<DistributorDetailsTO> selectDistributorDetailsList();

	void insertDistributorDetails(String userTrackid, int addressDtlsId);

	String insertWLoginDtl(String userTrackId, String emailId, String password, String userType, String cmpnyInfoId,
			String activationCode, String userStatus);

	void updateAssociatedDistributor(int userDistributorListId, String associatedDistributor);

	int updateGstNo(String trackId, String gstNo);

	void updateUserDtls(String trackId, String status);

	List<ContactDtls> getAllContactDirectories();

	List<SmsDtlsWrapper> getAllSmsDtls(Integer pageNumber, Integer pageDisplayLength);

	int addContactDirectories(ContactDtls contactDtls);

	int editContactDirectories(ContactDtls contactDtls);

	int activateDeactivateContactDetails(int status, int contactDtlsId);

	int activateDeactivateSmsDetails(int status, int smsDtlsId);

	int editContactDirectories(SmsDtls smsDtls);

	int addSmsDtls(int contactDtlsId,String str);

	List<ContactDtls> selectName(String contactNo);

	int selectContactNum(String contactNo);

	int selectContact(String contactNo);

	String select(String contactNo);

	ContactDtls getContactDetails(String contactNo);

	int getCountOfTotalRecords();

//	Integer selectOTPRecord(String deviceInfo, String cellNum, int otp);

//	void updateOTPRecord(String otp, Integer otpId);
	
}
