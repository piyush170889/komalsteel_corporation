package co.in.replete.komalindustries.beans;

public class EnquiryDetailsTo {

	private int enquiryDtlsId;
	
	private String firstNm;
	
	private String lastNm;
	
	private String emailId;
	
	private String phnNm;
	
	private String state;
	
	private String city;
	
	private String enquiryType;
	
	private String message;
	
	private String createdTs;
	
	private String pinNo;
	
	private String companyName;
	
	public EnquiryDetailsTo() {}

	public String getPinNo() {
		return pinNo;
	}

	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}

	public int getEnquiryDtlsId() {
		return enquiryDtlsId;
	}

	public void setEnquiryDtlsId(int enquiryDtlsId) {
		this.enquiryDtlsId = enquiryDtlsId;
	}

	public String getFirstNm() {
		return firstNm;
	}

	public void setFirstNm(String firstNm) {
		this.firstNm = firstNm;
	}

	public String getLastNm() {
		return lastNm;
	}

	public void setLastNm(String lastNm) {
		this.lastNm = lastNm;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhnNm() {
		return phnNm;
	}

	public void setPhnNm(String phnNm) {
		this.phnNm = phnNm;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEnquiryType() {
		return enquiryType;
	}

	public void setEnquiryType(String enquiryType) {
		this.enquiryType = enquiryType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
