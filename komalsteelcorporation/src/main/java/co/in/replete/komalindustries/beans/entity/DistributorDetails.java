package co.in.replete.komalindustries.beans.entity;

public class DistributorDetails {

	private int distributorDetailsId;
	
	private String trackId;
	
	private String addressDtlsId;
	
	public DistributorDetails() {}

	public int getDistributorDetailsId() {
		return distributorDetailsId;
	}

	public void setDistributorDetailsId(int distributorDetailsId) {
		this.distributorDetailsId = distributorDetailsId;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getAddressDtlsId() {
		return addressDtlsId;
	}

	public void setAddressDtlsId(String addressDtlsId) {
		this.addressDtlsId = addressDtlsId;
	}
	
}
