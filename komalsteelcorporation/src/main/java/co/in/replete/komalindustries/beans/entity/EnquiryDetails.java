package co.in.replete.komalindustries.beans.entity;

public class EnquiryDetails {

	private int enquiryDtlsId;
	
	private String firstNm;
	
	private String lastNm;
	
	private String email;
	
	private String phnNm;
	
	private int stateId;
	
	private int cityId;
	
	private String enquiryType;
	
	private String message;
	
	public EnquiryDetails() {}

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

	public String getPhnNm() {
		return phnNm;
	}

	public void setPhnNm(String phnNm) {
		this.phnNm = phnNm;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEnquiryType() {
		return enquiryType;
	}

	public void setEnquiryType(String enquiryType) {
		this.enquiryType = enquiryType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
}
