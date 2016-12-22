package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the user_login_dtls database table.
 * 
 */
@Entity
@Table(name="user_login_dtls")
@NamedQuery(name="UserLoginDtl.findAll", query="SELECT u FROM UserLoginDtl u")
public class UserLoginDtl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4637904324637947797L;

	@Id
	@Column(name="USER_LOGIN_DTLS_ID")
	private String userLoginDtlsId;

	@Column(name="CHANGE_PWD")
	private String changePwd;
	
	@Column(name="ACTIVATION_CODE")
	private String activationCode;
	
	@Column(name="CMPNY_INFO_ID")
	private String cmpnyInfoId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	@Column(name="IS_EMAIL_VERIFIED")
	private String isEmailVerified;

	@Column(name="IS_PHONE_VERIFIED")
	private String isPhoneVerified;

	@Column(name="LOGIN_ID")
	private String loginId;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;

	private String password;

	@Column(name="RECEIVE_OFFEppRS")
	private String receiveOffers;

	private String status;

	@Column(name="TERMS_ACCEPTED")
	private String termsAccepted;

	@Column(name="TERMS_ACCEPTED_TS")
	private Timestamp termsAcceptedTs;

	@Column(name="TRACK_ID")
	private String trackId;

	@Column(name="USER_TYPE")
	private String userType;

	public UserLoginDtl() {
	}

	public String getUserLoginDtlsId() {
		return this.userLoginDtlsId;
	}

	public void setUserLoginDtlsId(String userLoginDtlsId) {
		this.userLoginDtlsId = userLoginDtlsId;
	}

	public String getChangePwd() {
		return this.changePwd;
	}

	public void setChangePwd(String changePwd) {
		this.changePwd = changePwd;
	}

	public String getCmpnyInfoId() {
		return this.cmpnyInfoId;
	}

	public void setCmpnyInfoId(String cmpnyInfoId) {
		this.cmpnyInfoId = cmpnyInfoId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public String getIsEmailVerified() {
		return this.isEmailVerified;
	}

	public void setIsEmailVerified(String isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public String getIsPhoneVerified() {
		return this.isPhoneVerified;
	}

	public void setIsPhoneVerified(String isPhoneVerified) {
		this.isPhoneVerified = isPhoneVerified;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedTs() {
		return this.modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReceiveOffers() {
		return this.receiveOffers;
	}

	public void setReceiveOffers(String receiveOffers) {
		this.receiveOffers = receiveOffers;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTermsAccepted() {
		return this.termsAccepted;
	}

	public void setTermsAccepted(String termsAccepted) {
		this.termsAccepted = termsAccepted;
	}

	public Timestamp getTermsAcceptedTs() {
		return this.termsAcceptedTs;
	}

	public void setTermsAcceptedTs(Timestamp termsAcceptedTs) {
		this.termsAcceptedTs = termsAcceptedTs;
	}

	public String getTrackId() {
		return this.trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}