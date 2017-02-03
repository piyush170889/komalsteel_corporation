package co.in.replete.komalindustries.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.in.replete.komalindustries.beans.entity.AdminLoginDtls;
import co.in.replete.komalindustries.beans.entity.AppConfiguration;
import co.in.replete.komalindustries.beans.entity.CategoryMaster;
import co.in.replete.komalindustries.beans.entity.EnquiryDetails;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.LocationDtls;
import co.in.replete.komalindustries.beans.entity.PackagingInfo;
import co.in.replete.komalindustries.webcontroller.beans.WConfigurationTO;

@Repository
public class AdminDAOImpl  extends BaseDAOImpl implements AdminDAO {

	@Autowired 
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Properties sqlProperties;
	
	/**
	 * Description: select count for company information existance
	 * @param {@link cmpnyRegNum}
	 *@return {@link company count}
	 *@throws {@link DataAccessException} 
	 */
	@Override
	public int selectCmpnyCount(String cmpnyRegNum) throws DataAccessException 
	{
      return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.count.cmpnyinfo.regnum"),Integer.class, cmpnyRegNum);        		
	}
   
	/**
	 * Description: insert the company information to CMPNY_INFO Table
	 * @param {@link  cmpnyName,  cmpnyLogo,  cmpnyDesc,  cmpnyTitle,
			 cmpnyAccNo,  cmpnyBank,  cmpnyCheckRef,  cmpnyBankIfsc,  cmpnyPanNO,
			 cmpnyVatNO,  cmpnyServiceTaxNo,  cmpnyRegNum}
	 * @return 
     * @throws {@link DataAccessException}
	 */
	@Override
	public String insertCompnyInfo(final String cmpnyName,final String cmpnyLogo,final String cmpnyDesc,final String cmpnyTitle,
			final double cmpnyAccNo,final String cmpnyBank,final String cmpnyCheckRef,final String cmpnyBankIfsc,final String cmpnyPanNO,
			final String cmpnyVatNO,final String cmpnyServiceTaxNo,final String cmpnyRegNum) throws DataAccessException 
	{

		final String uuid = getUUID();
		
		jdbcTemplate.update(new PreparedStatementCreator() {           

		                @Override
		                public PreparedStatement createPreparedStatement(Connection connection)
		                        throws SQLException {
		                    PreparedStatement ps = connection.prepareStatement(sqlProperties.getProperty("insert.cmpnyinfo.cmpnydetails"), Statement.RETURN_GENERATED_KEYS);
		                    ps.setString(1, uuid);
		                    ps.setString(2, cmpnyName);
		                    ps.setString(3, cmpnyLogo);
		                    ps.setString(4, cmpnyDesc);
		                    ps.setString(5, cmpnyTitle);
		                    ps.setDouble(6, cmpnyAccNo);
		                    ps.setString(7, cmpnyBank);
		                    ps.setString(8, cmpnyCheckRef);
		                    ps.setString(9, cmpnyBankIfsc);
		                    ps.setString(10, cmpnyPanNO);
		                    ps.setString(11, cmpnyVatNO);
		                    ps.setString(12, cmpnyServiceTaxNo);
		                    ps.setString(13, cmpnyRegNum);
		                    return ps;
		                }
		            });

		return uuid;
	 }
	/**
	 * Description: insert the company address information to the CMPNY_ADDRESS_DTl Table
	 * @param {link  cmpnyInfoId,  addressDtl1,  addressDtl2,  addressDtl3,
			 addressType,  cmpnyCity,  cmpnyState,  cmpnyCountry,  cmpnyPostal,
			 cmpnyLongitude,  cmpnyLattitude}
	 *@throws {@link DataAccessException}		 
	 */
	@Override
	public void insertCompanyAddress(String cmpnyInfoId, String addressDtl1, String addressDtl2, String addressDtl3,
			String addressType, String cmpnyCity, String cmpnyState, String cmpnyCountry, int cmpnyPostal,
			BigDecimal cmpnyLongitude, BigDecimal cmpnyLattitude) throws DataAccessException
	{
           jdbcTemplate.update(sqlProperties.getProperty("insert.cmpnyaddressdtls.address"),cmpnyInfoId,addressDtl1,addressDtl2,addressDtl3,addressType,cmpnyCity,cmpnyState,cmpnyCountry,cmpnyPostal,cmpnyLongitude,cmpnyLattitude);	
	}

	/**
	 * Description: Get company info id count
	 * @param {@link cmpnyInfoId}
	 * @return int
	 * @throws {@link Exception}
	 */
	@Override
	public int selectCmpnyInfoId(String cmpnyInfoId) throws Exception {
		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.count.cmpnyinfo.cmpnyinfoid"),Integer.class,cmpnyInfoId);
	}

	/**
	 * Description : update company basic information
	 * @param {@link cmpnyName cmpnyRegNum cmpnyLogo cmpnyDesc cmpnyTitle cmpnyAccNo cmpnyCheckRef cmpnyBank cmpnyBankIfsc cmpnyVatNO cmpnyPanNO cmpnyServiceTaxNo cmpnyInfoId}
	 * @throws {@link Exception}
	 */
	@Override
	public void updateCompanyBasicInfo(String cmpnyName, String cmpnyRegNum, String cmpnyLogo, String cmpnyDesc,
			String cmpnyTitle, double cmpnyAccNo, String cmpnyCheckRef, String cmpnyBank, String cmpnyBankIfsc,
			String cmpnyVatNO, String cmpnyPanNO, String cmpnyServiceTaxNo, String cmpnyInfoId) throws Exception {
		 jdbcTemplate.update(sqlProperties.getProperty("update.cmpnyinfo.cmnydetails"),cmpnyName,cmpnyLogo,cmpnyDesc,cmpnyTitle,cmpnyAccNo,cmpnyBank,cmpnyCheckRef,cmpnyBankIfsc,cmpnyPanNO,cmpnyVatNO,cmpnyServiceTaxNo,cmpnyRegNum,cmpnyInfoId);
              		
	}

	/**
	 * Description : update company address details
	 * @param {@link stAddress1 stAddress2 stAddress3 addressType city state country postalCode}
	 * @throws {@link Exception}
	 */
	@Override
	public void updateCmpnyAddress(String stAddress1, String stAddress2, String stAddress3, String addressType,
			String city, String state, String country, int postalCode,int addressDtlId, String cmpnyInfoId) throws Exception {

		jdbcTemplate.update(sqlProperties.getProperty("update.cmpnyaddress.addressdetails"), stAddress1,stAddress2, stAddress3,addressType,city,state,country,postalCode,addressDtlId,cmpnyInfoId);
	}

	/**
	 * Description get configvalue
	 * @param configName
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public  AppConfiguration selectConfigurationValue(String configName) throws DataAccessException {
		
		List<AppConfiguration> appconfigList=jdbcTemplate.query(sqlProperties.getProperty("select.appconfiguration.configvalue"), new Object[] {configName}, new BeanPropertyRowMapper<>(AppConfiguration.class));
		
		if(appconfigList.size() != 1) {
			throw new DataAccessException("Multiple Configuration found for configuration : " + configName) {
				private static final long serialVersionUID = 1L;
			};
		} else {
			return appconfigList.get(0);
		}
	}
	
 /**
  * Description update config value
  * @param configValue
  * @param desc
  * @param configname
  * @throws DataAccessException
  */
	@Override
	public void updateConfiguration(String configValue, String desc,String configname) throws DataAccessException {

		jdbcTemplate.update(sqlProperties.getProperty("select.configuration.all"),configValue,desc,configname);
	}

@Override
public List<WConfigurationTO> selectWConfigurationList() {
	return jdbcTemplate.query(sqlProperties.getProperty("select.configuration.all"), new RowMapper<WConfigurationTO>() {
		@Override
		public WConfigurationTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			WConfigurationTO wConfigurationTO = new WConfigurationTO();
			wConfigurationTO.setConfigName(rs.getString("CONFIG_NAME"));
			wConfigurationTO.setConfigValue(rs.getString("CONFIG_VAL"));
			wConfigurationTO.setConfigDesc(rs.getString("CONFIG_DESC"));
			return wConfigurationTO;
		}
	});
}

@Override
public List<LocationDtls> selectCityList() throws DataAccessException {
	
	return jdbcTemplate.query("select LOCATION_NAME from location_dtls where LOCATION_PARENT_ID=0", new BeanPropertyRowMapper<LocationDtls>(LocationDtls.class));
}

@Override
public List<CategoryMaster> getCategoryList() throws DataAccessException {
	
	return jdbcTemplate.query("select * from category_master where PARANT_ID=0", new BeanPropertyRowMapper<CategoryMaster>(CategoryMaster.class));
}

@Override
public List<CategoryMaster> getSubCategoryList() throws DataAccessException {
	
	return jdbcTemplate.query("select * from category_master where PARANT_ID<>0", new BeanPropertyRowMapper<CategoryMaster>(CategoryMaster.class));
}

@Override
public List<PackagingInfo> getPackingInfoList() throws DataAccessException {
	
	return jdbcTemplate.query("select * from packaging_info", new BeanPropertyRowMapper<PackagingInfo>(PackagingInfo.class));
}

@Override
public void addPackagingInfo(String packagingInfoDesc, String packagingInfoName) {
	
	jdbcTemplate.update("insert into packaging_info(PACKAGING_INFO,PACKAGING_DESCRIPTION) values(?,?)", new Object[] {packagingInfoName, packagingInfoDesc} );
}

@Override
public void editPackagingInfo(String packaginInfoId, String packagingInfoDesc, String packagingInfoName) {
	
	jdbcTemplate.update("update packaging_info set PACKAGING_INFO=?,PACKAGING_DESCRIPTION=? where PACKAGING_INFO_ID=?", new Object[] {packagingInfoName, 
			packagingInfoDesc, packaginInfoId} );
}

@Override
public List<CategoryMaster> selectProductSubCategoryByCatId(String catId) {
	return jdbcTemplate.query("select ID,NAME from category_master where PARANT_ID=?", new Object[] {catId}, new BeanPropertyRowMapper<CategoryMaster>(CategoryMaster.class));
}

@Override
public void insertConfigValues(String configName, String configVal, String configDesc) {
	
	jdbcTemplate.update("insert into configuration(CONFIG_NAME,CONFIG_VAL,CONFIG_DESC) values(?,?,?)", new Object[] {configName, configVal, configDesc});
}

@Override
public void updateConfigValues(String configName, String configVal, String configDesc) {
	jdbcTemplate.update("update configuration set CONFIG_VAL=?,CONFIG_DESC=? where CONFIG_NAME=?", new Object[] {configVal, configDesc,configName});
	
}

@Override
public void insertCategoryDetails(String catName, String url, String catDesc, int parentId) {
	
	jdbcTemplate.update("insert into category_master(NAME,URL,CAT_DESC,PARANT_ID) values(?,?,?,?)", new Object[] {catName, (null == url ? "":url), catDesc, parentId});
}

@Override
public void updateCategoryDetails(String catId, String catName, String url, String catDesc) {
	jdbcTemplate.update("update category_master set NAME=?,URL=?,CAT_DESC=? where ID=?", new Object[] {catName, (null == url ? "" : url), catDesc, catId});
}

@Override
public void deleteCategoryDetails(String id) {
	jdbcTemplate.update("delete from category_master where ID=?", new Object[] {id});
}

@Override
public void updateSubCategoryDetails(String catId, String catName, String parentId, String catDesc, String url) {
	jdbcTemplate.update("update category_master set NAME=?,PARANT_ID=?,CAT_DESC=?,URL=? where ID=?", new Object[] {catName, parentId, catDesc, url, catId});
	
}

@Override
public List<LocationDtls> selectCityListByStateName(String stateId) {
	
	return jdbcTemplate.query("select * from location_dtls where LOCATION_PARENT_ID in(select LOCATION_ID from location_dtls where LOCATION_NAME=?)", new Object[] {stateId},
			new BeanPropertyRowMapper<LocationDtls>(LocationDtls.class));
}

@Override
public List<ItemMasterDtl> selectProductDetailsBySubcategoryId(String subCatId) {
	
	return jdbcTemplate.query("select ITEM_MASTER_DTLS_ID,ITEM_NM from item_master_dtls where ITEM_SUB_CATEGORY=?", new Object[] {subCatId}, 
			new BeanPropertyRowMapper<ItemMasterDtl>(ItemMasterDtl.class));
}

@Override
public ItemMasterDtl selectProductMasterCartonPriceById(String productId) {
	
	return jdbcTemplate.query("select MASTER_CARTON_PRICE from item_master_dtls where ITEM_MASTER_DTLS_ID=?", new Object[] {productId}, 
			new BeanPropertyRowMapper<ItemMasterDtl>(ItemMasterDtl.class)).get(0);
}

@Override
public List<AdminLoginDtls> selectUserDetailsByEmailIdAndPass(String emailId, String password) {
	
	return jdbcTemplate.query("select * from admin_login_dtls where EMAIL_ID=? and PASSWORD=?", new Object[] {emailId, password}, new BeanPropertyRowMapper<AdminLoginDtls>(AdminLoginDtls.class));
}

@Override
public List<LocationDtls> selectStateList() {
	
	return jdbcTemplate.query("select * from location_dtls where LOCATION_PARENT_ID=0 order by LOCATION_NAME", new BeanPropertyRowMapper<LocationDtls>(LocationDtls.class));
}

@Override
public List<LocationDtls> selectCityListByStateId(String stateId) {
	
	return jdbcTemplate.query("select * from location_dtls where LOCATION_PARENT_ID=? order by LOCATION_NAME", new Object[] {stateId}, new BeanPropertyRowMapper<LocationDtls>(LocationDtls.class));
}

@Override
public int insertEnquiryDetails(EnquiryDetails request) {
	
	return jdbcTemplate.update("insert into enquiry_dtls(CITY_ID,STATE_ID,EMAIL_ID,ENQUIRY_TYPE,FIRST_NM,LAST_NM,PHN_NM,MESSAGE,PINNO,COMPANY_NAME) values(?,?,?,?,?,?,?,?,?,?)", 
			request.getCityId(), request.getStateId(), request.getEmail(), request.getEnquiryType(), request.getFirstNm(), request.getLastNm(), 
			request.getPhnNm(),request.getMessage(), request.getPinNo(), request.getCompanyName());
}

@Override
public List<LocationDtls> selectAllStates() {
	
	return jdbcTemplate.query("select * from location_dtls where LOCATION_PARENT_ID=0 order by LOCATION_NAME", new BeanPropertyRowMapper<LocationDtls>(LocationDtls.class));
}

@Override
public void wDeleteLocation(String locationId) throws Exception {
	
	int rowAffected = jdbcTemplate.update("update location_dtls set IS_ACTIVE='false' where LOCATION_ID=?", locationId);
	if(rowAffected != 1) {
		throw new Exception("Location Cannot be deleted at the moment");
	}
}

@Override
public void editWLocation(LocationDtls request) throws Exception {
	
	int rowAffected = jdbcTemplate.update("update location_dtls set LOCATION_NAME=?,LOCATION_DESC=?,LOCATION_PARENT_ID=?,IS_ACTIVE=? where LOCATION_ID=?", 
			request.getLocationName(), request.getLocationDesc(), request.getLocationParentId(), request.getIsActive(), request.getLocationId());
	if(rowAffected != 1) {
		throw new Exception("Location Cannot be deleted at the moment");
	}
}

@Override
public void addWLocation(LocationDtls request) throws Exception {
	
	int rowAffected = jdbcTemplate.update("insert into location_dtls(LOCATION_NAME,LOCATION_DESC,LOCATION_PARENT_ID,IS_ACTIVE) values(?,?,?,?)", 
			request.getLocationName(), request.getLocationDesc(), request.getLocationParentId(), request.getIsActive());
	if(rowAffected != 1) {
		throw new Exception("Location Cannot be deleted at the moment");
	}
}
}
