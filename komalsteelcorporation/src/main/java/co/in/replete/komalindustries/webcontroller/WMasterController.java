package co.in.replete.komalindustries.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.in.replete.komalindustries.beans.entity.HSNDetails;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.service.WMasterService;
import co.in.replete.komalindustries.utils.PrepareViewModelUtilty;

@Controller
public class WMasterController {

	@Autowired
	private WMasterService wMasterService;
	
	@Autowired
	private PrepareViewModelUtilty prepareViewModelUtilty;
	
	@RequestMapping(value="hsnDetails", method=RequestMethod.GET)
	public String doShowHSNView(Model model) {
		try {
			model = prepareViewModelUtilty.prepareViewModelMap(KomalIndustriesConstants.VIEW_URL_HSN, model, null, null);
		}  catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
			return KomalIndustriesConstants.ERROR_PAGE_URL;
		}
		
		return KomalIndustriesConstants.VIEW_URL_HSN;
	}
	
	@RequestMapping(value="hsnDetails", method=RequestMethod.POST)
	public String doAddHSNView(@ModelAttribute("hsnAdd") HSNDetails hsnDetails, Model model, RedirectAttributes redAttr) {
		try {
			int rowAffected = wMasterService.doAddHsnDetails(hsnDetails);
			if (rowAffected!=1) {
				throw new ServicesException("Something went wrong while adding HSN");
			}
			redAttr.addAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, "Successfully Added HSN No: " + hsnDetails.getHsnNo());
		}  catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
			return KomalIndustriesConstants.ERROR_PAGE_URL;
		}
		
		return "redirect:hsnDetails";
	}
	
	
	@RequestMapping(value="hsnDetailsEdit", method=RequestMethod.POST)
	public String doUpdateHSN(@ModelAttribute("hsnEdit") HSNDetails hsnDetails, Model model, RedirectAttributes redAttr) {
		try {
			int rowAffected = wMasterService.doUpdateHSN(hsnDetails);
			if (rowAffected!=1) {
				throw new ServicesException("Something went wrong while updating HSN");
			}
			redAttr.addAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, "Successfully Update HSN No: " + hsnDetails.getHsnNo());
		}  catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
			return KomalIndustriesConstants.ERROR_PAGE_URL;
		}
		
		return "redirect:hsnDetails";
	}
	
	
	@RequestMapping(value="activateinactivatehsn", method=RequestMethod.GET)
	public String doActivateDeactivateHSN(@RequestParam(value="hsnId", required=true) int hsnId, 
			@RequestParam(value="status", required=true) String status, Model model, RedirectAttributes redAttr) {
		try {
			int rowAffected = wMasterService.doActivateDeactivateHSN(hsnId, status);
			if (rowAffected!=1) {
				throw new ServicesException("Something went wrong while updating HSN status");
			}
			redAttr.addAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, "Successfully Updated Status of HSN");
		}  catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
			return KomalIndustriesConstants.ERROR_PAGE_URL;
		}
		
		return "redirect:hsnDetails";
	}
}
