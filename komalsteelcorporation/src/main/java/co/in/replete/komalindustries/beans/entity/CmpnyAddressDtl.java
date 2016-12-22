package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the cmpny_address_dtls database table.
 * 
 */
@Entity
@Table(name="cmpny_address_dtls")
@NamedQuery(name="CmpnyAddressDtl.findAll", query="SELECT c FROM CmpnyAddressDtl c")
public class CmpnyAddressDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7288527309124184273L;

	@Id
	@Column(name="ADDRESS_DTLS_ID")
	private int addressDtlsId;

	@Column(name="ADDRESS_TYPE")
	private String addressType;

	private String city;

	@Column(name="CMPNY_INFO_ID")
	private String cmpnyInfoId;

	private String country;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	private BigDecimal latitude;

	private BigDecimal longitude;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;

	@Column(name="POSTAL_CODE")
	private int postalCode;

	@Column(name="ST_ADDRESS_1")
	private String stAddress1;

	@Column(name="ST_ADDRESS_2")
	private String stAddress2;

	@Column(name="ST_ADDRESS_3")
	private String stAddress3;

	private String state;

	public CmpnyAddressDtl() {
	}

	public int getAddressDtlsId() {
		return this.addressDtlsId;
	}

	public void setAddressDtlsId(int addressDtlsId) {
		this.addressDtlsId = addressDtlsId;
	}

	public String getAddressType() {
		return this.addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCmpnyInfoId() {
		return this.cmpnyInfoId;
	}

	public void setCmpnyInfoId(String cmpnyInfoId) {
		this.cmpnyInfoId = cmpnyInfoId;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
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

	public int getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getStAddress1() {
		return this.stAddress1;
	}

	public void setStAddress1(String stAddress1) {
		this.stAddress1 = stAddress1;
	}

	public String getStAddress2() {
		return this.stAddress2;
	}

	public void setStAddress2(String stAddress2) {
		this.stAddress2 = stAddress2;
	}

	public String getStAddress3() {
		return this.stAddress3;
	}

	public void setStAddress3(String stAddress3) {
		this.stAddress3 = stAddress3;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}