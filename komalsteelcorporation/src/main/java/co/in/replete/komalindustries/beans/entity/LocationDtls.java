package co.in.replete.komalindustries.beans.entity;

public class LocationDtls {

	private int locationId;
	
	private String locationName;
	
	private String locationDesc;
	
	private int locationParentId;
	
	private String isActive;
	
	public LocationDtls() {}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public int getLocationParentId() {
		return locationParentId;
	}

	public void setLocationParentId(int locationParentId) {
		this.locationParentId = locationParentId;
	}
	
	
}
