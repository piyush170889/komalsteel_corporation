package co.in.replete.komalindustries.beans.entity;

public class CourierMasterDtls {

	private int courierMasterId;
	
	private String courierNm;
	
	private String trackingUrl;
	
	private int isActive;
	
	private String createdTs;
	
	private String modifiedTs;
	
	public CourierMasterDtls() {}

	public int getCourierMasterId() {
		return courierMasterId;
	}

	public void setCourierMasterId(int courierMasterId) {
		this.courierMasterId = courierMasterId;
	}

	public String getCourierNm() {
		return courierNm;
	}

	public void setCourierNm(String courierNm) {
		this.courierNm = courierNm;
	}

	public String getTrackingUrl() {
		return trackingUrl;
	}

	public void setTrackingUrl(String trackingUrl) {
		this.trackingUrl = trackingUrl;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
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
