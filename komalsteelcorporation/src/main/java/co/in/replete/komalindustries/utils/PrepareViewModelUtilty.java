package co.in.replete.komalindustries.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import co.in.replete.komalindustries.beans.AddItemsToCartTO;
import co.in.replete.komalindustries.beans.OrderEditTO;
import co.in.replete.komalindustries.beans.UserAddTO;
import co.in.replete.komalindustries.beans.entity.ContactDtls;
import co.in.replete.komalindustries.beans.entity.HSNDetails;
import co.in.replete.komalindustries.beans.entity.LocationDtls;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.AdminDAO;
import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.dao.WMasterDAO;
import co.in.replete.komalindustries.exception.PrepareViewModelException;
import co.in.replete.komalindustries.webcontroller.beans.EditCartItemDtlsTO;

@Component
public class PrepareViewModelUtilty extends KomalIndustriesConstants {

	@Autowired
	Properties responseMessageProperties;
	
	@Autowired
	AdminDAO adminDAO;
	
	@Autowired
	UserManagementDAO userDAO;
	
	@Autowired
	private WMasterDAO wMasterDAO;
	
	
	/**
	 * Description :
	 * 
	 * @param {@link ModelMap}
	 * @return {@link ModelMap}
	 */
	public Model prepareViewModelMap(String viewName, Model model, String messageLabel, Object message) throws PrepareViewModelException {
		try {
				
				List<ContactDtls> contactDtlsList = wMasterDAO.selectActiveContactDetails();
				String  appendedContactDtlsList = "[";
				int j=0;
				for (ContactDtls contactDtls : contactDtlsList) {
					String  appendedContactDtls= contactDtls.getContactNumber()+"-"+contactDtls.getContactName()+"-"+contactDtls.getShopName();
					if (j==0) {
						appendedContactDtlsList+="\"" + appendedContactDtls + "\"";
					} else {
						appendedContactDtlsList+=",\"" + appendedContactDtls + "\"";
					}
					++j;
//					appendedContactDtlsList += appendedContactDtls;
//					appendedContactDtlsList.add(appendedContactNumbers);
				}
				appendedContactDtlsList += "]";
				System.out.println("appendedContactDtlsList : "+appendedContactDtlsList);
				model.addAttribute("appendedContactDtlsList", appendedContactDtlsList);
			switch(viewName) {
			case VIEW_URL_ORDER :
				List<String> paymodeList = new ArrayList<>();
				List<LocationDtls> cityList = new ArrayList<>();
				List<String> paymentStatusList = new ArrayList<>();
				List<String> orderStatusList = new ArrayList<>();
				
				//Add payment status info
				paymodeList.add("COD");
				paymodeList.add("Online");
				model.addAttribute("paymodeList", paymodeList);
				
				//Add payment Status
				paymentStatusList.add(UDValues.CART_STATUS_PENDING.toString());
				paymentStatusList.add(UDValues.CART_STATUS_COMPLETED.toString());
				model.addAttribute("paymentStatusList", paymentStatusList);
				
				//Add city List
				cityList = adminDAO.selectCityList();
				model.addAttribute("cityList", cityList);
				
				//Add order Status List
				orderStatusList.add(UDValues.CART_STATUS_PENDING.toString());
				orderStatusList.add(UDValues.CART_STATUS_PACKED.toString());
				orderStatusList.add(UDValues.CART_STATUS_DISPATCHED.toString());
				model.addAttribute("orderStatusList", orderStatusList);
				
				model.addAttribute("orderEdit", new OrderEditTO());
				model.addAttribute("courierList", wMasterDAO.selectActiveCourierDetailsList());
				
				//Add State List
				model.addAttribute("stateList", userDAO.getStateList());
				
				List<String> transportationNameList = wMasterDAO.selectActiveTransportationNamesList();
//				String transportationName = "[\"First\",\"Second\"]";
				
				String transportationName = "[";
				
				int i=0;
				for (String trasnporterName : transportationNameList) {
					if (i==0) {
						transportationName+="\"" + trasnporterName + "\"";
					} else {
						transportationName+=",\"" + trasnporterName + "\"";
					}
					++i;
				}
				
				transportationName += "]";
				
//				System.out.println("transportationName - " + transportationName);
				model.addAttribute("transportationName", transportationName);
				
				break;
				
			case VIEW_URL_USER_DTLS :
				model.addAttribute("userAdd", new UserAddTO());
				model.addAttribute("userEdit", new UserAddTO());
				model.addAttribute("stateList", userDAO.getStateList());
				model.addAttribute("DistributorList", userDAO.selectDistributorDetailsList());
				break;
				
			case VIEW_URL_ORDER_DTLS :
				model.addAttribute("categoryList", adminDAO.getCategoryList());
				model.addAttribute("addCartItem", new AddItemsToCartTO());
				model.addAttribute("editCartItemDetails", new EditCartItemDtlsTO());
				break;
			
			case VIEW_URL_LOCATION :
				List<LocationDtls> locationDetailsList = adminDAO.selectAllStates();
				model.addAttribute("locationAdd", new LocationDtls());
				model.addAttribute("locationEdit", new LocationDtls());
				model.addAttribute("statesList", locationDetailsList);
				model.addAttribute("pagenum", 1);
				break;
				
			case VIEW_URL_HSN:
				List<HSNDetails> hsnDetailsList = wMasterDAO.selectAllHsnDetails();
				model.addAttribute("hsnDetailsList", hsnDetailsList);
				model.addAttribute("hsnAdd", new HSNDetails());
				model.addAttribute("hsnEdit", new HSNDetails());
				break;
				
 			default :
				throw new Exception(responseMessageProperties.getProperty("error.invalid.input"));
			}
			
			if(null != messageLabel && null != message) {
				model.addAttribute(messageLabel, message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PrepareViewModelException(responseMessageProperties.getProperty("error.dataaccess"));
		}
			return model;
	}
}
