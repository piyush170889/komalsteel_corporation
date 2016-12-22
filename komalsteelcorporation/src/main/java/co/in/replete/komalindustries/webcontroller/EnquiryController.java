package co.in.replete.komalindustries.webcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.entity.EnquiryDetails;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.exception.PrepareViewModelException;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.service.EnquiryService;

@Controller
public class EnquiryController extends KomalIndustriesConstants {
	@Autowired
	private EnquiryService enquiryService;

	@RequestMapping(value = "/enquiry", method = RequestMethod.GET)
	public String getTest(ModelMap model) {
		try {
			model.addAttribute("enqlist", enquiryService.getEnquiryList());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, "Error Occured Preparing the view");
			return ERROR_PAGE_URL;
		}
		return "customer management/enquiry";
		
	}
	@RequestMapping(value = "getenquirybydate", method = RequestMethod.GET)
	public String getTest(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		try {
			String startDate=request.getParameter("startDate").toString();
			String  endDate=request.getParameter("endDate").toString();
			System.out.println("startdate"+startDate);
			System.out.println("enbdate"+endDate);
			model.addAttribute("enqlist", enquiryService.getEnquiryListByDate(startDate, endDate));
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(ERROR_MSSG_LABEL, "Error Occured Preparing the view");
			return ERROR_PAGE_URL;
		}
		return "customer management/enquiry";
		
	}
	
}
