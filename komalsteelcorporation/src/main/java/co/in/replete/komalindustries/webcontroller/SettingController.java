package co.in.replete.komalindustries.webcontroller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.exception.PrepareViewModelException;
import co.in.replete.komalindustries.service.SettingService;
import co.in.replete.komalindustries.utils.CommonUtility;

@Controller
public class SettingController {

	@Autowired
	private SettingService settingService;

	

	@Autowired
	private CommonUtility commonUtility;
	/**
	 * Description: get Settings Page
	 * @param model
	 * @return
	 * @throws PrepareViewModelException 
	 */
	@RequestMapping(value="/setting",method=RequestMethod.GET)
	public String settingPageView(Model model) throws PrepareViewModelException
	{
		return "settings/setting_password";
	}

	@RequestMapping(value="/settings",method=RequestMethod.POST)
	public String settingPage(Model model,HttpServletRequest request) throws PrepareViewModelException
	{
		String password = request.getParameter("password");
		System.out.println("Password : "+request.getParameter("password"));
		String matchPassword = settingService.getSettingsPassword();
		System.out.println("Password 2: "+password);

		if( null == password || password.isEmpty()) {
			model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, "Required Fields Are empty");
			return "redirect:setting";
		} else {
			try {
				if(!password.equals(matchPassword)) {
					throw new ServiceException("Invalid credentials");
				} else {
					String adminEmailList = commonUtility.getAdminEmailIds();
					String contactNumbers = commonUtility.getAdminContactNumbers();
					model.addAttribute("contactNumbers", contactNumbers);
					model.addAttribute("adminEmailList", adminEmailList);
					model.addAttribute("matchPassword", matchPassword);
					return "settings/setting";
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
				return "redirect:setting";
			}
		}
	}



	@RequestMapping(value="/check-oldPassword",method=RequestMethod.POST)
	public String checkPassword(Model model,HttpServletRequest request) throws PrepareViewModelException
	{
		String matchPassword = settingService.getSettingsPassword();
		System.out.println("Password -->: "+request.getParameter("oldPassword1"));
		if(matchPassword.equals(request.getParameter("oldPassword1"))) {
			return "Matched";
		}else {
			return "Unmatched";
		}
	}


	@RequestMapping(value="/update-password",method=RequestMethod.POST)
	public String updatePassword(Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) throws PrepareViewModelException
	{
		String newPassword = request.getParameter("newPassword");
		settingService.updatePassword(newPassword);
		return"redirect:setting";
	}

	@RequestMapping(value="/update-email",method=RequestMethod.POST)
	public String updateEmail(Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) throws PrepareViewModelException
	{
		try {
			String email = request.getParameter("emailId");
			 settingService.updateAdminEmailIds(email);
			String adminEmailList = commonUtility.getAdminEmailIds();
			String contactNumbers = commonUtility.getAdminContactNumbers();
			model.addAttribute("contactNumbers", contactNumbers);
			model.addAttribute("adminEmailList", adminEmailList);
			model.addAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL,"Email Id Updated Successfully." );
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL,"Exception Occured while updating email id.");
		}
		return"settings/setting";
	}
	
	@RequestMapping(value="/update-contact",method=RequestMethod.POST)
	public String updateContact(Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) throws PrepareViewModelException
	{
		try {
			String contact = request.getParameter("contact");
			 settingService.updateContactNumbers(contact);
			String adminEmailList = commonUtility.getAdminEmailIds();
			String contactNumbers = commonUtility.getAdminContactNumbers();
			model.addAttribute("contactNumbers", contactNumbers);
			model.addAttribute("adminEmailList", adminEmailList);
			model.addAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL,"Contact Numbers Updated Successfully." );
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL,"Exception Occured while updating email id.");
		}
		return"settings/setting";
	}


}
