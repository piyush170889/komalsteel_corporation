package co.in.replete.komalindustries.webcontroller;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.OrderDetailsDAO;
import co.in.replete.komalindustries.exception.PrepareViewModelException;
import co.in.replete.komalindustries.service.EnquiryService;
import co.in.replete.komalindustries.utils.CommonUtility;
import co.in.replete.komalindustries.utils.MessageUtility;
import co.in.replete.komalindustries.utils.PrepareViewModelUtilty;

@Controller
public class EnquiryController extends KomalIndustriesConstants {
	@Autowired
	private EnquiryService enquiryService;

	@Autowired
	private PrepareViewModelUtilty prepareViewModelUtilty;
	
	@Autowired
	private CommonUtility commonUtility;
	
	@Autowired
	private MessageUtility messageUtility;
	
	@Autowired
	private Properties configProperties;

	@Autowired
	OrderDetailsDAO orderDetailsDAO;
	
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
	
	@RequestMapping(value="customer-messaging", method=RequestMethod.GET)
	public String showCustomerMessagingView(Model model) throws PrepareViewModelException {
		
		model = prepareViewModelUtilty.prepareViewModelMap(KomalIndustriesConstants.VIEW_URL_ORDER, model, null, null);
		
		return "masters/customer-messaging";
	}
	

	@RequestMapping(value="customer-messaging", method=RequestMethod.POST)
	public String sendCustomerSMS(HttpServletRequest servletRequest, Model model) throws Exception {
		
		String smsType = servletRequest.getParameter("smsType");
		String contactNo = servletRequest.getParameter("custContact");
		String orderNo = servletRequest.getParameter("orderNo");
		
		String messageLabel = KomalIndustriesConstants.SUCCESS_MSSG_LABEL;
		String message = " SMS Sent Successfully";
		
		switch (smsType) {
		case "NEW_ORDER":
			messageUtility.sendMessage(contactNo, String.format(configProperties.getProperty("sms.orderplaced.success"), orderNo));
			message = "New Order" + message;
			break;

		case "LR_SMS":
			String transporterNm = servletRequest.getParameter("transporterNm");
			String destination = servletRequest.getParameter("destination");
			String lrNo = servletRequest.getParameter("lrNo");
			String noOfCarton = servletRequest.getParameter("noOfCarton");
			String lrDate = servletRequest.getParameter("lrDate");
			
			String lrDispatchDetailsMssg = commonUtility.createLrMessage(orderNo, transporterNm, destination, lrNo, noOfCarton, lrDate);
			
			contactNo += "," + KomalIndustriesConstants.ADMIN_MOBILE_NO;
			
			messageUtility.sendMessage(contactNo, lrDispatchDetailsMssg);
			message = "LR Details" + message;;
			break;
			
		case "COURIER_SMS":
			String courierNm = servletRequest.getParameter("courierNm");
			String docateNo = servletRequest.getParameter("docateNo");
			String trackingUrl = orderDetailsDAO.selectTrackingUrlByCourierName(courierNm);
			String delvryDate = servletRequest.getParameter("delvryDate");
			
			String courierDispatchDetailsMssg = commonUtility.createCourierMessage(orderNo, courierNm, docateNo, trackingUrl, delvryDate);
			
			contactNo += "," + KomalIndustriesConstants.ADMIN_MOBILE_NO;
			
			messageUtility.sendMessage(contactNo, courierDispatchDetailsMssg);
			message = "Courier Details" +  message;;
			break;
			
		default:
			messageLabel = KomalIndustriesConstants.ERROR_MSSG_LABEL;
			message = "Invalid SMS Type Supplied";
		}
		
		model = prepareViewModelUtilty.prepareViewModelMap(KomalIndustriesConstants.VIEW_URL_ORDER, model, null, null);
		model.addAttribute(messageLabel, message);
		
		return "masters/customer-messaging";
	}
	
}
