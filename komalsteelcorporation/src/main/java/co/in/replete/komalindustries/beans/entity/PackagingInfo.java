package co.in.replete.komalindustries.beans.entity;

public class PackagingInfo {

	private int packagingInfoId;
	
	private String packagingInfo;
	
	private String packagingDescription;
	
	public PackagingInfo() {}

	public int getPackagingInfoId() {
		return packagingInfoId;
	}

	public void setPackagingInfoId(int packagingInfoId) {
		this.packagingInfoId = packagingInfoId;
	}

	public String getPackagingInfo() {
		return packagingInfo;
	}

	public void setPackagingInfo(String packagingInfo) {
		this.packagingInfo = packagingInfo;
	}

	public String getPackagingDescription() {
		return packagingDescription;
	}

	public void setPackagingDescription(String packagingDescription) {
		this.packagingDescription = packagingDescription;
	}
}
