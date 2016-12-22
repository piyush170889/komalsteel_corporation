package co.in.replete.komalindustries.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.entity.AdminLoginDtls;
import co.in.replete.komalindustries.beans.entity.AppConfiguration;
import co.in.replete.komalindustries.beans.entity.CategoryMaster;
import co.in.replete.komalindustries.beans.entity.EnquiryDetails;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.LocationDtls;
import co.in.replete.komalindustries.beans.entity.PackagingInfo;
import co.in.replete.komalindustries.webcontroller.beans.WConfigurationTO;

public interface AdminDAO {

	int selectCmpnyCount(String cmpnyRegNum)throws DataAccessException;

	String insertCompnyInfo(final String cmpnyName,final String cmpnyLogo,final String cmpnyDesc,final String cmpnyTitle,final double cmpnyAccNo,
			final String cmpnyBank,final String cmpnyCheckRef,final String cmpnyBankIfsc,final String cmpnyPanNO,final String cmpnyVatNO,
			final String cmpnyServiceTaxNo,final String cmpnyRegNum)throws DataAccessException;

	void insertCompanyAddress(String cmpnyInfoId, String addressDtl1, String addressDtl2, String addressDtl3,
			String addressType, String cmpnyCity, String cmpnyState, String cmpnyCountry, int cmpnyPostal,
			BigDecimal cmpnyLongitude, BigDecimal cmpnyLattitude) throws DataAccessException;

	int selectCmpnyInfoId(String cmpnyInfoId) throws Exception;

	void updateCompanyBasicInfo(String cmpnyName, String cmpnyRegNum, String cmpnyLogo, String cmpnyDesc,
			String cmpnyTitle, double cmpnyAccNo, String cmpnyCheckRef, String cmpnyBank, String cmpnyBankIfsc,
			String cmpnyVatNO, String cmpnyPanNO, String cmpnyServiceTaxNo,String cmpnyInfoId) throws Exception;

	void updateCmpnyAddress(String stAddress1, String stAddress2, String stAddress3, String addressType, String city,
			String state, String country, int postalCode,int addressDtlId, String cmpnyInfoId) throws Exception;

    AppConfiguration selectConfigurationValue(String configname) throws DataAccessException;

	void updateConfiguration(String configValue, String desc,String configname) throws DataAccessException;
	
    List<WConfigurationTO>  selectWConfigurationList();

	List<LocationDtls> selectCityList() throws DataAccessException;

	List<CategoryMaster> getCategoryList() throws DataAccessException;

	List<CategoryMaster> getSubCategoryList() throws DataAccessException;

	List<PackagingInfo> getPackingInfoList() throws DataAccessException;

	void addPackagingInfo(String packagingInfoDesc, String packagingInfoName);

	void editPackagingInfo(String packaginInfoId, String packagingInfoDesc, String packagingInfoName);

	List<CategoryMaster> selectProductSubCategoryByCatId(String catId);

	void insertConfigValues(String configName, String configVal, String configDesc);

	void updateConfigValues(String configName, String configVal, String configDesc);

	void insertCategoryDetails(String catName, String url, String catDesc, int parentId);

	void updateCategoryDetails(String catId, String catName, String url, String catDesc);

	void deleteCategoryDetails(String id);

	void updateSubCategoryDetails(String catId, String catName, String parentId, String catDesc, String url);

	List<LocationDtls> selectCityListByStateName(String stateId);

	List<ItemMasterDtl> selectProductDetailsBySubcategoryId(String subCatId);

	ItemMasterDtl selectProductMasterCartonPriceById(String productId);

	List<AdminLoginDtls> selectUserDetailsByEmailIdAndPass(String emailId, String password);

	List<LocationDtls> selectStateList();

	List<LocationDtls> selectCityListByStateId(String stateId);

	int insertEnquiryDetails(EnquiryDetails request);

	List<LocationDtls> selectAllStates();

	void wDeleteLocation(String locationId) throws Exception;

	void editWLocation(LocationDtls request) throws Exception;

	void addWLocation(LocationDtls request) throws Exception;;


}
