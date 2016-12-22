package co.in.replete.komalindustries.beans;

import java.util.List;

import co.in.replete.komalindustries.beans.entity.LocationDtls;

public class CityDetailsByStateIdTO extends BaseWrapper {

	private List<LocationDtls> response;
	
	public CityDetailsByStateIdTO() {}

	public CityDetailsByStateIdTO(List<LocationDtls> cityList) {
		this.response = cityList;
	}

	public List<LocationDtls> getResponse() {
		return response;
	}

	public void setResponse(List<LocationDtls> response) {
		this.response = response;
	}
	
}
