package co.in.replete.komalindustries.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contact_dtls")
public class ContactDtls {
	public static Class<ContactDtls> String;

	@Id
	@Column(name="CONTACT_DTLS_ID")
	private int contactDtlsId;

	@Column(name="CONTACT_NAME")
	private String contactName;

	@Column(name="CONTACT_NUMBER")
	private String contactNumber;

	@Column(name="SHOP_NAME")
	private String shopName;

	
	@Column(name="IS_ACTIVE")
	private String isActive;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_TS")
	private String createdTs;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_TS")
	private String modifiedTs;


	public int getContactDtlsId() {
		return contactDtlsId;
	}
	public void setContactDtlsId(int contactDtlsId) {
		this.contactDtlsId = contactDtlsId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
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
	@Override
	public String toString() {
		return "ContactDtls [contactDtlsId=" + contactDtlsId + ", contactName=" + contactName + ", contactNumber="
				+ contactNumber + ", isActive=" + isActive + ", createdBy=" + createdBy + ", createdTs=" + createdTs
				+ ", modifiedBy=" + modifiedBy + ", modifiedTs=" + modifiedTs + "]";
	}


}
