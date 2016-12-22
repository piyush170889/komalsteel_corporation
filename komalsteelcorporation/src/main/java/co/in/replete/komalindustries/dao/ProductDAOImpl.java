package co.in.replete.komalindustries.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.Statement;

import co.in.replete.komalindustries.beans.CategoryDetailsTO;
import co.in.replete.komalindustries.beans.CategoryTO;
import co.in.replete.komalindustries.beans.ItemInventoryUpdateTO;
import co.in.replete.komalindustries.beans.ProductAllDetailsTO;
import co.in.replete.komalindustries.beans.ProductDetailsByCatAndSubCatTO;
import co.in.replete.komalindustries.beans.ProductDetailsTO;
import co.in.replete.komalindustries.beans.RefillTO;
import co.in.replete.komalindustries.beans.SingleValueCommonClass;
import co.in.replete.komalindustries.beans.SubCategoryTo;
import co.in.replete.komalindustries.beans.entity.CategoryMaster;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.ItemsInventoryDtl;
import co.in.replete.komalindustries.beans.entity.PackagingInfo;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.webcontroller.beans.CategoryMasterTO;
import co.in.replete.komalindustries.webcontroller.beans.WItemDetailsTO;

@Repository
public class ProductDAOImpl extends BaseDAOImpl implements ProductDAO{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Properties sqlProperties; 
	
	
	/**
	 * Description : Executes the select query to get product details
	 * 
	 * @param  pageNum
	 * @param  companyInfoId
	 * @return  {@link List<ProductDetailsTO>}
	 * @throws  {@link Exception}
	 */
	public List<ProductDetailsTO> selectProductDetails(int pageNum, String companyInfoId,int maxLimit) throws DataAccessException,Exception {
	
		int startIdx = (pageNum - 1) * maxLimit;
		int endIdx = maxLimit;
		
		List<ProductDetailsTO> productsList = jdbcTemplate.query(sqlProperties.getProperty("select.productdetails.wall"), new Object[] {companyInfoId, startIdx, endIdx}, new RowMapper<ProductDetailsTO>() {
			@Override
			public ProductDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductDetailsTO productDetailsTO = new ProductDetailsTO();
				
				productDetailsTO.setCmpnyInfoId(rs.getString("CMPNY_INFO_ID"));
				productDetailsTO.setInitialQuantity(rs.getDouble("AVL_QTY"));
				productDetailsTO.setItemCategory(rs.getString("ITEM_CATEGORY"));
				productDetailsTO.setItemContentInfo(rs.getString("ITEM_CONTENT_INFO"));
				productDetailsTO.setItemDesc(rs.getString("ITEM_DESC"));
//				productDetailsTO.setItemImage(Base64.encodeBase64String(rs.getBytes("ITEM_IMAGE")));
				productDetailsTO.setItemManufacturer(rs.getString("ITEM_MANUFACTURER"));
				productDetailsTO.setItemMasterDtlsId(rs.getInt("ITEM_MASTER_DTLS_ID"));
				productDetailsTO.setItemNm(rs.getString("ITEM_NM"));
				/*productDetailsTO.setItemPckgInfo(rs.getString("ITEM_PCKG_INFO"));
				productDetailsTO.setItemPckgType(rs.getString("ITEM_PCKG_TYPE"));*/
				productDetailsTO.setUom(rs.getString("UOM"));
				productDetailsTO.setItemsInMasterCarton(rs.getInt("ITEMS_IN_MASTER_CARTON"));
				productDetailsTO.setMasterCartonPrice(rs.getFloat("MASTER_CARTON_PRICE"));
				productDetailsTO.setItemSubCategory(rs.getString("ITEM_SUB_CATEGORY"));
				productDetailsTO.setOfferDtlsId(rs.getInt("OFFER_DTLS_ID"));
				
				return productDetailsTO;
			}
		});
		
		if(productsList.size() > 0) {
			return productsList;
		} else {
			throw new Exception("No Records Found");
		}
	}
	
	/**
	 * Description : Get the product categories
	 * 
	 * @param  companyInfoId
	 * @return  {@link List<String>}
	 * @throws  {@link Exception}
	 */
	@Override
	public List<String> selectProductCategory(String companyInfoId) throws DataAccessException {
	
		return jdbcTemplate.queryForList(sqlProperties.getProperty("select.itemdetails.category"), new Object[] {companyInfoId},String.class);
	}
	
	/**
	 * Description : Get the product sub categories for the category specified
	 * 
	 * @param  companyInfoId
	 * @param  category
	 * @return  {@link List<SingleValueCommonClass>}
	 * @throws  {@link Exception}
	 */
	@Override
	public List<SingleValueCommonClass> selectProductSubcategoriesByCategory(String category, String companyInfoId) throws DataAccessException {
	
		return jdbcTemplate.query(sqlProperties.getProperty("select.itemdetails.subcategory"), new Object[] {category, companyInfoId}, new RowMapper<SingleValueCommonClass>() {
			@Override
			public SingleValueCommonClass mapRow(ResultSet rs, int arg1) throws SQLException {
				
				SingleValueCommonClass singleValueCommonClass = new SingleValueCommonClass();
				singleValueCommonClass.setValue(rs.getString("ITEM_SUB_CATEGORY"));
				return singleValueCommonClass;
			}
		});
	}
	
	
	/**
	 * Description : Gets the products details by category and sub category specified
	 * 
	 * @param category
	 * @param subCategory
	 * @param companyInfoId
	 * @return  {@link List<ItemMasterDtl>}
	 * @throws  {@link DataAccessException}
	 */
	@Cacheable(value="productsListWithImage", key="#subCategory", unless="#result==null")
	public List<ProductDetailsByCatAndSubCatTO> selectProductdetailsByCatAndSubCatWithImage(int category, int subCategory, String companyInfoId) throws DataAccessException {
		System.out.println("Inside With Image Fetch Mode");
		return jdbcTemplate.query(sqlProperties.getProperty("select.itemmasterdetails.bycategoryandsubcategory1"), 
				new Object[] {category, subCategory, companyInfoId}, new RowMapper<ProductDetailsByCatAndSubCatTO>() {
			@Override
			public ProductDetailsByCatAndSubCatTO mapRow(ResultSet rs, int arg1) throws SQLException {
				
				ProductDetailsByCatAndSubCatTO itemMasterDtl = new ProductDetailsByCatAndSubCatTO();
				itemMasterDtl.setItemCategory(rs.getInt("ITEM_CATEGORY"));
				itemMasterDtl.setItemContentInfo(rs.getString("ITEM_CONTENT_INFO"));
				itemMasterDtl.setItemDesc(rs.getString("ITEM_DESC"));
				itemMasterDtl.setItemImage(rs.getBytes("ITEM_IMAGE"));
				itemMasterDtl.setItemManufacturer(rs.getString("ITEM_MANUFACTURER"));
				itemMasterDtl.setItemMasterDtlsId(rs.getInt("ITEM_MASTER_DTLS_ID"));
				itemMasterDtl.setItemNm(rs.getString("ITEM_NM"));
				itemMasterDtl.setItemSubCategory(rs.getInt("ITEM_SUB_CATEGORY"));
				itemMasterDtl.setUom(rs.getString("UOM"));
				itemMasterDtl.setItemsInMasterCarton(rs.getInt("ITEMS_IN_MASTER_CARTON"));
				itemMasterDtl.setMasterCartonPrice(rs.getFloat("MASTER_CARTON_PRICE"));
				itemMasterDtl.setOfferDtlsId(rs.getInt("OFFER_DTLS_ID"));
//				itemMasterDtl.setItemPrice(rs.getString("MRP"));
				itemMasterDtl.setMasterCartonQtyRange(rs.getString("MASTER_CARTON_QTY_RANGE"));
				itemMasterDtl.setMasterCartonQtyIncVal(rs.getString("MASTER_CARTON_QTY_INC_VAL"));
				itemMasterDtl.setItemNo(rs.getString("ITEM_NO"));
				
				return itemMasterDtl;
			}
		});
	}
	
	@Cacheable(value="productsListWithoutImage", key="#subCategory", unless="#result==null")
	public List<ProductDetailsByCatAndSubCatTO> selectProductdetailsByCatAndSubCatWithoutImage(int category, int subCategory, String companyInfoId) throws DataAccessException {
		System.out.println("Inside Without Image Fetch Mode");
		return jdbcTemplate.query(sqlProperties.getProperty("select.itemmasterdetails.bycategoryandsubcategory1"), 
				new Object[] {category, subCategory, companyInfoId}, new RowMapper<ProductDetailsByCatAndSubCatTO>() {
			@Override
			public ProductDetailsByCatAndSubCatTO mapRow(ResultSet rs, int arg1) throws SQLException {
				
				ProductDetailsByCatAndSubCatTO itemMasterDtl = new ProductDetailsByCatAndSubCatTO();
				itemMasterDtl.setItemCategory(rs.getInt("ITEM_CATEGORY"));
				itemMasterDtl.setItemContentInfo(rs.getString("ITEM_CONTENT_INFO"));
				itemMasterDtl.setItemDesc(rs.getString("ITEM_DESC"));
				itemMasterDtl.setItemManufacturer(rs.getString("ITEM_MANUFACTURER"));
				itemMasterDtl.setItemMasterDtlsId(rs.getInt("ITEM_MASTER_DTLS_ID"));
				itemMasterDtl.setItemNm(rs.getString("ITEM_NM"));
				itemMasterDtl.setItemSubCategory(rs.getInt("ITEM_SUB_CATEGORY"));
				itemMasterDtl.setUom(rs.getString("UOM"));
				itemMasterDtl.setItemsInMasterCarton(rs.getInt("ITEMS_IN_MASTER_CARTON"));
				itemMasterDtl.setMasterCartonPrice(rs.getFloat("MASTER_CARTON_PRICE"));
				itemMasterDtl.setOfferDtlsId(rs.getInt("OFFER_DTLS_ID"));
//				itemMasterDtl.setItemPrice(rs.getString("MRP"));
				itemMasterDtl.setMasterCartonQtyRange(rs.getString("MASTER_CARTON_QTY_RANGE"));
				itemMasterDtl.setMasterCartonQtyIncVal(rs.getString("MASTER_CARTON_QTY_INC_VAL"));
				itemMasterDtl.setItemNo(null == rs.getString("ITEM_NO") ? "" : rs.getString("ITEM_NO"));
				
				return itemMasterDtl;
			}
		});
	}
	
	/*@Override
	public void updateSubCategoryCache(int category, int subCategoryId, String companyInfoId) {
		updateCacheWithImage(category, subCategoryId, companyInfoId);
		updateCacheWithoutImage(category, subCategoryId, companyInfoId);
	}*/

	@CachePut(value="productsListWithImage", key="#subCategoryId", unless="#result==null")
	public List<ProductDetailsByCatAndSubCatTO> updateCacheWithImage(int category, int subCategoryId, String companyInfoId) {	
		System.out.println("<!---------UPDATING CACHE WITH IMAGE--------->");
		return jdbcTemplate.query(sqlProperties.getProperty("select.itemmasterdetails.bycategoryandsubcategory1"), 
				new Object[] {category, subCategoryId, companyInfoId}, new RowMapper<ProductDetailsByCatAndSubCatTO>() {
			@Override
			public ProductDetailsByCatAndSubCatTO mapRow(ResultSet rs, int arg1) throws SQLException {
				
				ProductDetailsByCatAndSubCatTO itemMasterDtl = new ProductDetailsByCatAndSubCatTO();
				itemMasterDtl.setItemCategory(rs.getInt("ITEM_CATEGORY"));
				itemMasterDtl.setItemContentInfo(rs.getString("ITEM_CONTENT_INFO"));
				itemMasterDtl.setItemDesc(rs.getString("ITEM_DESC"));
				itemMasterDtl.setItemImage(rs.getBytes("ITEM_IMAGE"));
				itemMasterDtl.setItemManufacturer(rs.getString("ITEM_MANUFACTURER"));
				itemMasterDtl.setItemMasterDtlsId(rs.getInt("ITEM_MASTER_DTLS_ID"));
				itemMasterDtl.setItemNm(rs.getString("ITEM_NM"));
				itemMasterDtl.setItemSubCategory(rs.getInt("ITEM_SUB_CATEGORY"));
				itemMasterDtl.setUom(rs.getString("UOM"));
				itemMasterDtl.setItemsInMasterCarton(rs.getInt("ITEMS_IN_MASTER_CARTON"));
				itemMasterDtl.setMasterCartonPrice(rs.getFloat("MASTER_CARTON_PRICE"));
				itemMasterDtl.setOfferDtlsId(rs.getInt("OFFER_DTLS_ID"));
//				itemMasterDtl.setItemPrice(rs.getString("MRP"));
				itemMasterDtl.setMasterCartonQtyRange(rs.getString("MASTER_CARTON_QTY_RANGE"));
				itemMasterDtl.setMasterCartonQtyIncVal(rs.getString("MASTER_CARTON_QTY_INC_VAL"));
				itemMasterDtl.setItemNo(rs.getString("ITEM_NO"));
				
				return itemMasterDtl;
			}
		});
	}
	
	@CachePut(value="productsListWithoutImage", key="#subCategoryId", unless="#result==null")
	public List<ProductDetailsByCatAndSubCatTO> updateCacheWithoutImage(int category, int subCategoryId, String companyInfoId) {	
		System.out.println("<!---------UPDATING CACHE WITHOUT IMAGE--------->");
		return jdbcTemplate.query(sqlProperties.getProperty("select.itemmasterdetails.bycategoryandsubcategory1"), 
				new Object[] {category, subCategoryId, companyInfoId}, new RowMapper<ProductDetailsByCatAndSubCatTO>() {
			@Override
			public ProductDetailsByCatAndSubCatTO mapRow(ResultSet rs, int arg1) throws SQLException {
				
				ProductDetailsByCatAndSubCatTO itemMasterDtl = new ProductDetailsByCatAndSubCatTO();
				itemMasterDtl.setItemCategory(rs.getInt("ITEM_CATEGORY"));
				itemMasterDtl.setItemContentInfo(rs.getString("ITEM_CONTENT_INFO"));
				itemMasterDtl.setItemDesc(rs.getString("ITEM_DESC"));
				itemMasterDtl.setItemManufacturer(rs.getString("ITEM_MANUFACTURER"));
				itemMasterDtl.setItemMasterDtlsId(rs.getInt("ITEM_MASTER_DTLS_ID"));
				itemMasterDtl.setItemNm(rs.getString("ITEM_NM"));
				itemMasterDtl.setItemSubCategory(rs.getInt("ITEM_SUB_CATEGORY"));
				itemMasterDtl.setUom(rs.getString("UOM"));
				itemMasterDtl.setItemsInMasterCarton(rs.getInt("ITEMS_IN_MASTER_CARTON"));
				itemMasterDtl.setMasterCartonPrice(rs.getFloat("MASTER_CARTON_PRICE"));
				itemMasterDtl.setOfferDtlsId(rs.getInt("OFFER_DTLS_ID"));
//				itemMasterDtl.setItemPrice(rs.getString("MRP"));
				itemMasterDtl.setMasterCartonQtyRange(rs.getString("MASTER_CARTON_QTY_RANGE"));
				itemMasterDtl.setMasterCartonQtyIncVal(rs.getString("MASTER_CARTON_QTY_INC_VAL"));
				itemMasterDtl.setItemNo(rs.getString("ITEM_NO"));
				
				return itemMasterDtl;
			}
		});
	}

	@Override
	public void deleteProductById(String id) {
		
		jdbcTemplate.update("update item_master_dtls set IS_ACTIVE='Inactive' where ITEM_MASTER_DTLS_ID=?", new Object[] {id});
	}
	
	/**
	 * Description: select item count to find already item present or not
	 * @param:{@link itemName}
	 * @return {@link int}
	 * @throws {@link DataAccessException}
	 */
	@Override
	public int selectItemCount(String itemName) throws DataAccessException {
		
		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.itemmasterdtl.itemcount"),Integer.class, itemName);
	}

	/**
	 * Description: insert new product
	 * @param{@link cmpnyInfoId  itemNm itemCategoryCd itemSubCategoryCd itemContentInfo itemDesc itemImage  itemManufacturer itemPckgInfo itemPckgTypeCd offerId}
	 * @return {@link generatedPrimaryKeyValue}
	 * @throws IOException 
	 * @throws {@link DataAccessException}
	 * 
	 */
	@Override
	public int insertProduct(final String cmpnyInfoId, final String itemNm, final String itemCategoryCd, final String itemSubCategoryCd,
			final String itemContentInfo, final String itemDesc, final MultipartFile itemImage, final String itemManufacturer, final String itemPckgInfo,
		    final String itemPckgTypeCd, int offerId, int itemsInMasterCarton, Float masterCartonPrice, String masterCartonQtyRange, String masterCartonIncVal,
		    String itemNo) throws DataAccessException, IOException {
			
		byte[] itemImageBytes = itemImage.getBytes();
		//Create the prepared Statement as needed
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				PreparedStatement psmt = con.prepareStatement(sqlProperties.getProperty("insert.itemmasterdtl.itembasicdetails"), Statement.RETURN_GENERATED_KEYS);
				psmt.setString(1,cmpnyInfoId);
				psmt.setString(2,itemNm);
				psmt.setString(3,itemCategoryCd);
				psmt.setString(4,itemSubCategoryCd);
				psmt.setString(5,itemDesc);
				psmt.setString(6,itemContentInfo);
				psmt.setString(7,itemPckgTypeCd);
				psmt.setString(8,itemPckgInfo);
				psmt.setString(9,itemManufacturer);
				psmt.setInt(10, offerId);
				psmt.setBytes(11,itemImageBytes);
				psmt.setInt(12, itemsInMasterCarton);
				psmt.setFloat(13, masterCartonPrice);
				psmt.setString(14, masterCartonQtyRange);
				psmt.setString(15, masterCartonIncVal);
				psmt.setString(16, itemNo);
				
				return psmt;
				
			}
		};
		
		//Create a holder to hold the auto generated primary key
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(psc, holder);
		
		//Get generated Primary key
		int generatedPrimaryKeyValue = holder.getKey().intValue();
		return generatedPrimaryKeyValue;
	}

	/**
	 * Description: insert items inventory details
	 * @param {@link itemMasterId  initialQuantity mrp threshholdValue cmpnyInfoId bookedQty}
	 * @throws {@link Exception }
	 */
	@Override
	public void insertItemsInventoryDetails(int itemMasterId, double initialQuantity, double mrp,
			double threshholdValue,String cmpnyInfoId, double bookedQty) throws Exception 
	{
		jdbcTemplate.update(sqlProperties.getProperty("insert.inventorydetails.item"),itemMasterId,cmpnyInfoId,initialQuantity,bookedQty,threshholdValue,mrp);
		
	}

	/**
	 * Description: insert inventory refil details
	 * @param {@link itemMasterId initialQuantity mrp rejectedScrap rejectedRework vendorId refilPrice perUnitPrice cmpnyInfoId}
	 * @throws {@link Exception}
	 */
	@Override
	public void insertInventoryRefilDetails(int itemMasterId, double initialQuantity, double mrp,double rejectedScrap,double rejectedRework,int vendorId, double refilPrice, double perUnitPrice,String cmpnyInfoId)
			throws Exception {
		
		jdbcTemplate.update(sqlProperties.getProperty("insert.inventoryrefildtls.refilitem"),initialQuantity,rejectedScrap,rejectedRework,vendorId,refilPrice,itemMasterId,perUnitPrice,mrp,cmpnyInfoId);
		
	}

	/**
	 * Description: select item count by item master details id 
	 * @param {@link id}
	 * @throws {@link DataAccessException}
	 */
	@Override
	public int selectItem(int id) throws DataAccessException {
		
		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.itemmasterdtl.id"),Integer.class,id);
	}

	/**
	 * Description: update product details
	 * @param {@link  id contentInfo  itemCategory itemDesc itemImage itemName manufacturer offerDetailsId packInfo  packType}
	 * @throws {@link DataAccessException}
	 */
	@Override
	public void updateProductDetails(int id, String contentInfo, String itemCategory, String itemDesc, byte[] itemImage,
			String itemName, String manufacturer, int offerDetailsId, String packInfo, String packType,
			String subCategory, int itemInMasterCarton, Float masterCartonPrice, String masterCartonQtyRange, 
			String masterCartonQtyIncVal, String itemNo) throws DataAccessException {
	
		if(null == itemImage || itemImage.length == 0) {
			jdbcTemplate.update(sqlProperties.getProperty("update.itemmasterdtl.item"),itemName,itemCategory,subCategory,itemDesc,contentInfo,packType,packInfo,
					manufacturer,offerDetailsId,itemInMasterCarton,masterCartonPrice, "0-" + masterCartonQtyRange, masterCartonQtyIncVal, itemNo, id);
		} else {
			String sql = "update item_master_dtls set ITEM_NM=?,ITEM_CATEGORY=?,ITEM_SUB_CATEGORY=?,ITEM_DESC=?,"
					+ "ITEM_CONTENT_INFO=?,ITEM_PCKG_TYPE=?,UOM=?,ITEM_MANUFACTURER=?,OFFER_DTLS_ID=?,ITEMS_IN_MASTER_CARTON=?,"
					+ "MASTER_CARTON_PRICE=?,MASTER_CARTON_QTY_RANGE=?,MASTER_CARTON_QTY_INC_VAL=?,ITEM_IMAGE=?,ITEM_NO=? where ITEM_MASTER_DTLS_ID=?";
			jdbcTemplate.update(sql,itemName,itemCategory,subCategory,itemDesc,contentInfo,packType,packInfo,
					manufacturer,offerDetailsId,itemInMasterCarton,masterCartonPrice, "0-" + masterCartonQtyRange, masterCartonQtyIncVal, itemImage, itemNo, id);
		}
		
		
	}
    
	/**
	 * Description : update inventory item details 
	 * @param{@link id itemMasterDtlId cmpnyInfoId avlQty  bookedQty threshol mrp  shelfCode }
	 * @throws{@link DataAccessException}
	 */
	@Override
	public void updateInventoryItem(int id, int itemMasterDtlId, String cmpnyInfoId, double avlQty,
			double threshol, double mrp) throws DataAccessException {

		jdbcTemplate.update(sqlProperties.getProperty("update.iteminventorydtl.refil"),avlQty,threshol,mrp,id,itemMasterDtlId,cmpnyInfoId);
	}

	/**
	 * Description: insert refil details of item
	 * @param{@link avlQty rejectedScrapQty rejectedReworkQty vendorId refilPrice itemMasterDtlId perUnitCostPrice mrp comments cmpnyInfoId }
	 * @throws{@link DataAccessException}
	 */
	@Override
	public void insertRefilDetails(double avlQty, double rejectedScrapQty, double rejectedReworkQty,int vendorId, double refilPrice,
			int itemMasterDtlId, double perUnitCostPrice, double mrp, String comments,String cmpnyInfoId) throws DataAccessException {

		jdbcTemplate.update(sqlProperties.getProperty("inser.inventoryrefildtls"),avlQty,rejectedScrapQty,rejectedReworkQty,vendorId,refilPrice,itemMasterDtlId,perUnitCostPrice,mrp,comments,cmpnyInfoId);
	}

	/**
	 * Description : select refill history of item
	 * @param{@link id}
	 * @return{@link List<RefillTO>}
	 * @throws{@link Exception}
	 */
	@Override
	public List<RefillTO> selectRefilHistory(int id) throws DataAccessException {
		return jdbcTemplate.query(sqlProperties.getProperty("select.inventoryrefildtls.refilhistory"),new Object[]{id},  new RowMapper<RefillTO>() {
			@Override
			public RefillTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RefillTO refillTO = new RefillTO();
				
				refillTO.setInventoryRefillDtlsId(rs.getInt("INVENTORY_REFILL_DTLS_ID"));
				refillTO.setRefillDate(rs.getDate("REFILL_DT"));
				refillTO.setReceivedQty(rs.getDouble("RECEIVED_QTY"));
				refillTO.setRejectedScrapQty(rs.getDouble("REJECTED_SCRAP_QTY"));
				refillTO.setRejectedrework(rs.getDouble("REJECTED_REWORK_QTY"));
				refillTO.setVendorId(rs.getInt("VENDOR_ID"));
				refillTO.setRefillPrice(rs.getDouble("REFILL_PRICE"));
				refillTO.setItemMasterDtlsId(rs.getInt("ITEM_MASTER_DTLS_ID"));
				refillTO.setPerUnitCostPrice(rs.getDouble("PER_UNIT_COST_PRICE"));
				refillTO.setMrp(rs.getDouble("MRP"));
				refillTO.setComments(rs.getString("COMMENTS"));
				refillTO.setCreatedTs(rs.getDate("CREATED_TS"));
				refillTO.setModifiedTs(rs.getDate("MODIFIED_TS"));
				
				return refillTO;
			}
		});
	}

	/**
	 * Description : select Item count in refill
	 * @param{@link id}
	 * @return{@link int}
	 * @throws{@link DataAccessException}
	 */
	@Override
	public int getRefilCount(int id) throws DataAccessException {
		
		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.invetoryrefildtls.itemcount"),Integer.class,id);

	}
	
	@Override
	public List<WItemDetailsTO> selectAllItemsInfo() {

		return jdbcTemplate.query(sqlProperties.getProperty("select.productdetails.all"), new RowMapper<WItemDetailsTO>() {
			@Override
			public WItemDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				WItemDetailsTO wItemDetailsTO = new WItemDetailsTO();
				
				wItemDetailsTO.setProductId(Integer.toString(rs.getInt("ITEM_MASTER_DTLS_ID")));
				wItemDetailsTO.setProductName(rs.getString("ITEM_NM"));
				wItemDetailsTO.setCategoryName(rs.getString("CATEGORY"));
				wItemDetailsTO.setSubCategoryName(rs.getString("SUBCATEGORY"));
				wItemDetailsTO.setProductCategory(rs.getString("ITEM_CATEGORY"));
				wItemDetailsTO.setProductSubCategory(rs.getString("ITEM_SUB_CATEGORY"));
//				wItemDetailsTO.setProductDescription(rs.getString("ITEM_DESC"));
//				wItemDetailsTO.setProductPackaging(rs.getString("ITEM_PCKG_TYPE"));
				wItemDetailsTO.setProductPackagingInfo(rs.getString("UOM"));
//				wItemDetailsTO.setProductContent(rs.getString("ITEM_CONTENT_INFO"));
				/*wItemDetailsTO.setProductAvlQty(Float.toString(rs.getFloat("AVL_QTY")));
				wItemDetailsTO.setProductBookedQty(Float.toString(rs.getFloat("BOOKED_QTY")));
				wItemDetailsTO.setProductThresholdVal(Float.toString(rs.getFloat("THRHLD_VAL")));
				wItemDetailsTO.setProductMrp(Float.toString(rs.getFloat("MRP")));*/
				wItemDetailsTO.setItemsInMasterCarton(rs.getInt("ITEMS_IN_MASTER_CARTON"));
				/*wItemDetailsTO.setMasterCartonPrice(rs.getFloat("MASTER_CARTON_PRICE"));*/
				wItemDetailsTO.setMasterCartonQtyRange(rs.getString("MASTER_CARTON_QTY_RANGE"));
				wItemDetailsTO.setMasterCartonQtyIncVal(rs.getString("MASTER_CARTON_QTY_INC_VAL"));
				wItemDetailsTO.setItemNo(rs.getString("ITEM_NO"));
				wItemDetailsTO.setIsActive(rs.getString("IS_ACTIVE"));
				
				return wItemDetailsTO;
			}
		});
	}
	
	@Override
	public Integer selectItemInventoryId(int itemMasterDtlId) {
		
		return (Integer)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.iteminventorydtls.id"), Integer.class, new Object[] {itemMasterDtlId});
	}

	/**
	 * Description:  select category list
	 */
	@Override
	public List<CategoryTO> selectCategoryDetails(int pageNum, int maxLimit) throws Exception {
		int startIdx = (pageNum - 1) * maxLimit;
		
		return jdbcTemplate.query(sqlProperties.getProperty("select.category.list"), new Object[] {startIdx, maxLimit}, new RowMapper<CategoryTO>() {
			@Override
			public CategoryTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CategoryTO categoryTO = new CategoryTO();
				
				categoryTO.setCategoryId(rs.getInt("ID"));
				categoryTO.setCategoryName(rs.getString("NAME"));
				categoryTO.setUrl(rs.getString("URL"));
				return categoryTO;
			}
		});
	}

	/**
	 * Description: select category count
	 */
	@Override
	public int selectCategoryCount() throws DataAccessException {
		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.category.count"),Integer.class);
	}

	/**
	 * select subcategory list
	 * @param categoryid
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<SubCategoryTo> selectSubCategoryDetails(int categoryid) throws DataAccessException {
		return jdbcTemplate.query(sqlProperties.getProperty("select.subcategory.list"), new Object[] {categoryid}, new RowMapper<SubCategoryTo>() {
			@Override
			public SubCategoryTo mapRow(ResultSet rs, int rowNum) throws SQLException {
				SubCategoryTo subCategoryTo = new SubCategoryTo();
				subCategoryTo.setSubCategoryId(rs.getInt("ID"));
				subCategoryTo.setSubCategoryName(rs.getString("NAME"));
				
				return subCategoryTo;
			}
		});
	}
	
	@Override
	public List<CategoryMaster> selectSubCategoryDetailsByCategoryId(int categoryid) {
		return jdbcTemplate.query("select * from category_master where PARANT_ID=?", new Object[] {categoryid}, new BeanPropertyRowMapper<CategoryMaster>(CategoryMaster.class));
	}

	@Override
	public List<CategoryMasterTO> selectCategoryMasterDetails() {
		return jdbcTemplate.query(sqlProperties.getProperty("select.categorymaster.details"), new RowMapper<CategoryMasterTO>() {
			@Override
			public CategoryMasterTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CategoryMasterTO categoryMasterTO = new CategoryMasterTO();
				categoryMasterTO.setId(rs.getInt("ID"));
				categoryMasterTO.setName(rs.getString("NAME"));
				categoryMasterTO.setUrl(rs.getString("URL"));
				categoryMasterTO.setCatDesc(rs.getString("CAT_DESC"));
				return categoryMasterTO;
			}
		});
	}
	
	@Override
	public List<CategoryDetailsTO> selectAllCategoryDetails(String companyInfoId) {
		return jdbcTemplate.query(sqlProperties.getProperty("select.categorymaster.details"), new BeanPropertyRowMapper<CategoryDetailsTO>(CategoryDetailsTO.class));
	}

	@Override
	public List<CategoryMasterTO> selectSubCategoryMasterDetails() {
		return jdbcTemplate.query(sqlProperties.getProperty("select.subcategorymaster.details"), new RowMapper<CategoryMasterTO>() {
			@Override
			public CategoryMasterTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CategoryMasterTO categoryMasterTO = new CategoryMasterTO();
				categoryMasterTO.setId(rs.getInt("ID"));
				categoryMasterTO.setParentId(rs.getInt("PARANT_ID"));
				categoryMasterTO.setName(rs.getString("NAME"));
				categoryMasterTO.setUrl(rs.getString("URL"));
				categoryMasterTO.setCatDesc(rs.getString("CAT_DESC"));
				categoryMasterTO.setParantNm(rs.getString("PARANT_NM"));
				
				return categoryMasterTO;
			}
		});
	}
	
	@Override
	public List<PackagingInfo> selectPackagingInfoDetails() {
		
		return jdbcTemplate.query("select * from packaging_info", new BeanPropertyRowMapper<PackagingInfo>(PackagingInfo.class));
	}
	
	@Override
	public ProductAllDetailsTO selectProductDetailsById(String productId) {
		
		String sql = "select * from item_master_dtls as imd inner join items_inventory_dtls as iid on imd.ITEM_MASTER_DTLS_ID=iid.ITEM_MASTER_DTLS_ID "
				+ "AND imd.ITEM_MASTER_DTLS_ID=?";
		return jdbcTemplate.query(sql, new Object[] {productId}, new BeanPropertyRowMapper<ProductAllDetailsTO>(ProductAllDetailsTO.class)).get(0);
	}
	
	@Override
	public byte[] selectProductImage(String id) {
		
		return jdbcTemplate.queryForObject("select ITEM_IMAGE from item_master_dtls where ITEM_MASTER_DTLS_ID=?", new Object[] {id}, 
				byte[].class);
	}
	
	@Override
	public void updateInventoryDetailsById(Integer productInventoryDetailsId, double thresholdVal, double mrp) {
		
		jdbcTemplate.update("update items_inventory_dtls set THRHLD_VAL=?,MRP=? where ITEMS_INVENTORY_DTLS_ID=?", new Object[] {thresholdVal, mrp, productInventoryDetailsId});
	}
	
	@Override
	public void updateInventoryDetails(ItemInventoryUpdateTO request) throws ServicesException {
		//Get items inventory details
		ItemsInventoryDtl itemInventoryDtls = jdbcTemplate.query("select * from items_inventory_dtls where ITEM_MASTER_DTLS_ID=?", 
				new Object[] {request.getItemMasterDtlsId()}, new BeanPropertyRowMapper<ItemsInventoryDtl>(ItemsInventoryDtl.class)).get(0);
		
		//Inventory Qty to upadte
		Float qtyToUpdate = Float.parseFloat(request.getInvProdRefillQty()) + itemInventoryDtls.getAvlQty();
		
		//Update item inventory details
		int updateNoOfRows = jdbcTemplate.update("update items_inventory_dtls set AVL_QTY=?,MRP=?,LAST_REFILL_DT=? where ITEMS_INVENTORY_DTLS_ID=?", 
				new Object[] {qtyToUpdate, request.getInvProdMrp(), request.getInvProdRefillDt(),
						 itemInventoryDtls.getItemsInventoryDtlsId()});
		
		//Check if rows are upadted propertly
		if(updateNoOfRows != 1) {
			throw new ServicesException("Error Updating inventory details");
		}
		
		//Insert refill record
		jdbcTemplate.update("insert into inventory_refill_dtls(REFILL_DT,RECEIVED_QTY,ITEM_MASTER_DTLS_ID,MRP,COMMENTS,CMPNY_INFO_ID) values(?,?,?,?,?,?)", 
				new Object[] {request.getInvProdRefillDt(), request.getInvProdRefillQty(), request.getItemMasterDtlsId()
						, request.getInvProdMrp(), request.getInvRefillComments(),"56"});
	}
	
	@Override
	public CategoryDetailsTO selectCategoryDetailsById(int categoryId) {
		
		return jdbcTemplate.query("select * from category_master where ID=?", new Object[] {categoryId},
				new BeanPropertyRowMapper<CategoryDetailsTO>(CategoryDetailsTO.class)).get(0);
	}
	
	
	@Override
	public void updateProductDetailsForActivation(String id) {
		
		jdbcTemplate.update("update item_master_dtls set IS_ACTIVE='Active' where ITEM_MASTER_DTLS_ID=?", new Object[] {id});
	}
	
	@Override
	public ItemMasterDtl selectProductDetailsByItemId(String id) {
		
		return jdbcTemplate.query("select * from item_master_dtls where ITEM_MASTER_DTLS_ID=?", new Object[] {id}, 
				new BeanPropertyRowMapper<ItemMasterDtl>(ItemMasterDtl.class)).get(0);
	}
}	

