package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cmpny_info database table.
 * 
 */
@Entity
@Table(name="cmpny_info")
@NamedQuery(name="CmpnyInfo.findAll", query="SELECT c FROM CmpnyInfo c")
public class CmpnyInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7838587558722809561L;

	@Id
	@Column(name="CMPNY_INFO_ID")
	private String cmpnyInfoId;

	@Column(name="CMPNY_ACC_NO")
	private double cmpnyAccNo;

	@Column(name="CMPNY_BANK")
	private String cmpnyBank;

	@Column(name="CMPNY_BANK_IFSC")
	private String cmpnyBankIfsc;

	@Column(name="CMPNY_CHECK_REF")
	private String cmpnyCheckRef;

	@Column(name="CMPNY_DESCRIPTION")
	private String cmpnyDescription;

	@Lob
	@Column(name="CMPNY_LOGO")
	private byte[] cmpnyLogo;

	@Column(name="CMPNY_NAME")
	private String cmpnyName;

	@Column(name="CMPNY_PAN_NO")
	private String cmpnyPanNo;

	@Column(name="CMPNY_SERVICE_TAX_NO")
	private String cmpnyServiceTaxNo;

	@Column(name="CMPNY_TITLE")
	private String cmpnyTitle;

	@Column(name="CMPNY_VAT_NO")
	private String cmpnyVatNo;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;
	
	@Column(name="CMPNY_REG")
	private String cmpnyReg;

	public String getCmpnyReg() {
		return cmpnyReg;
	}

	public void setCmpnyReg(String cmpnyReg) {
		this.cmpnyReg = cmpnyReg;
	}

	public CmpnyInfo() {
	}

	public String getCmpnyInfoId() {
		return this.cmpnyInfoId;
	}

	public void setCmpnyInfoId(String cmpnyInfoId) {
		this.cmpnyInfoId = cmpnyInfoId;
	}

	public double getCmpnyAccNo() {
		return this.cmpnyAccNo;
	}

	public void setCmpnyAccNo(double cmpnyAccNo) {
		this.cmpnyAccNo = cmpnyAccNo;
	}

	public String getCmpnyBank() {
		return this.cmpnyBank;
	}

	public void setCmpnyBank(String cmpnyBank) {
		this.cmpnyBank = cmpnyBank;
	}

	public String getCmpnyBankIfsc() {
		return this.cmpnyBankIfsc;
	}

	public void setCmpnyBankIfsc(String cmpnyBankIfsc) {
		this.cmpnyBankIfsc = cmpnyBankIfsc;
	}

	public String getCmpnyCheckRef() {
		return this.cmpnyCheckRef;
	}

	public void setCmpnyCheckRef(String cmpnyCheckRef) {
		this.cmpnyCheckRef = cmpnyCheckRef;
	}

	public String getCmpnyDescription() {
		return this.cmpnyDescription;
	}

	public void setCmpnyDescription(String cmpnyDescription) {
		this.cmpnyDescription = cmpnyDescription;
	}

	public byte[] getCmpnyLogo() {
		return this.cmpnyLogo;
	}

	public void setCmpnyLogo(byte[] cmpnyLogo) {
		this.cmpnyLogo = cmpnyLogo;
	}

	public String getCmpnyName() {
		return this.cmpnyName;
	}

	public void setCmpnyName(String cmpnyName) {
		this.cmpnyName = cmpnyName;
	}

	public String getCmpnyPanNo() {
		return this.cmpnyPanNo;
	}

	public void setCmpnyPanNo(String cmpnyPanNo) {
		this.cmpnyPanNo = cmpnyPanNo;
	}

	public String getCmpnyServiceTaxNo() {
		return this.cmpnyServiceTaxNo;
	}

	public void setCmpnyServiceTaxNo(String cmpnyServiceTaxNo) {
		this.cmpnyServiceTaxNo = cmpnyServiceTaxNo;
	}

	public String getCmpnyTitle() {
		return this.cmpnyTitle;
	}

	public void setCmpnyTitle(String cmpnyTitle) {
		this.cmpnyTitle = cmpnyTitle;
	}

	public String getCmpnyVatNo() {
		return this.cmpnyVatNo;
	}

	public void setCmpnyVatNo(String cmpnyVatNo) {
		this.cmpnyVatNo = cmpnyVatNo;
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

}