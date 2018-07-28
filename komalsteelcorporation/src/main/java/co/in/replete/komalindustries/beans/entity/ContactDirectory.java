package co.in.replete.komalindustries.beans.entity;

public class ContactDirectory {
 private int contactDirectoryId;
 
 private int contactNumber;
 
 
 
 private String isActive;
 
 private String createdBy;
 
 private String createdTs;
 
 private String modifiedBy;
 
 private String modifiedTs;

public int getContactDirectoryId() {
	return contactDirectoryId;
}

public void setContactDirectoryId(int contactDirectoryId) {
	this.contactDirectoryId = contactDirectoryId;
}

public int getContactNumber() {
	return contactNumber;
}

public void setContactNumber(int contactNumber) {
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
 
}
