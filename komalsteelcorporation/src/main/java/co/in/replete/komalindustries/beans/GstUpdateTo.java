package co.in.replete.komalindustries.beans;

public class GstUpdateTo extends BaseWrapper {

	private String trackId;
	
	private String gstNo;

	public GstUpdateTo() {}
	
	public GstUpdateTo(String trackId, String gstNo) {
		this.trackId = trackId;
		this.gstNo = gstNo;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

}
