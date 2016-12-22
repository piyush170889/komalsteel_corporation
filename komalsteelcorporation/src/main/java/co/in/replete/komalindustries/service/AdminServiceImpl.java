package co.in.replete.komalindustries.service;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.in.replete.komalindustries.beans.AppConfigurationWrapper;
import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.CityDetailsByStateIdTO;
import co.in.replete.komalindustries.beans.CompanyInfoRequestWrapper;
import co.in.replete.komalindustries.beans.UpdateCmpnyInfoWrapper;
import co.in.replete.komalindustries.beans.entity.AppConfiguration;
import co.in.replete.komalindustries.beans.entity.CategoryMaster;
import co.in.replete.komalindustries.beans.entity.CmpnyAddressDtl;
import co.in.replete.komalindustries.beans.entity.EnquiryDetails;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.LocationDtls;
import co.in.replete.komalindustries.dao.AdminDAO;
import co.in.replete.komalindustries.exception.ServicesException;

@Service
@Transactional(rollbackFor=Throwable.class)
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminDAO adminDAO;
	
	@Autowired
	Properties responseMessageProperties;
	

	/**
	 * Description : Add company info and company address info
	 * @param  pageNum
	 * @param  companyInfoId
	 * @return  {@link BaseWrapper}
	 * @throws  {@link Exception}
	 */
	
	@Override
	public BaseWrapper addCmpnyInfo(CompanyInfoRequestWrapper request) throws Exception 
	{
	  try
	  {   
		  List<CmpnyAddressDtl> cmpnyAddressDtl=request.getCompanyDetailsTO().getCmpnyAddressDtl();
		  
		  // check if comapny info is  already added
		  int isCmpnyInfoadded=adminDAO.selectCmpnyCount(request.getCompanyDetailsTO().getCmpnyRegNum()); 
		  if(isCmpnyInfoadded !=0)
		  {
			  throw new Exception(responseMessageProperties.getProperty("error.cmpnyregistration.alreadyexist"));
		  }
		  else
		  {
			//insert company information
			String insertedCmpnyInfoId= adminDAO.insertCompnyInfo(request.getCompanyDetailsTO().getCmpnyName(), request.getCompanyDetailsTO().getCmpnyLogo(), request.getCompanyDetailsTO().getCmpnyDesc(), request.getCompanyDetailsTO().getCmpnyTitle(), 
					  request.getCompanyDetailsTO().getCmpnyAccNo(), request.getCompanyDetailsTO().getCmpnyBank(), request.getCompanyDetailsTO().getCmpnyCheckRef(), request.getCompanyDetailsTO().getCmpnyBankIfsc(), 
					  request.getCompanyDetailsTO().getCmpnyPanNO(), request.getCompanyDetailsTO().getCmpnyVatNO(), request.getCompanyDetailsTO().getCmpnyServiceTaxNo(),request.getCompanyDetailsTO().getCmpnyRegNum());
			  
			for(CmpnyAddressDtl cmpnyaddress:cmpnyAddressDtl)
			{
				//insert company address
		   adminDAO.insertCompanyAddress(insertedCmpnyInfoId, cmpnyaddress.getStAddress1(), cmpnyaddress.getStAddress2(), cmpnyaddress.getStAddress3(),
					  cmpnyaddress.getAddressType(), cmpnyaddress.getCity(), cmpnyaddress.getState(), cmpnyaddress.getCountry(),
					  cmpnyaddress.getPostalCode(), cmpnyaddress.getLongitude(),cmpnyaddress.getLatitude());
		  }
		  }
		 return  request;
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
		  throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
	  }
		
  }

	/**
	 * Description : update company information
	 * @param {@link UpdateCmpnyInfoWrapper}
	 * @return{@link BaseWrapper}
	 * @throws {@link Exception}
	 */
	@Override
	public BaseWrapper updateCmpnyInfo(UpdateCmpnyInfoWrapper request) throws Exception 
	{
		try{
			List<CmpnyAddressDtl> cmpnyAddressDtl=request.getComoanyUpdateDetailsTO().getCmpnyAddressDtl();
		
		    String	cmpnyInfoId=request.getComoanyUpdateDetailsTO().getCmpnyInfoId();
			// check if company is not exist
			  int isCmpnyExist=adminDAO.selectCmpnyInfoId(cmpnyInfoId); 
			  if(isCmpnyExist !=1)
			  {
				  throw new Exception(responseMessageProperties.getProperty("error.cmpnyinfoid.notexist"));
			  }
			  else
			  {
				    adminDAO.updateCompanyBasicInfo(request.getComoanyUpdateDetailsTO().getCmpnyName(), request.getComoanyUpdateDetailsTO().getCmpnyRegNum(), request.getComoanyUpdateDetailsTO().getCmpnyLogo(), request.getComoanyUpdateDetailsTO().getCmpnyDesc(),
				    		request.getComoanyUpdateDetailsTO().getCmpnyTitle(), request.getComoanyUpdateDetailsTO().getCmpnyAccNo(), request.getComoanyUpdateDetailsTO().getCmpnyCheckRef(), request.getComoanyUpdateDetailsTO().getCmpnyBank(),
				    		request.getComoanyUpdateDetailsTO().getCmpnyBankIfsc(), request.getComoanyUpdateDetailsTO().getCmpnyVatNO(), request.getComoanyUpdateDetailsTO().getCmpnyPanNO(), request.getComoanyUpdateDetailsTO().getCmpnyServiceTaxNo(), cmpnyInfoId);
				    		
				  for(CmpnyAddressDtl cmpnylist:cmpnyAddressDtl)
					{ 
				      adminDAO.updateCmpnyAddress(cmpnylist.getStAddress1(),cmpnylist.getStAddress2(),cmpnylist.getStAddress3(),cmpnylist.getAddressType(),
				    		cmpnylist.getCity(),cmpnylist.getState(),cmpnylist.getCountry(),cmpnylist.getPostalCode(),cmpnylist.getAddressDtlsId(),cmpnyInfoId);
				    }
					return request;

			  }

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		}

	}

	/**
	 * Description: get configuration value
	 * @param{@link configname}
	 * @return{@link BaseWrapper}
	 * @throws{@link Exception}
	 * 
	 */
	@Override
	public BaseWrapper getConfigurationValue(String configname) throws Exception 
	{
       try
       {
		 AppConfiguration appConfiguration=adminDAO.selectConfigurationValue(configname);
		 AppConfigurationWrapper response=new AppConfigurationWrapper();
		 response.setAppConfiguration(appConfiguration);
		 return response;
	  }
      catch(DataAccessException e) {
    	  e.printStackTrace();
    	  throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
       }
  }

	/**
	 * Description update configuration value
	 * @param{@link AppConfigurationWrapper configname }
	 * @return{@link BaseWrapper}
	 * @throws{@link Exception}
	 */
	@Override
	public BaseWrapper updateConfigurationValue(AppConfigurationWrapper request,String configname) throws Exception 
	{      try
      {
		 String configValue=request.getAppConfiguration().getConfigVal();
		 String desc=request.getAppConfiguration().getConfigDesc();
			 //update service Tax
			 adminDAO.updateConfiguration(configValue,desc,configname);
		return request;
     }
     catch(Exception e)
      {
    	 e.printStackTrace();
    	 throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
      }
  }
	
	@Override
	public List<CategoryMaster> getSubCategoryOnCatId(String catId) {
	
		return adminDAO.selectProductSubCategoryByCatId(catId);
	}
	
	@Override
	public List<LocationDtls> getCityListByStateName(String stateId) {
		
		return adminDAO.selectCityListByStateName(stateId);
	}

	@Override
	public List<ItemMasterDtl> getProductsListBySubCategoryId(String subCatId) {
		
		return adminDAO.selectProductDetailsBySubcategoryId(subCatId);
	}
	
	@Override
	public ItemMasterDtl getProductmaterCartonPrice(String productId) {
		return adminDAO.selectProductMasterCartonPriceById(productId);
	}
	
	@Override
	public List<LocationDtls> getCityListByStateId(String stateId) {
		return adminDAO.selectCityListByStateId(stateId);
	}
	
	@Override
	public BaseWrapper getStateList() {
		List<LocationDtls> stateList = adminDAO.selectStateList();
		return new CityDetailsByStateIdTO(stateList);
	}
	
	@Override
	public BaseWrapper saveEnquiry(EnquiryDetails request) throws ServicesException {
		
		int rowAffected = adminDAO.insertEnquiryDetails(request);
		if(rowAffected != 1) {
			throw new ServicesException("Error Saving enquiry");
		} else {
			return new BaseWrapper();
		}
	}
	
}
