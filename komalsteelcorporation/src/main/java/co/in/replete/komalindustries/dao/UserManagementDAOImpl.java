package co.in.replete.komalindustries.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import co.in.replete.komalindustries.beans.DistributorDetailsTO;
import co.in.replete.komalindustries.beans.DistributorTO;
import co.in.replete.komalindustries.beans.UserAddTO;
import co.in.replete.komalindustries.beans.UserDetailsAllTO;
import co.in.replete.komalindustries.beans.UserDetailsTO;
import co.in.replete.komalindustries.beans.UserOrderDetailsTO;
import co.in.replete.komalindustries.beans.entity.AddressDetail;
import co.in.replete.komalindustries.beans.entity.AppConfiguration;
import co.in.replete.komalindustries.beans.entity.LocationDtls;
import co.in.replete.komalindustries.beans.entity.OtpDetails;
import co.in.replete.komalindustries.beans.entity.UserDetailsAssociatedTO;
import co.in.replete.komalindustries.beans.entity.UserLoginDtl;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.utils.UDValues;
import co.in.replete.komalindustries.webcontroller.beans.WUserDetailsTO;

@Repository
public class UserManagementDAOImpl extends BaseDAOImpl implements UserManagementDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Properties sqlProperties; 
	
	@Autowired
	AdminDAO adminDAO;
	
	/**
	 * Description : Get User Details
	 * @param{@link emailId password userType cmpnyInfoId }
	 * @return{@link List<UserDetailsTO>}
	 * @throws{@link Exception}
	 */
	@Override
	public List<UserDetailsTO> selectUserDetails(String cntcNum, String password, String cmpnyInfoId) {
		
		return jdbcTemplate.query(sqlProperties.getProperty("select.userdetailslogin.all"), 
				new Object[] {cntcNum, password,cmpnyInfoId}, new RowMapper<UserDetailsTO>() {
				@Override
				public UserDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					UserDetailsTO userDetailsTO = new UserDetailsTO();
					userDetailsTO.setCmpnyInfoId(rs.getString("CMPNY_INFO_ID"));
					userDetailsTO.setCntc_num(rs.getString("CNTC_NUM"));
					userDetailsTO.setDob(rs.getDate("DOB"));
					userDetailsTO.setFirstName(rs.getString("FIRST_NAME"));
					userDetailsTO.setGender(rs.getString("GENDER"));
					userDetailsTO.setLastName(rs.getString("LAST_NAME"));
					userDetailsTO.setLoginId(rs.getString("LOGIN_ID"));
					userDetailsTO.setPanNo(rs.getString("PAN_NO"));
					userDetailsTO.setStatus(rs.getString("STATUS"));
					userDetailsTO.setTinNo(rs.getString("VAT_TIN_NO"));
					userDetailsTO.setUserType(rs.getString("USER_TYPE"));
					userDetailsTO.setUserTrackId(rs.getString("TRACK_ID"));
					userDetailsTO.setDisplayName(rs.getString("DISPLAY_NAME"));
					
					return userDetailsTO;
				}
				});
	}

	/**
	 * Executes the select to get particular details list of the distributors
	 * 
	 * @return  {@link List<UserDtl>}
	 * @throws  {@link Exception}
	 */
	
	
	/**
	 * Description: Select Associated distributor list
	 * @param{@link userTrackId }
	 * @return{@link List<UserDtl>}
	 * @throws{@link Exception}
	 * 
	 */
	@Override
	public List<UserDetailsAssociatedTO> selectAssociatedDistributorsList(String userTrackId) {
		
		return jdbcTemplate.query(sqlProperties.getProperty("select.userdtl.associateddistributorslist"), new Object[] {userTrackId}, new RowMapper<UserDetailsAssociatedTO>() {
			@Override
			public UserDetailsAssociatedTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				UserDetailsAssociatedTO userDtl = new UserDetailsAssociatedTO();
				userDtl.setTrackId(rs.getString("TRACK_ID"));
				userDtl.setFirstName(rs.getString("FIRST_NAME"));
				userDtl.setLastName(rs.getString("LAST_NAME"));
				userDtl.setDisplayName(rs.getString("DISPLAY_NAME"));
				userDtl.setUserDistributionListId(rs.getInt("USER_DISTRIBUTION_LIST_ID"));
				return userDtl;
			}
		});
	}
    
	/**
	 * Description: Select user count
	 * @param {@link loginId}
	 * @return{@link List<UserLoginDtl>}
	 * @throws {@link DataAccessException}
	 * 
	 */
	@Override
	public List<UserLoginDtl> selectUserCount(String loginId) {


		return jdbcTemplate.query(sqlProperties.getProperty("select.loginid.recordcount"), new Object[] {loginId}, new RowMapper<UserLoginDtl>() {
			@Override
			public UserLoginDtl mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				UserLoginDtl userLoginDtl = new UserLoginDtl();
				userLoginDtl.setTrackId(rs.getString("TRACK_ID"));
				return userLoginDtl;
			}
		});
	}
    
	/**
	 * Description: select emailID count
	 * @param {@link emailId}
	 * @return {@link emailIdCount}
	 * @throws {@link DataAccessException}
	 */
	@Override
	public int selectEmailIdCount(String emailId) {

		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.count.emailid"),Integer.class,emailId);
	}

	/**
	 * Description: select contact number count
	 * @param {@link contactNum}
	 * @return {@link contactnumber count}
	 * @throws {@link DataAccessException}
	 */
	@Override
	public int selectContactNumCount(String contactNum) {

	    return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.count.contactno"),Integer.class, contactNum);
	}

	/**
	 * Description: select display name count
	 * @param {@link displayName}
	 * @return {@link displayName count}
	 * @throws {@link DataAccessException}
	 */
	@Override
	public int selectDisplayNameCount(String displayName) {

		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.count.displayname"),Integer.class,displayName);
	}

	/**
	 * Description: select PAN number count
	 * @param {@link panNum}
	 * @return {@link panNum count
	 * @throws {@link DataAccessException}
	 * 
	 */
	@Override
	public int selectPanCount(String panNum) {

		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.count.pannumber"),Integer.class, panNum);
	}

	/**
	 * Description: select VAT TIN number count
	 * @param {@link vatTinNum}
	 * @return {@link vat number count}
	 * @throws {@link DataAccessException}
	 */
	@Override
	public int selectVatTinCount(String vatTinNum) {

		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.count.vattinnumber"),Integer.class,vatTinNum);
	}

	/**
	 * Description: insert user deatils
	 * @param {@link firstName, lastName, contactNum, displayName, panNum, vatTinNum, cmpnyInfoId}
	 * @throws {@link DataAccessException}
	 */
	@Override
	public String insertUserDtl(final String firstName, final String lastName, final String contactNum, final String displayName, final String panNum,
			final String vatTinNum, final String cmpnyInfoId, String gstNo, Float discount) {

		final String uuid = getUUID();
		
		jdbcTemplate.update(new PreparedStatementCreator() {           

		                @Override
		                public PreparedStatement createPreparedStatement(Connection connection)
		                        throws SQLException {
		                    PreparedStatement ps = connection.prepareStatement(sqlProperties.getProperty("insert.userdtls.newuserdetails"), Statement.RETURN_GENERATED_KEYS);
		                    ps.setString(1, uuid);
		                    ps.setString(2, firstName);
		                    ps.setString(3, lastName);
		                    ps.setString(4, vatTinNum);
		                    ps.setString(5, panNum);
		                    ps.setString(6, contactNum);
		                    ps.setString(7, cmpnyInfoId);
		                    ps.setString(8, displayName);
		                    ps.setString(9, gstNo);
		                    ps.setFloat(10, discount);
		                    return ps;
		                }
		            });

		return uuid;
	}

	/**
	 * Description: insert user deatils
	 * @param {@link emailId, password, userType, cmpnyInfoId}
	 * @throws {@link DataAccessException}
	 */
	@Override
	public String insertLoginDtl(String userTrackId, String emailId, String password, String userType, String cmpnyInfoId,String activationCode) {

		String uuid = getUUID();
		
		jdbcTemplate.update(sqlProperties.getProperty("insert.userlogindtls.newlogindetails"),uuid,userTrackId,emailId,password,cmpnyInfoId,userType,UDValues.BOOLEAN_TRUE.toString(),
				UDValues.BOOLEAN_TRUE.toString(), UDValues.BOOLEAN_FALSE.toString(), UDValues.USER_STATUS_INACTIVE.toString(),activationCode);
		
		return uuid;
	}
	
	/**
	 * Description: insert user deatils
	 * @param {@link emailId, password, userType, cmpnyInfoId}
	 * @throws {@link DataAccessException}
	 */
	@Override
	public String insertWLoginDtl(String userTrackId, String emailId, String password, String userType, String cmpnyInfoId,String activationCode, String userStatus) {

		String uuid = getUUID();
		
		jdbcTemplate.update(sqlProperties.getProperty("insert.userlogindtls.newlogindetails"),uuid,userTrackId,emailId,password,cmpnyInfoId,userType,UDValues.BOOLEAN_TRUE.toString(),
				UDValues.BOOLEAN_TRUE.toString(), UDValues.BOOLEAN_FALSE.toString(), userStatus,activationCode);
		
		return uuid;
	}

	/**
	 * Description : select user details by Id
	 * @param{@link id }
	 * @return{@link List<UserDetailsTO>}
	 * @throws{@link Exception}
	 */
	@Override
	public List<UserDetailsTO> selectUserDetailsById(String id) {
	
		return jdbcTemplate.query(sqlProperties.getProperty("select.userdetails.byid"), new Object[] {id,id}, new RowMapper<UserDetailsTO>() {
			@Override
			public UserDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserDetailsTO userDetailsTO = new UserDetailsTO();
				
				userDetailsTO.setCmpnyInfoId(rs.getString("CMPNY_INFO_ID"));
				userDetailsTO.setFirstName(rs.getString("FIRST_NAME"));
				userDetailsTO.setLastName(rs.getString("LAST_NAME"));
				userDetailsTO.setLoginId(rs.getString("LOGIN_ID"));
				userDetailsTO.setCntc_num(rs.getString("CNTC_NUM"));
				userDetailsTO.setPanNo(rs.getString("PAN_NO"));
				userDetailsTO.setTinNo(rs.getString("VAT_TIN_NO"));
				userDetailsTO.setUserType(rs.getString("USER_TYPE"));
				userDetailsTO.setStatus(rs.getString("STATUS"));
				userDetailsTO.setDisplayName(rs.getString("DISPLAY_NAME"));
				userDetailsTO.setLoginDtlsId(rs.getString("USER_LOGIN_DTLS_ID"));
				
				return userDetailsTO;
			}
		});
	}
	
	@Override
	public List<UserDetailsTO> selectUserDetailsBytrackIdWoAddrDetails(String id) {
	
		return jdbcTemplate.query(sqlProperties.getProperty("select.userdetails.bytrackid"), new Object[] {id}, new RowMapper<UserDetailsTO>() {
			@Override
			public UserDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserDetailsTO userDetailsTO = new UserDetailsTO();
				
				userDetailsTO.setCmpnyInfoId(rs.getString("CMPNY_INFO_ID"));
				userDetailsTO.setFirstName(rs.getString("FIRST_NAME"));
				userDetailsTO.setLastName(rs.getString("LAST_NAME"));
				userDetailsTO.setLoginId(rs.getString("LOGIN_ID"));
				userDetailsTO.setCntc_num(rs.getString("CNTC_NUM"));
				userDetailsTO.setPanNo(rs.getString("PAN_NO"));
				userDetailsTO.setTinNo(rs.getString("VAT_TIN_NO"));
				userDetailsTO.setUserType(rs.getString("USER_TYPE"));
				userDetailsTO.setStatus(rs.getString("STATUS"));
				userDetailsTO.setDisplayName(rs.getString("DISPLAY_NAME"));
				userDetailsTO.setLoginDtlsId(rs.getString("USER_LOGIN_DTLS_ID"));
				
				return userDetailsTO;
			}
		});
	}
	
	/**
	 * Description: update user details
	 * @param{@link trackid firstName lastName  cntc_num}
	 * @throws{@link Exception }
	 */
	@Override
	public void updateUserDetail(String trackid, String firstName, String lastName, String displayName, String gstNo 
			/*, String vatNo, String panNo*/) {
		
		 jdbcTemplate.update(sqlProperties.getProperty("update.userdetails.bytrackid"),firstName,lastName,displayName, gstNo,trackid);
		
	}
	
	@Override
	public Integer insertOTPDetails(Object... otpDetailsParams) {

		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sqlProperties.getProperty("insert.otpdetails"), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, otpDetailsParams[0].toString());
				ps.setString(2, otpDetailsParams[1].toString());
				ps.setInt(3, (Integer) otpDetailsParams[2]);
				ps.setInt(4, (Integer) otpDetailsParams[3]);
				
				return ps;
			}
		};
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(psc, holder);
		
		return Integer.parseInt(holder.getKey().toString());
	}

	/**
	 * Description: insert associated distributor and return primary key
	 * @param{@link trackid distributorTrackId }
	 * @return{@link generatedPrimaryKeyValue}
	 * @throws{@link Exception}
	 */
	@Override
	public int insertAssociatedDistributed(String trackid, String distributorTrackId) throws Exception {

   PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				PreparedStatement psmt = con.prepareStatement(sqlProperties.getProperty("insert.user_distributors_list.associteddistributor"), Statement.RETURN_GENERATED_KEYS);
				psmt.setString(1,trackid);
				psmt.setString(2,distributorTrackId);
			
				return psmt;
				
			}
		};
		
		//Create a holder to hold the auto generated primary key
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(psc, holder);
		
		//Get generated Primary key
		int generatedPrimaryKeyValue = holder.getKey().intValue();
		return generatedPrimaryKeyValue;
	}
	
	/**
	 * Description: Delete associated distributors
	 * @param{@link dealerTrackId userDistributorListId}
	 * @throws{@link Exception}
	 */
	@Override
	public void deleteAssociatedDistributed(String dealerTrackId, String userDistributorListId) throws Exception {

		jdbcTemplate.update(sqlProperties.getProperty("delete.user_distributors_list.associteddistributor"),dealerTrackId, userDistributorListId);
	}

	/**
	 * Description: select  distributor 
	 * @param{@link dealerTrackId}
	 * @return{@link List<DistributorTO>}
	 * @throws {@link DataAccessException}
	 */
	@Override
	public List<DistributorTO> selectDistributor(String dealerTrackId) throws DataAccessException {
		
		return jdbcTemplate.query(sqlProperties.getProperty("select.userdetails.distributor"),new Object[]{dealerTrackId},  new RowMapper<DistributorTO>() {
			@Override
			public DistributorTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				DistributorTO distributorTO = new DistributorTO();
				
				distributorTO.setTrackId(rs.getString("TRACK_ID"));
				distributorTO.setFirstName(rs.getString("FIRST_NAME"));
				distributorTO.setLastName(rs.getString("LAST_NAME"));
				distributorTO.setDisplayName(rs.getString("DISPLAY_NAME"));
				
				return distributorTO;
			}
		});
	}
	

	/*@Override
	public OtpDetails selectOTPDetails(String deviceInfo, String cellNum, Integer otp) throws DataAccessException{
		List<OtpDetails> otpDetailsList = null;
		if(null == otp) {
		  otpDetailsList = jdbcTemplate.query(sqlProperties.getProperty("select.otpdetails.bydeviceinfocellno"), new Object[] {deviceInfo, cellNum}, new BeanPropertyRowMapper<>(OtpDetails.class));
		} else {
		  otpDetailsList = jdbcTemplate.query(sqlProperties.getProperty("select.otpdetails.bydeviceinfocellnonotp"), new Object[] {deviceInfo, cellNum, otp}, new BeanPropertyRowMapper<>(OtpDetails.class));	
		}
		if(otpDetailsList.size() == 1) {
			return otpDetailsList.get(0);
		} else {
			return null;
		}
	}
	*/
	@Override
	public void updateOTPRecord(int numofAttempts, int otpId, int otp) {
		jdbcTemplate.update(sqlProperties.getProperty("update.otpdetails.all"), otp, numofAttempts, otpId);
	}

	/**
	 * Description: select user login details id 
	 */
	@Override
	public List<UserLoginDtl> selectUserLoginDtlId(String id, String oldPassWord) throws DataAccessException {
		return jdbcTemplate.query(sqlProperties.getProperty("select.userlogindtl.userlogindtlid"), new Object[] {id,oldPassWord}, new RowMapper<UserLoginDtl>() {
			@Override
			public UserLoginDtl mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserLoginDtl userLoginDtl = new UserLoginDtl();
				
				userLoginDtl.setUserLoginDtlsId(rs.getString("USER_LOGIN_DTLS_ID"));
			
				return userLoginDtl;
			}
		});
}

	@Override
	public void updatePassword(String userLoginDtlId, String newPassword) {

		 jdbcTemplate.update(sqlProperties.getProperty("update.userlogindtl.password"),newPassword,userLoginDtlId);
	}

	@Override
	public List<WUserDetailsTO> selectWuserDetails() {
		AppConfiguration appConfig=adminDAO.selectConfigurationValue(KomalIndustriesConstants.WMAX_LIMIT);
		int wmaxRecords=Integer.parseInt(appConfig.getConfigVal());
		return jdbcTemplate.query(sqlProperties.getProperty("select.userdetails.all"),new Object[] {UDValues.ADDRES_TYPE_DEFAULT_SHIPPING.toString(), wmaxRecords}, 
				new RowMapper<WUserDetailsTO>() {
			@Override
			public WUserDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				WUserDetailsTO wUserDetailsTO = new WUserDetailsTO();
				
				wUserDetailsTO.setUserTrackid(rs.getString("TRACK_ID"));
				wUserDetailsTO.setUserFirstName(rs.getString("FIRST_NAME"));
				wUserDetailsTO.setUserLastName(rs.getString("LAST_NAME"));
				wUserDetailsTO.setDisplayName(rs.getString("DISPLAY_NAME"));
				wUserDetailsTO.setStatus(rs.getString("STATUS"));
				wUserDetailsTO.setPanNo(rs.getString("PAN_NO"));
				wUserDetailsTO.setVatNo(rs.getString("VAT_TIN_NO"));
				wUserDetailsTO.setContactNo(rs.getString("CNTC_NUM"));
				wUserDetailsTO.setEmailId(rs.getString("LOGIN_ID"));
				wUserDetailsTO.setUserType(rs.getString("USER_TYPE"));
				wUserDetailsTO.setRegDate(rs.getDate("CREATED_TS"));
				wUserDetailsTO.setStAddress1(rs.getString("ST_ADDRESS_1"));
				wUserDetailsTO.setState(rs.getString("STATE"));
				wUserDetailsTO.setCity(rs.getString("CITY"));
				wUserDetailsTO.setPincode(rs.getString("POSTAL_CODE"));
				wUserDetailsTO.setAssociatedDistributor(rs.getString("DISTRIBUTOR_TRACK_ID"));
				wUserDetailsTO.setOtherAddressDtlsId(rs.getInt("OTHER_ADDRESS_ID"));
				wUserDetailsTO.setUserDistributorListId(rs.getInt("USER_DISTRIBUTION_LIST_ID"));
				wUserDetailsTO.setMark(rs.getString("MARK"));
				wUserDetailsTO.setDestination(rs.getString("DESTINATION"));
				wUserDetailsTO.setTransportName(rs.getString("TRAN_NM"));
				wUserDetailsTO.setGstNo(rs.getString("GSTNO"));
				wUserDetailsTO.setDiscount(rs.getFloat("DISCOUNT"));
				return wUserDetailsTO;
			}
		});
	}

	@Override
	public List<UserDetailsAllTO> selectUserDetailsByTrackId(String trackid) throws Exception {
		return jdbcTemplate.query(sqlProperties.getProperty("select.userdetailsbytrackid.all"), 
				new Object[] {trackid}, new RowMapper<UserDetailsAllTO>() {
				@Override
				public UserDetailsAllTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					UserDetailsAllTO userDetailsAllTO = new UserDetailsAllTO();
					
					userDetailsAllTO.setTrackId(rs.getString("TRACK_ID"));
					userDetailsAllTO.setFirstName(rs.getString("FIRST_NAME"));
					userDetailsAllTO.setLastName(rs.getString("LAST_NAME"));
					userDetailsAllTO.setDisplayName(rs.getString("DISPLAY_NAME"));
					userDetailsAllTO.setCity(rs.getString("CITY"));
					userDetailsAllTO.setContactNo(rs.getString("CNTC_NUM"));
					userDetailsAllTO.setCountry(rs.getString("COUNTRY"));
					userDetailsAllTO.setEmailId(rs.getString("LOGIN_ID"));
					userDetailsAllTO.setPanNo(rs.getString("PAN_NO"));
					userDetailsAllTO.setPostalCode(rs.getString("POSTAL_CODE"));
					userDetailsAllTO.setRegDate(rs.getDate("CREATED_TS"));
					userDetailsAllTO.setState(rs.getString("STATE"));
					userDetailsAllTO.setStatus(rs.getString("STATUS"));
					userDetailsAllTO.setStreet1(rs.getString("ST_ADDRESS_1"));
					userDetailsAllTO.setStreet2(rs.getString("ST_ADDRESS_2"));
					userDetailsAllTO.setStreet3(rs.getString("ST_ADDRESS_3"));
					userDetailsAllTO.setVatNo(rs.getString("VAT_TIN_NO"));
					userDetailsAllTO.setUserType(rs.getString("USER_TYPE"));
					userDetailsAllTO.setAssociatedDistributorTrackId(rs.getString("DISTRIBUTOR_TRACK_ID"));
					userDetailsAllTO.setMark(rs.getString("MARK"));
					userDetailsAllTO.setDestination(rs.getString("DESTINATION"));
					userDetailsAllTO.setTranNm(rs.getString("TRAN_NM"));
					
					return userDetailsAllTO;
				}
				});
	}

	@Override
	public List<UserOrderDetailsTO> selectUserOrderDetails(int orderId) throws Exception {
		return jdbcTemplate.query(sqlProperties.getProperty("select.orderdetails.allordertable"), 
				new Object[] {orderId}, new RowMapper<UserOrderDetailsTO>() {
				@Override
				public UserOrderDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					UserOrderDetailsTO userOrderDetailsTO = new UserOrderDetailsTO();
					
					userOrderDetailsTO.setAlternateContactNo(rs.getString("ALTERNATE_CNTC"));
					/*userOrderDetailsTO.setAmountBal(rs.getDouble("AMOUNT_BAL"));
					userOrderDetailsTO.setAmountPaid(rs.getDouble("AMOUNT_PAID"));*/
					userOrderDetailsTO.setCartDtlId(rs.getInt("CART_DTLS_ID"));
					userOrderDetailsTO.setCartNotes(rs.getString("CART_NOTES"));
					userOrderDetailsTO.setCartPrice(rs.getDouble("CART_PRICE"));
					userOrderDetailsTO.setCartStatus(rs.getString("CART_STATUS"));
					userOrderDetailsTO.setCity(rs.getString("CITY"));
					/*userOrderDetailsTO.setDiscount(rs.getDouble("DISCOUNT"));
					userOrderDetailsTO.setDiscountValue(rs.getDouble("DISCOUNT_VALUE"));*/
					userOrderDetailsTO.setExpectedDlvrDate(rs.getDate("EXP_DLVRY_DT"));
//					userOrderDetailsTO.setGrandTotal(rs.getDouble("GRAND_TOTAL"));
//					userOrderDetailsTO.setInvoiceId(rs.getInt("INVOICE_DTLS_ID"));
					userOrderDetailsTO.setItemsLoaded(rs.getString("NO_OF_CARTON_LOADED"));
					userOrderDetailsTO.setLrDate(rs.getString("LR_NO_DATE"));
					userOrderDetailsTO.setLrNo(rs.getString("LR_NO"));
//					userOrderDetailsTO.setMiscCharges(rs.getDouble("MISC_CHARGES"));
					userOrderDetailsTO.setOrderDate(rs.getDate("CREATED_TS"));
					/*userOrderDetailsTO.setPaymentStatus(rs.getString("PAYMENT_STATUS"));
					userOrderDetailsTO.setServiceTax(rs.getDouble("SERVICE_TAX"));
					userOrderDetailsTO.setServiceTaxValue(rs.getDouble("SERVICE_TAX_VALUE"));
					*/userOrderDetailsTO.setSt1(rs.getString("ST_ADDRESS_1"));
					userOrderDetailsTO.setState(rs.getString("STATE"));
					/*userOrderDetailsTO.setSubTotal(rs.getDouble("SUB_TOTAL"));
					userOrderDetailsTO.setTaxRefNo(rs.getString("TXN_REF_NO"));
					userOrderDetailsTO.setVat(rs.getDouble("VAT"));
					userOrderDetailsTO.setVatValue(rs.getDouble("VAT_VALUE"));
*/
					userOrderDetailsTO.setMark(rs.getString("MARK"));
					userOrderDetailsTO.setDestination(rs.getString("DESTINATION"));
					userOrderDetailsTO.setTinNo(rs.getString("TINNO"));
					userOrderDetailsTO.setTranNm(rs.getString("TRAN_NM"));
					
					return userOrderDetailsTO;
				}
				});
	}
	
	@Override
	public void updateUserDtl(UserAddTO request) {
		
		jdbcTemplate.update("update user_login_dtls set STATUS=?,USER_TYPE=?,LOGIN_ID=? where TRACK_ID=?", new Object[] {request.getStatus(), 
				request.getUserType(), request.getEmailId(), request.getUserId()});
		jdbcTemplate.update("update user_dtls set FIRST_NAME=?,LAST_NAME=?,PAN_NO=?,VAT_TIN_NO=?,DISPLAY_NAME=?,"
				+ "CNTC_NUM=?,GSTNO=?,DISCOUNT=? where TRACK_ID=?", new Object[] {request.getFirstName(), request.getLastName(), request.getPanNo(),
				request.getVatNo(), request.getDisplayName(), request.getContactNo(), request.getGstNo(), request.getDiscount(), request.getUserId()});
		
	}
	
	@Override
	public List<UserLoginDtl> selectUserDetailsByActivationCode(String activationCode) {
		return jdbcTemplate.query(sqlProperties.getProperty("select.userlogindtls.byactivationcode"), new Object[] {activationCode}
		, new BeanPropertyRowMapper<UserLoginDtl>(UserLoginDtl.class));
	}
	
	@Override
	public void updateUserActivationDetails(String userLoginDtlsId) {
		jdbcTemplate.update(sqlProperties.getProperty("update.userlogindtls.activationdetails"), new Object[] {UDValues.BOOLEAN_TRUE.toString(), 
				UDValues.USER_STATUS_ACTIVE.toString(),	-11, userLoginDtlsId});
		
	}
	
	@Override
	public List<OtpDetails> selectOTPDetails(String cellNum, String otp) {
//		System.out.println(cellNum + otp);
		return jdbcTemplate.query("select * from otp_details where CELLNUMBER=? and OTP=?", new Object[] {cellNum, otp}
		, new BeanPropertyRowMapper<OtpDetails>(OtpDetails.class));
	}
	
	@Override
	public UserLoginDtl selectUserDetailsByContactNum(String cellNum) throws Exception {
		
		List<UserLoginDtl> userDetails = jdbcTemplate.query("select * from user_login_dtls as uld inner join user_dtls as ud on uld.TRACK_ID=ud.TRACK_ID and ud.CNTC_NUM=?", 
				new Object[] {cellNum}, new RowMapper<UserLoginDtl>(){
			@Override
			public UserLoginDtl mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserLoginDtl userLoginDtls = new UserLoginDtl();
				
				userLoginDtls.setUserLoginDtlsId(rs.getString("USER_LOGIN_DTLS_ID"));
				
				return userLoginDtls;
			}
		}); 
		
		if(userDetails.size() == 1) {
			return userDetails.get(0);
		} else {
			throw new Exception("Invalid User details provided");
		}
	}
	
	@Override
	public void updateUserPassword(String loginId, String newPassword) {
		
		jdbcTemplate.update("update user_login_dtls set PASSWORD=? where USER_LOGIN_DTLS_ID=?", newPassword, loginId);
	}
	
	@Override
	public void insertForgotPasswordOTPDetails(String loginId, int otp) {
		
		jdbcTemplate.update("insert into otp_details(CELLNUMBER,OTP) values(?,?)", new Object[] {loginId, otp});
	}
	
	@Override
	public List<OtpDetails> selectOtpDetailsByCellNum(String loginId) {
		
		return jdbcTemplate.query("select * from otp_details where CELLNUMBER=?", new Object[] {loginId}, new BeanPropertyRowMapper<OtpDetails>(OtpDetails.class));
	}
	
	@Override
	public void updateOtpDetails(int otp, String loginId) {
		
		jdbcTemplate.update("update otp_details set OTP=? where CELLNUMBER=?", new Object[] {otp, loginId});
	}
	
	@Override
	public List<WUserDetailsTO> selectWuserDetailsByCriteria(String sql) {
		
		return jdbcTemplate.query(sql, new RowMapper<WUserDetailsTO>() {
			@Override
			public WUserDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				WUserDetailsTO wUserDetailsTO = new WUserDetailsTO();
				
				wUserDetailsTO.setUserTrackid(rs.getString("TRACK_ID"));
				wUserDetailsTO.setUserFirstName(rs.getString("FIRST_NAME"));
				wUserDetailsTO.setUserLastName(rs.getString("LAST_NAME"));
				wUserDetailsTO.setDisplayName(rs.getString("DISPLAY_NAME"));
				wUserDetailsTO.setStatus(rs.getString("STATUS"));
				wUserDetailsTO.setPanNo(rs.getString("PAN_NO"));
				wUserDetailsTO.setVatNo(rs.getString("VAT_TIN_NO"));
				wUserDetailsTO.setContactNo(rs.getString("CNTC_NUM"));
				wUserDetailsTO.setEmailId(rs.getString("LOGIN_ID"));
				wUserDetailsTO.setUserType(rs.getString("USER_TYPE"));
				wUserDetailsTO.setRegDate(rs.getDate("CREATED_TS"));
				wUserDetailsTO.setStAddress1(rs.getString("ST_ADDRESS_1"));
				wUserDetailsTO.setState(rs.getString("STATE"));
				wUserDetailsTO.setCity(rs.getString("CITY"));
				wUserDetailsTO.setPincode(rs.getString("POSTAL_CODE"));
				wUserDetailsTO.setAssociatedDistributor(rs.getString("DISTRIBUTOR_TRACK_ID"));
				wUserDetailsTO.setOtherAddressDtlsId(rs.getInt("OTHER_ADDRESS_ID"));
				wUserDetailsTO.setUserDistributorListId(rs.getInt("USER_DISTRIBUTION_LIST_ID"));
				wUserDetailsTO.setMark(rs.getString("MARK"));
				wUserDetailsTO.setDestination(rs.getString("DESTINATION"));
				wUserDetailsTO.setTransportName(rs.getString("TRAN_NM"));
				wUserDetailsTO.setGstNo(rs.getString("GSTNO"));
				wUserDetailsTO.setDiscount(rs.getFloat("DISCOUNT"));
				
				return wUserDetailsTO;
			}
		});
	}
	
	@Override
	public AddressDetail selectUsersDefaultShippindAddress(String trackId) throws Exception {
		
		List<AddressDetail> userShippingAddressList = jdbcTemplate.query("select * from other_address_details where TRACK_ID=? and ADDRESS_TYPE=?", new Object[] {trackId, 
				UDValues.ADDRES_TYPE_DEFAULT_SHIPPING.toString()}, new RowMapper<AddressDetail>() {
			@Override
			public AddressDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				AddressDetail addressDetail = new AddressDetail();
				addressDetail.setOtherAddressId(rs.getInt("OTHER_ADDRESS_ID"));
				addressDetail.setAddressType(rs.getString("ADDRESS_TYPE"));
				addressDetail.setCity(rs.getString("CITY"));
				addressDetail.setLatitude(rs.getBigDecimal("LATITUDE"));
				addressDetail.setLongitude(rs.getBigDecimal("LONGITUDE"));
				addressDetail.setCountry(rs.getString("COUNTRY"));
				addressDetail.setPostalCode(rs.getString("POSTAL_CODE"));
				addressDetail.setStAddress1(rs.getString("ST_ADDRESS_1"));
				addressDetail.setStAddress2(rs.getString("ST_ADDRESS_2"));
				addressDetail.setStAddress3(rs.getString("ST_ADDRESS_3"));
				addressDetail.setState(rs.getString("STATE"));
				addressDetail.setMark((null == rs.getString("MARK")) ? "" : rs.getString("MARK"));
				addressDetail.setDestination((null == rs.getString("DESTINATION")) ? "" : rs.getString("DESTINATION"));
				addressDetail.setTinNo((null == rs.getString("TINNO")) ? "" : rs.getString("TINNO"));
				addressDetail.setTranNm((null == rs.getString("TRAN_NM")) ? "" : rs.getString("TRAN_NM"));
				
				return addressDetail;
			}
		});
		
		if(userShippingAddressList.size() > 1) {
			throw new Exception("Multiple Shipping address found");
		} if(userShippingAddressList.size() == 0) {
			return null;
		} else {
			return userShippingAddressList.get(0);
		}
		
	}
	
	@Override
	public List<UserDetailsTO> selectUserProfileDetails(String trackid) {
		
		return jdbcTemplate.query(sqlProperties.getProperty("select.userprofiledetails.all"), 
				new Object[] {trackid}, new RowMapper<UserDetailsTO>() {
				@Override
				public UserDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					UserDetailsTO userDetailsTO = new UserDetailsTO();
					userDetailsTO.setCmpnyInfoId(rs.getString("CMPNY_INFO_ID"));
					userDetailsTO.setCntc_num(rs.getString("CNTC_NUM"));
					userDetailsTO.setDob(rs.getDate("DOB"));
					userDetailsTO.setFirstName(rs.getString("FIRST_NAME"));
					userDetailsTO.setGender(rs.getString("GENDER"));
					userDetailsTO.setLastName(rs.getString("LAST_NAME"));
					userDetailsTO.setLoginId(rs.getString("LOGIN_ID"));
					userDetailsTO.setPanNo(rs.getString("PAN_NO"));
					userDetailsTO.setStatus(rs.getString("STATUS"));
					userDetailsTO.setTinNo(rs.getString("VAT_TIN_NO"));
					userDetailsTO.setUserType(rs.getString("USER_TYPE"));
					userDetailsTO.setUserTrackId(rs.getString("TRACK_ID"));
					userDetailsTO.setDisplayName(rs.getString("DISPLAY_NAME"));
					userDetailsTO.setGstNo(rs.getString("GSTNO"));
					
					return userDetailsTO;
				}
				});
	}
	
	@Override
	public List<LocationDtls> getStateList() throws Exception {
		
		return jdbcTemplate.query("select * from location_dtls where LOCATION_PARENT_ID=0 order by LOCATION_NAME", new BeanPropertyRowMapper<LocationDtls>(LocationDtls.class));
	}
	
	@Override
	public void updateUserEmailId(String loginDtlsId, String loginId) {
		
		jdbcTemplate.update("update user_login_dtls set LOGIN_ID=? where USER_LOGIN_DTLS_ID=?", new Object[] {loginId, loginDtlsId});
	}
	
	@Override
	public AddressDetail selectUserDefaultShippingAddressDetails(String trackid) {
//		System.out.println("TRACK_ID : " + trackid);
		List<AddressDetail> addressDetailsList = jdbcTemplate.query("select * from other_address_details where ADDRESS_TYPE=? and TRACK_ID=?", 
				new Object[] {UDValues.ADDRES_TYPE_DEFAULT_SHIPPING.toString(), trackid}, new BeanPropertyRowMapper<AddressDetail>(AddressDetail.class));
		
//		System.out.println(addressDetailsList.size());
		if(addressDetailsList.size() != 1) {
			return null;
		} else {
			return addressDetailsList.get(0);
		}
	}
	
	@Override
	public void updateUserDefaultShippingAddress(int addressId, String address, String city, String state,
			String pincode, String mark,String destination, String tinNo, String tranNm) {
		
		jdbcTemplate.update("update other_address_details set ST_ADDRESS_1=?,CITY=?,STATE=?,POSTAL_CODE=?,MARK=?,DESTINATION=?,TINNO=?,TRAN_NM=? where OTHER_ADDRESS_ID=?", 
				new Object[] {address, city, state, pincode, mark, destination, tinNo, tranNm, addressId});
	}
	
	@Override
	public int insertUserDefaultShippingAddressDetails(String address, String city, String state, String pincode,String trackid, String mark,
			String destination, String tinNo, String tranNm) {
		
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement("insert into other_address_details(ADDRESS_TYPE,ST_ADDRESS_1,CITY,STATE,POSTAL_CODE,TRACK_ID,"
						+ "MARK,DESTINATION,TINNO,TRAN_NM) values(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, UDValues.ADDRES_TYPE_DEFAULT_SHIPPING.toString());
				ps.setString(2, address);
				ps.setString(3, city);
				ps.setString(4, state);
				ps.setString(5, pincode);
				ps.setString(6, trackid);
				ps.setString(7, mark);
				ps.setString(8, destination);
				ps.setString(9, tinNo);
				ps.setString(10, tranNm);
				
				return ps;
			}
		};
		
		//Create a holder to hold the auto generated primary key
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(psc, holder);
		
		return holder.getKey().intValue();
		
	}
	
	@Override
	public List<DistributorDetailsTO> selectDistributorDetailsList() {
		
		return jdbcTemplate.query("select ud.TRACK_ID,ud.FIRST_NAME,ud.LAST_NAME,ud.DISPLAY_NAME from distributor_details as dd inner join user_dtls as ud on dd.TRACK_ID=ud.TRACK_ID", 
				new RowMapper<DistributorDetailsTO>() {
			@Override
			public DistributorDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				DistributorDetailsTO distributorDetailsTO =new DistributorDetailsTO();
				distributorDetailsTO.setDisplayName(rs.getString("DISPLAY_NAME"));
				distributorDetailsTO.setDistributorTrackId(rs.getString("TRACK_ID"));
				distributorDetailsTO.setFirstName(rs.getString("FIRST_NAME"));
				distributorDetailsTO.setLastName(rs.getString("LAST_NAME"));
				
				return distributorDetailsTO;
			}
		});
	}
	
	@Override
	public void insertDistributorDetails(String userTrackid, int addressDtlsId) {
		
		jdbcTemplate.update("insert into distributor_details(TRACK_ID,ADDRESS_DTLS_ID) values(?,?)", userTrackid, addressDtlsId);
	}
	
	@Override
	public void updateAssociatedDistributor(int userDistributorListId, String associatedDistributor) {
		
		jdbcTemplate.update("update user_distributors_list set DISTRIBUTOR_TRACK_ID=? where USER_DISTRIBUTION_LIST_ID=?", new Object[] {associatedDistributor, userDistributorListId});
	}
	
	@Override
	public int updateGstNo(String trackId, String gstNo) {
		
		return jdbcTemplate.update("update user_dtls set GSTNO=? where TRACK_ID=?", gstNo, trackId); 
	}

	@Override
	public void updateUserDtls(String trackId, String status) {
		UserLoginDtl userLoginDtl = jdbcTemplate.queryForObject("select USER_LOGIN_DTLS_ID from user_login_dtls where TRACK_ID=?",new Object[]{trackId}, new BeanPropertyRowMapper<UserLoginDtl>(UserLoginDtl.class));
		jdbcTemplate.update("update user_login_dtls set STATUS=? where USER_LOGIN_DTLS_ID=?",new Object[]{status,userLoginDtl.getUserLoginDtlsId()});
	}

}
