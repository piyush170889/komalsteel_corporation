package co.in.replete.komalindustries.beans;

public class SmsDtlsWrapper {

	private int smsDtlsId;

	private int contactDtlsId;

	private String smsSendTime;

	private String smsContent;
	
	private String contactName;

	private String contactNumber;
	
	private String shopName;

	private String createdTs;

	private String modifiedTs;

	
	

	
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
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

	public int getSmsDtlsId() {
		return smsDtlsId;
	}

	public void setSmsDtlsId(int smsDtlsId) {
		this.smsDtlsId = smsDtlsId;
	}

	public int getContactDtlsId() {
		return contactDtlsId;
	}

	public void setContactDtlsId(int contactDtlsId) {
		this.contactDtlsId = contactDtlsId;
	}

	public String getSmsSendTime() {
		return smsSendTime;
	}

	public void setSmsSendTime(String smsSendTime) {
		this.smsSendTime = smsSendTime;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "SmsDtlsWrapper [smsDtlsId=" + smsDtlsId + ", contactDtlsId=" + contactDtlsId + ", smsSendTime="
				+ smsSendTime + ", smsContent=" + smsContent + ", contactName=" + contactName + ", contactNumber="
				+ contactNumber + "]";
	}

	
	

}
