package co.in.replete.komalindustries.beans;

import java.sql.Timestamp;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import co.in.replete.komalindustries.beans.entity.CmpnyAddressDtl;

public class CompanyDetailsTO {

	private String cmpnyInfoId;

	@NotNull(message="error.cmpnyName.required")
	@NotEmpty(message="error.cmpnyName.required")
	private String cmpnyName;
    
	@NotNull(message="error.cmpnyRegNum.required")
	@NotEmpty(message="error.cmpnyRegNum.required")
	private String cmpnyRegNum;

	private String cmpnyLogo;

	@NotNull(message="error.cmpnyDesc.required")
	@NotEmpty(message="error.cmpnyDesc.required")
	@Size(min=10, max=300)
	private String cmpnyDesc;

	@NotNull(message="error.cmpnyTitle.required")
	@NotEmpty(message="error.cmpnyTitle.required")
	private String cmpnyTitle;

	@NotNull(message="error.cmpnyAccNo.required")
	private double cmpnyAccNo;

	@NotNull(message="error.cmpnyBank.required")
	@NotEmpty(message="error.cmpnyBank.required")
	private String cmpnyBank;

	@NotNull(message="error.cmpnyCheckRef.required")
	@NotEmpty(message="error.cmpnyCheckRef.required")
	private String cmpnyCheckRef;

	@NotNull(message="error.cmpnyBankIfsc.required")
	@NotEmpty(message="error.cmpnyBankIfsc.required")
	private String cmpnyBankIfsc;

	private String modifiedBy;

	private Timestamp modifiedTs;

	@NotNull(message="error.cmpnyVatNO.required")
	@NotEmpty(message="error.cmpnyVatNO.required")
	private String cmpnyVatNO;
             
	@NotNull(message="error.cmpnyPanNO.required")
	@NotEmpty(message="error.cmpnyPanNO.required")
	private String cmpnyPanNO;

	@NotNull(message="error.cmpnyServiceTaxNo.required")
	@NotEmpty(message="error.cmpnyServiceTaxNo.required")
	private String cmpnyServiceTaxNo;
	 
	private int cmpnyAddressDtlId;
	
	@NotNull(message="error.addressDtl1.required")
	@NotEmpty(message="error.addressDtl1.required")
	private String addressDtl1;
	
	private String addressDtl2;
	
	private String addressDtl3;
	
	@NotNull(message="error.addressType.required")
	@NotEmpty(message="error.addressType.required")
	private String addressType;
	
	@NotNull(message="error.cmpnyCity.required")
	@NotEmpty(message="error.cmpnyCity.required")
	private String cmpnyCity;
	
	@NotNull(message="error.cmpnyState.required")
	@NotEmpty(message="error.cmpnyState.required")
	private String cmpnyState;
	
	@NotNull(message="error.cmpnyCountry.required")
	@NotEmpty(message="error.cmpnyCountry.required")
	private String cmpnyCountry;
	
	@NotNull(message="error.cmpnyPostal.required")
	@NotEmpty(message="error.cmpnyPostal.required")
	private int cmpnyPostal;
	
	private double cmpnyLattitude;
	
	private double cmpnyLongitude;
	
	private  List <CmpnyAddressDtl> cmpnyAddressDtl; 
	
	private List<SingleValueCommonClass> addressList;
	 

	public List<SingleValueCommonClass> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<SingleValueCommonClass> addressList) {
		this.addressList = addressList;
	}

	public List<CmpnyAddressDtl> getCmpnyAddressDtl() {
		return cmpnyAddressDtl;
		 	
	}

	public void setCmpnyAddressDtl(List<CmpnyAddressDtl> cmpnyAddressDtl) {
		this.cmpnyAddressDtl = cmpnyAddressDtl;
	}

	public String getCmpnyInfoId() {
		return cmpnyInfoId;
	}

	public void setCmpnyInfoId(String cmpnyInfoId) {
		this.cmpnyInfoId = cmpnyInfoId;
	}

	public String getCmpnyName() {
		return cmpnyName;
	}

	public void setCmpnyName(String cmpnyName) {
		this.cmpnyName = cmpnyName;
	}

	public String getCmpnyLogo() {
		return cmpnyLogo;
	}

	public void setCmpnyLogo(String cmpnyLogo) {
		this.cmpnyLogo = cmpnyLogo;
	}

	public String getCmpnyDesc() {
		return cmpnyDesc;
	}

	public void setCmpnyDesc(String cmpnyDesc) {
		this.cmpnyDesc = cmpnyDesc;
	}

	public String getCmpnyTitle() {
		return cmpnyTitle;
	}

	public void setCmpnyTitle(String cmpnyTitle) {
		this.cmpnyTitle = cmpnyTitle;
	}

	public double getCmpnyAccNo() {
		return cmpnyAccNo;
	}

	public void setCmpnyAccNo(double cmpnyAccNo) {
		this.cmpnyAccNo = cmpnyAccNo;
	}

	public String getCmpnyBank() {
		return cmpnyBank;
	}

	public void setCmpnyBank(String cmpnyBank) {
		this.cmpnyBank = cmpnyBank;
	}

	public String getCmpnyCheckRef() {
		return cmpnyCheckRef;
	}

	public void setCmpnyCheckRef(String cmpnyCheckRef) {
		this.cmpnyCheckRef = cmpnyCheckRef;
	}

	public String getCmpnyBankIfsc() {
		return cmpnyBankIfsc;
	}

	public void setCmpnyBankIfsc(String cmpnyBankIfsc) {
		this.cmpnyBankIfsc = cmpnyBankIfsc;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public String getCmpnyVatNO() {
		return cmpnyVatNO;
	}

	public void setCmpnyVatNO(String cmpnyVatNO) {
		this.cmpnyVatNO = cmpnyVatNO;
	}

	public String getCmpnyPanNO() {
		return cmpnyPanNO;
	}

	public void setCmpnyPanNO(String cmpnyPanNO) {
		this.cmpnyPanNO = cmpnyPanNO;
	}

	public String getCmpnyServiceTaxNo() {
		return cmpnyServiceTaxNo;
	}

	public void setCmpnyServiceTaxNo(String cmpnyServiceTaxNo) {
		this.cmpnyServiceTaxNo = cmpnyServiceTaxNo;
	}

	public int getCmpnyAddressDtlId() {
		return cmpnyAddressDtlId;
	}

	public void setCmpnyAddressDtlId(int cmpnyAddressDtlId) {
		this.cmpnyAddressDtlId = cmpnyAddressDtlId;
	}

	public String getAddressDtl1() {
		return addressDtl1;
	}

	public void setAddressDtl1(String addressDtl1) {
		this.addressDtl1 = addressDtl1;
	}

	public String getAddressDtl2() {
		return addressDtl2;
	}

	public void setAddressDtl2(String addressDtl2) {
		this.addressDtl2 = addressDtl2;
	}

	public String getAddressDtl3() {
		return addressDtl3;
	}

	public void setAddressDtl3(String addressDtl3) {
		this.addressDtl3 = addressDtl3;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getCmpnyCity() {
		return cmpnyCity;
	}

	public void setCmpnyCity(String cmpnyCity) {
		this.cmpnyCity = cmpnyCity;
	}

	public String getCmpnyState() {
		return cmpnyState;
	}

	public void setCmpnyState(String cmpnyState) {
		this.cmpnyState = cmpnyState;
	}

	public String getCmpnyCountry() {
		return cmpnyCountry;
	}

	public void setCmpnyCountry(String cmpnyCountry) {
		this.cmpnyCountry = cmpnyCountry;
	}

	public int getCmpnyPostal() {
		return cmpnyPostal;
	}

	public void setCmpnyPostal(int cmpnyPostal) {
		this.cmpnyPostal = cmpnyPostal;
	}

	public double getCmpnyLattitude() {
		return cmpnyLattitude;
	}

	public void setCmpnyLattitude(double cmpnyLattitude) {
		this.cmpnyLattitude = cmpnyLattitude;
	}

	public double getCmpnyLongitude() {
		return cmpnyLongitude;
	}

	public void setCmpnyLongitude(double cmpnyLongitude) {
		this.cmpnyLongitude = cmpnyLongitude;
	}
	
	public String getCmpnyRegNum() {
		return cmpnyRegNum;
	}

	public void setCmpnyRegNum(String cmpnyRegNum) {
		this.cmpnyRegNum = cmpnyRegNum;
	}

	
	
	
		
}
