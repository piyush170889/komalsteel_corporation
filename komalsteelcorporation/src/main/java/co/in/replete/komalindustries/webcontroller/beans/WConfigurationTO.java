package co.in.replete.komalindustries.webcontroller.beans;

public class WConfigurationTO {

	private String configName;
	
	private String configValue;
	
	private String configDesc;
	
	public WConfigurationTO(){}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	
}
