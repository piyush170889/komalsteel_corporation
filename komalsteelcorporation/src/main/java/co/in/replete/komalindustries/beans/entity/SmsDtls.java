package co.in.replete.komalindustries.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sms_dtls")
public class SmsDtls {
	@Id
	@Column(name="SMS_HISTORY_ID")
     private int smsDtlsId;
	
	@Column(name="CONTACT_NUMBER")
private String contactNumber;
	
	@Column(name="CONTACT_NAME")
private String contactName;
	
	@Column(name="SMS_SEND_TIME")
private String smsSendTime;
	
	
	
	@Column(name="SMS_CONTENT")
private String smsContent;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="CREATED_TS")
	private String createdTs;
	
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name="MODIFIED_TS")
	private String modifiedTs;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
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

	@Override
	public String toString() {
		return "SmsDtls [smsDtlsId=" + smsDtlsId + ", contactNumber=" + contactNumber + ", contactName=" + contactName
				+ ", smsSendTime=" + smsSendTime + ", smsContent=" + smsContent + "]";
	}
	

	
}
