package co.in.replete.komalindustries.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.in.replete.komalindustries.beans.AppConfigurationWrapper;
import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.CityDetailsByStateIdTO;
import co.in.replete.komalindustries.beans.CompanyInfoRequestWrapper;
import co.in.replete.komalindustries.beans.UpdateCmpnyInfoWrapper;
import co.in.replete.komalindustries.beans.entity.EnquiryDetails;
import co.in.replete.komalindustries.beans.entity.LocationDtls;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.service.AdminService;

@RestController
public class AdminController { 
	
	@Autowired
	AdminService adminService;
	
	
	/**
	 * Description :Add company information
	 * @param   {@link CompanyInfoRequestWrapper}
	 * @return  {@link BaseWrapper}
	 * @throws  {@link Exception}
	 */
	@RequestMapping(value="/companyinfo", method=RequestMethod.POST, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper addCompanyInfo(@Valid @RequestBody CompanyInfoRequestWrapper request) throws Exception{
		
		return adminService.addCmpnyInfo(request);
	}
	
	/**
	 * Description: update company information and addreess details
	 * @param {@link request}
	 * @return {@link BaseWrapper}
	 * @throws {@link Exception}
	 */
	@RequestMapping(value="/companyinfo",method=RequestMethod.PUT,consumes=KomalIndustriesConstants.APPLICATION_JSON, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper updateCmpnyDetails(@Valid @RequestBody UpdateCmpnyInfoWrapper request) throws Exception
	{
		return adminService.updateCmpnyInfo(request);
	}
	
	/**
	 * Description get configuration value
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/configuration/{configname}",method=RequestMethod.GET,produces=KomalIndustriesConstants.APPLICATION_JSON)
		public BaseWrapper getConfiguration(@PathVariable ("configname") String configname) throws Exception
		{
			return adminService.getConfigurationValue(configname);
		}
	
	/**
	 * Description : update configuration value
	 * @param configname
	 * @param request
	 * @return BaseWrapper
	 * @throws Exception
	 */
	@RequestMapping(value="/configuration/{configname}",method=RequestMethod.PUT,consumes=KomalIndustriesConstants.APPLICATION_JSON, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper updateConfiguration(@PathVariable ("configname") String configname,@Valid @RequestBody AppConfigurationWrapper request) throws Exception
	{
		return adminService.updateConfigurationValue(request,configname);
	}
	
	@RequestMapping(value="/getCityList", method=RequestMethod.GET)
	public BaseWrapper getCityListFromStateId(@RequestParam("stateId") String stateId) {
		List<LocationDtls> cityList = adminService.getCityListByStateId(stateId);
		return new CityDetailsByStateIdTO(cityList); 
	}
	
	@RequestMapping(value="/getStateList", method=RequestMethod.GET)
	public BaseWrapper getStateList() {
		return adminService.getStateList();
	}
	
	@RequestMapping(value="/enquiry", method=RequestMethod.POST)
	public BaseWrapper fillContactDetails(@RequestBody EnquiryDetails request) throws ServicesException {
		return adminService.saveEnquiry(request);
	}
	
}