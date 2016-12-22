package co.in.replete.komalindustries.beans;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import co.in.replete.komalindustries.beans.entity.CmpnyAddressDtl;

public class CompanyUpdateDetailsTO {

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
	
	private  List <CmpnyAddressDtl> cmpnyAddressDtl;

	public CompanyUpdateDetailsTO(){}
	
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

	public String getCmpnyRegNum() {
		return cmpnyRegNum;
	}

	public void setCmpnyRegNum(String cmpnyRegNum) {
		this.cmpnyRegNum = cmpnyRegNum;
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

	public List<CmpnyAddressDtl> getCmpnyAddressDtl() {
		return cmpnyAddressDtl;
	}

	public void setCmpnyAddressDtl(List<CmpnyAddressDtl> cmpnyAddressDtl) {
		this.cmpnyAddressDtl = cmpnyAddressDtl;
	} 
	
	
	
	
}
