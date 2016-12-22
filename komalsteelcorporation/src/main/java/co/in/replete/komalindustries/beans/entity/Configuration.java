package co.in.replete.komalindustries.beans.entity;

public class Configuration {

	private String configName;
	
	private String configVal;
	
	private String configDesc;
	
	private byte[] configImg;
	
	public Configuration() {}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigVal() {
		return configVal;
	}

	public void setConfigVal(String configVal) {
		this.configVal = configVal;
	}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	public byte[] getConfigImg() {
		return configImg;
	}

	public void setConfigImg(byte[] configImg) {
		this.configImg = configImg;
	}
	
}
