package co.in.replete.komalindustries.beans;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import co.in.replete.komalindustries.beans.entity.AppConfiguration;

public class AppConfigurationWrapper extends BaseWrapper {

	@JsonProperty("request")
	@Valid
	private AppConfiguration appConfiguration;
	
	public AppConfigurationWrapper(){}

	public AppConfiguration getAppConfiguration() {
		return appConfiguration;
	}

	public void setAppConfiguration(AppConfiguration appConfiguration) {
		this.appConfiguration = appConfiguration;
	}

}
