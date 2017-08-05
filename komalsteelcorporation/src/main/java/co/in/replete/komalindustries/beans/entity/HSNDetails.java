package co.in.replete.komalindustries.beans.entity;

public class HSNDetails {

	private int hsnDtlsId;
	
	private int hsnNo;
	
	private Float iGst;
	
	private Float sGst;
	
	private Float cGst;
	
	private String isActive;
	
	private String createdTs;
	
	private String modifiedTs;
	
	public HSNDetails() {}

	public int getHsnDtlsId() {
		return hsnDtlsId;
	}

	public void setHsnDtlsId(int hsnDtlsId) {
		this.hsnDtlsId = hsnDtlsId;
	}

	public int getHsnNo() {
		return hsnNo;
	}

	public void setHsnNo(int hsnNo) {
		this.hsnNo = hsnNo;
	}

	public Float getiGst() {
		return iGst;
	}

	public void setiGst(Float iGst) {
		this.iGst = iGst;
	}

	public Float getsGst() {
		return sGst;
	}

	public void setsGst(Float sGst) {
		this.sGst = sGst;
	}

	public Float getcGst() {
		return cGst;
	}

	public void setcGst(Float cGst) {
		this.cGst = cGst;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}

	public String getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(String modifiedTs) {
		this.modifiedTs = modifiedTs;
	}
	
}
