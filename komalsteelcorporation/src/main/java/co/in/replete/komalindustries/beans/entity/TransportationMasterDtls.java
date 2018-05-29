package co.in.replete.komalindustries.beans.entity;

public class TransportationMasterDtls {

	
	private int transportationMasterId;
	
	private String name;
	
	private String description;
	
	private int isActive;
	
	private String createdTs;
	
	private String modifiedTs;
	
	public TransportationMasterDtls(){}

	public int getTransportationMasterId() {
		return transportationMasterId;
	}

	public void setTransportationMasterId(int transportationMasterId) {
		this.transportationMasterId = transportationMasterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		return "TransportationMasterDtls [transportationMasterId=" + transportationMasterId + ", name=" + name
				+ ", description=" + description + ", isActive=" + isActive + ", createdTs=" + createdTs
				+ ", modifiedTs=" + modifiedTs + "]";
	}
	
	
	
}
