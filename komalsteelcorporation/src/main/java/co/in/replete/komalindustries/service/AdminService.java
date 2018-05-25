package co.in.replete.komalindustries.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import co.in.replete.komalindustries.beans.AppConfigurationWrapper;
import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.CompanyInfoRequestWrapper;
import co.in.replete.komalindustries.beans.UpdateCmpnyInfoWrapper;
import co.in.replete.komalindustries.beans.entity.CategoryMaster;
import co.in.replete.komalindustries.beans.entity.EnquiryDetails;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.LocationDtls;
import co.in.replete.komalindustries.exception.ServicesException;

public interface AdminService {

	BaseWrapper addCmpnyInfo(CompanyInfoRequestWrapper request) throws Exception;

	BaseWrapper updateCmpnyInfo(UpdateCmpnyInfoWrapper request) throws Exception;

	BaseWrapper getConfigurationValue(String configname) throws Exception;

	BaseWrapper updateConfigurationValue(AppConfigurationWrapper request,String configname) throws Exception;

	List<CategoryMaster> getSubCategoryOnCatId(String catId);

	List<LocationDtls> getCityListByStateName(String stateId);

	List<ItemMasterDtl> getProductsListBySubCategoryId(String subCatId);

	ItemMasterDtl getProductmaterCartonPrice(String productId);

	BaseWrapper getStateList();

	List<LocationDtls> getCityListByStateId(String stateId);

	BaseWrapper saveEnquiry(EnquiryDetails request) throws ServicesException;

}
