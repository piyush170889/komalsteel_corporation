package co.in.replete.komalindustries.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;

import co.in.replete.komalindustries.beans.CartDetailsTO;
import co.in.replete.komalindustries.beans.CartItemDetailsListTO;
import co.in.replete.komalindustries.beans.CartItemDtlsTO;
import co.in.replete.komalindustries.beans.ItemDetailsTO;
import co.in.replete.komalindustries.beans.ItemOrderDetailsTO;
import co.in.replete.komalindustries.beans.NewCartDetailsTO;
import co.in.replete.komalindustries.beans.UserDetailsTO;
import co.in.replete.komalindustries.beans.entity.AppConfiguration;
import co.in.replete.komalindustries.beans.entity.CartDlvryDtl;
import co.in.replete.komalindustries.beans.entity.CartDtl;
import co.in.replete.komalindustries.beans.entity.CartItemDtl;
import co.in.replete.komalindustries.beans.entity.InvoiceDtl;
import co.in.replete.komalindustries.beans.entity.ItemsInventoryDtl;
import co.in.replete.komalindustries.beans.entity.OfferDtl;
import co.in.replete.komalindustries.beans.entity.PaymentDtl;
import co.in.replete.komalindustries.beans.entity.ShippingAddressDetail;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.utils.CommonUtility;
import co.in.replete.komalindustries.utils.UDValues;
import co.in.replete.komalindustries.webcontroller.beans.WCartItemTO;
import co.in.replete.komalindustries.webcontroller.beans.WOrderDetailsTO;
import co.in.replete.komalindustries.webcontroller.beans.WUserCartDetails;
import co.in.replete.komalindustries.webcontroller.beans.WUserOrderInfo;

@Repository
public class CartDAOImpl extends BaseDAOImpl implements CartDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Properties sqlProperties; 
	
	@Autowired
	AdminDAO adminDAO;
	
	
	/**
	 * Description : Executes the select query and returns all the cart details as per the order status code specified
	 * @param	String	id of the user
	 * @param	String	status code of the order for which order details are to be selected
	 * @return {@link List<CartDetailsTO>}
	 * @throws {@link ServicesException}
	 * 
	 */
	@Override
	public List<CartDetailsTO> selectCartDetaisByOrderStatus(String userMasterId, String orderStatus) {
		List<CartDetailsTO> cartDetailsList = jdbcTemplate.query(sqlProperties.getProperty("select.cartdtl.bycartstatuscd"), new String[] {userMasterId, orderStatus}, new RowMapper<CartDetailsTO>(){
			
			@Override
			public CartDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CartDetailsTO cartDetails = new CartDetailsTO();
				cartDetails.setActualDlvryDt(rs.getString("actual_dlvry_dt"));
				cartDetails.setAddressTypeCd(rs.getString("address_type_cd"));
				cartDetails.setAlternateCntc(rs.getString("alternate_cntc"));
				cartDetails.setAmountBal(rs.getString("amount_bal"));
				cartDetails.setAmountPaid(rs.getString("amount_paid"));
				cartDetails.setCartDtlsId(rs.getString("cart_dtls_id"));
				cartDetails.setCartNotes(rs.getString("cart_notes"));
				cartDetails.setCartPrice(rs.getString("cart_price"));
				cartDetails.setCartStatus(rs.getString("cart_status_cd"));
				cartDetails.setCity(rs.getString("city"));
				cartDetails.setCountry(rs.getString("country_cd"));
				cartDetails.setDiscount(rs.getString("discount"));
				cartDetails.setDiscountValue(rs.getString("discount_value"));
				cartDetails.setDlvryByTrackId(rs.getString("dlvry_by_user_master_id"));
				cartDetails.setDlvryType(rs.getString("dlvry_type_cd"));
				cartDetails.setExpDlvryDt(rs.getString("exp_dlvry_dt"));
				cartDetails.setGrandTotal(rs.getString("grand_total"));
				cartDetails.setLatitude(rs.getString("latitude"));
				cartDetails.setLongitude(rs.getString("longitude"));
				cartDetails.setMiscCharges(rs.getString("misc_charges"));
				cartDetails.setIsOfferApld(rs.getString("is_offer_apld"));
				cartDetails.setPaymentAmount(rs.getString("payment_amount"));
				cartDetails.setPaymentGateway(rs.getString("payment_gateway_cd"));
				cartDetails.setPaymentStatus(rs.getString("payment_status_cd"));
				cartDetails.setPaymentTs(rs.getString("payment_ts"));
				cartDetails.setPaymentMode(rs.getString("payment_mode_cd"));
				cartDetails.setPostalCode(rs.getString("postal_code"));
				cartDetails.setServiceTax(rs.getString("service_tax"));
				cartDetails.setServiceTaxValue(rs.getString("service_tax_value"));
				cartDetails.setShippingCharges(rs.getString("shipping_charges"));
				cartDetails.setStAddress1(rs.getString("st_address_1"));
				cartDetails.setStAddress2(rs.getString("st_address_2"));
				cartDetails.setStAddress3(rs.getString("st_address_3"));
				cartDetails.setState(rs.getString("state_cd"));
				cartDetails.setSubTotal(rs.getString("sub_total"));
				cartDetails.setTxnData(rs.getString("txn_data"));
				cartDetails.setTxnRefNo(rs.getString("txn_ref_no"));
				cartDetails.setVat(rs.getString("vat"));
				cartDetails.setVatValue(rs.getString("vat_value"));
				
				return cartDetails;
			}
		});
		
		for(int i=0; i<cartDetailsList.size(); i++){
			List<CartItemDetailsListTO> cartItemsList = jdbcTemplate.query(sqlProperties.getProperty("select.cartitemdtls.bycartitemdtlsid"), 
					new String[]{cartDetailsList.get(i).getCartDtlsId()}, new RowMapper<CartItemDetailsListTO>(){
				@Override
				public CartItemDetailsListTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					CartItemDetailsListTO cartItemDetails = new CartItemDetailsListTO();
					cartItemDetails.setItemName(rs.getString("item_nm"));
					cartItemDetails.setItemPrice(rs.getString("item_price"));
					cartItemDetails.setItemQty(rs.getString("item_qty"));
					cartItemDetails.setItemMasterDtlsId(rs.getString("item_master_dtls_id"));
					return cartItemDetails;
				}
			});
			
			cartDetailsList.get(i).setCartItemsList(cartItemsList);
		}
		return cartDetailsList;
	}

	/**
	 * Description : Executes the insert query and inserts the address details 
	 * @param	{@link AddressDetailDTO}
	 * @return	int 	Generated Id of the inserted row
	 * @throws {@link ServicesException}
	 * 
	 */
	@Override
	public int insertAddress(final ShippingAddressDetail shippingAddressDetail) {
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sqlProperties.getProperty("insert.shippingaddress.all"), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, UDValues.ADDRESS_TYPE_SHIPPING.toString());
				ps.setString(2, shippingAddressDetail.getCity());
				ps.setString(3, shippingAddressDetail.getCountry());
				ps.setBigDecimal(4, shippingAddressDetail.getLatitude());
				ps.setBigDecimal(5, shippingAddressDetail.getLongitude());
				ps.setString(6, shippingAddressDetail.getPostalCode());
				ps.setString(7, shippingAddressDetail.getStAddress1());
				ps.setString(8, shippingAddressDetail.getStAddress2());
				ps.setString(9, shippingAddressDetail.getStAddress3());
				ps.setString(10, shippingAddressDetail.getState());
				ps.setString(11, shippingAddressDetail.getTrackId());
				ps.setString(12, shippingAddressDetail.getMark());
				ps.setString(13, shippingAddressDetail.getDestination());
				ps.setString(14, shippingAddressDetail.getTranNm());
				ps.setString(15, shippingAddressDetail.getTinNo());
				
				return ps;
			}
		};
		
		jdbcTemplate.update(psc, holder);
		return holder.getKey().intValue();
	}

	/**
	 * Description : Executes the insert query for cart delivery details and inserts the cart delivery details
	 * @return	int		Generated Id of the inserted row
	 * @throws 	{@link ServicesException}
	 * 
	 */
	@Override
	public int insertCartDeliveryDtls(CartDlvryDtl cartDelivryDtls) {
		KeyHolder holder = new GeneratedKeyHolder();
		
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sqlProperties.getProperty("insert.cartdeliverydetails.all"), Statement.RETURN_GENERATED_KEYS);
				
				ps.setInt(1, cartDelivryDtls.getShippingAddressId());
				try {
					ps.setDate(2, (null == cartDelivryDtls.getExpDlvryDt() || cartDelivryDtls.getExpDlvryDt().isEmpty()) ? null : (Date) CommonUtility.getParsedDate(cartDelivryDtls.getExpDlvryDt()));
				    ps.setDate(3 ,(null == cartDelivryDtls.getActualDlvryDt() || cartDelivryDtls.getActualDlvryDt().isEmpty()) ? null : (Date) CommonUtility.getParsedDate(cartDelivryDtls.getActualDlvryDt()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				ps.setString(4 , cartDelivryDtls.getDlvryByTrackId());
				ps.setString(5 , cartDelivryDtls.getDlvryType());
				ps.setString(6 , cartDelivryDtls.getAlternateCntc());
				
				return ps;
			}
		};
		
		jdbcTemplate.update(psc, holder);
		return holder.getKey().intValue();
	}

	/**
	 * Description : Executes the insert query for invoice details and inserts the invoice details
	 * @param	{@link InvoiceDtlDTO}
	 * @return 	int 	Generated Id of the inserted row
	 * @throws 	{@link ServicesException}
	 * 
	 */
	@Override
	public int insertInvoiceDetails(InvoiceDtl invoiceDetailsParams) {
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				PreparedStatement ps = con.prepareStatement(sqlProperties.getProperty("insert.invoicedetails.all"), Statement.RETURN_GENERATED_KEYS);
				ps.setFloat(1 , invoiceDetailsParams.getSubTotal());
				ps.setFloat(2 , invoiceDetailsParams.getDiscount());
				ps.setFloat(3 , invoiceDetailsParams.getDiscountValue());
				ps.setFloat(4 , invoiceDetailsParams.getServiceTax());
				ps.setFloat(5 , invoiceDetailsParams.getServiceTaxValue());
				ps.setFloat(6 , invoiceDetailsParams.getVat());
				ps.setFloat(7 , invoiceDetailsParams.getVatValue());
				ps.setFloat(8 , invoiceDetailsParams.getShippingCharges());
				ps.setFloat(9 , invoiceDetailsParams.getMiscCharges());
				ps.setFloat(10 , invoiceDetailsParams.getGrandTotal());
				ps.setFloat(11 , invoiceDetailsParams.getAmountPaid());
				ps.setFloat(12 , invoiceDetailsParams.getAmountBal());
				ps.setString(13, invoiceDetailsParams.getTrackId());
				
				return ps;
			}
		};
		
		jdbcTemplate.update(psc, holder);
		return holder.getKey().intValue();
	}

	/**
	 * Description : Executes the select query for offer details and get details of the offer associated with the offerDtlsId parameter
	 * @param	offerDtlsId					Id of the offer for which details are to be selected
	 * @return 	{@link OfferDtlDTO}
	 * @throws 	{@link ServicesException}
	 * 
	 */
	@Override
	public OfferDtl selectOfferDetails(String offerDtlsId) {
		return (OfferDtl) jdbcTemplate.query(sqlProperties.getProperty("select.offerdtl.byofferid"), new String[] {offerDtlsId}, new BeanPropertyRowMapper<OfferDtl>());
	}

	/**
	 * Description : Executes the insert query for payment details and inserts the payment details
	 * @param	{@link PaymentDtlDTO}
	 * @return 	String			Generated Id of the inserted row
	 * @throws 	{@link ServicesException}
	 * 
	 */
	@Override
	public String insertPaymentDetails(PaymentDtl paymentDtl) {
		
		String uuid = getUUID();
		
		jdbcTemplate.update(sqlProperties.getProperty("insert.paymentdtls.all"), uuid, paymentDtl.getTxnRefNo(), paymentDtl.getPaymentTs(), paymentDtl.getPaymentModeCd(), paymentDtl.getPaymentStatusCd(),
				paymentDtl.getPaymentGatewayCd(), paymentDtl.getBankRefNum(), paymentDtl.getTxnData(), paymentDtl.getPaymentAmount());
		
		return uuid;		
	}

	/**
	 * Description : Executes the insert query for cart details and inserts the cart details
	 * @param	{@link CartDtlDTO}
	 * @return 	int 	Generated Id of the inserted row
	 * @throws 	{@link ServicesException}
	 * 
	 */
	@Override
	public int insertCartDetails(CartDtl cartDetailParams) {
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sqlProperties.getProperty("insert.cartdetails.all"), Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, cartDetailParams.getCartDlvryDtlsId());
				ps.setFloat(2, cartDetailParams.getCartPrice());
				ps.setString(3, cartDetailParams.getTrackId());
				ps.setString(4, cartDetailParams.getCartNotes());
//				ps.setString(5, cartDetailParams.getCartStatus());
				ps.setString(5, UDValues.CART_STATUS_PENDING.toString());
				ps.setInt(6, cartDetailParams.getInvoiceDtlsId());
				ps.setString(7, cartDetailParams.getPaymentDtlsId());
				ps.setString(8, UDValues.BOOLEAN_FALSE.toString());
				ps.setInt(9, cartDetailParams.getOfferDtlId());
				ps.setInt(10, cartDetailParams.getCartDtlsId());
				
				return ps;
			}
		};
		
		jdbcTemplate.update(psc, holder);
		return holder.getKey().intValue();
	}

	/**
	 * Description : Executes the insert query for cart item details and inserts the cart item details
	 * @param {@link List<CartItemDtl>}		List of all the cart Item details to be inserted
	 * @return int[] 	array of generated Id's of the inserted row
	 * @throws {@link ServicesException}
	 * 
	 */
	@Override
	public int[] insertCartItemDetails(List<CartItemDtl> cartItemDtls) {

		return jdbcTemplate.batchUpdate(sqlProperties.getProperty("insert.cartitemdetails.all"), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				 
				CartItemDtl cartItemDtl = cartItemDtls.get(index);
				ps.setInt(1, cartItemDtl.getCartDtlsId());
				ps.setInt(2, cartItemDtl.getItemQty());
				ps.setInt(3, cartItemDtl.getItemMasterDtlsId());
				ps.setFloat(4, cartItemDtl.getItemPrice());
				ps.setString(5, cartItemDtl.getTrackId());
				ps.setInt(6, cartItemDtl.getOfferDtlsId());
				ps.setString(7, cartItemDtl.getIsOfferAppld());
			}
			
			@Override
			public int getBatchSize() {
				return cartItemDtls.size();
			}
		});
	}
	
	/**
	 * Description : Executes the select query for item details and selects all the item details as per the filter's provided
	 * @param	String		The sql query to be executed
	 * @param	String[]	the array of filter values to be applied to the sql query
	 * @return	{@link List<ItemDetailsTO>} 	List containing the search result with details of all the items that matced the search filter
	 * @throws 	{@link ServicesException}
	 * 
	 */
	@Override
	public List<ItemDetailsTO> selectItemsListByFilter(String sql, String[] filterValues) {
		List<ItemDetailsTO> itemDetailsList = jdbcTemplate.query(sql, filterValues, new RowMapper<ItemDetailsTO>(){
			@Override
			public ItemDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemDetailsTO itemDetailsTO = new ItemDetailsTO();
				itemDetailsTO.setAvlQty(rs.getBigDecimal("AVL_QTY").toString());
				itemDetailsTO.setBookedQty(rs.getBigDecimal("BOOKED_QTY").toString());
				itemDetailsTO.setItemCategoryCd(rs.getString("ITEM_CATEGORY"));
				itemDetailsTO.setItemContentInfo(rs.getString("ITEM_CONTENT_INFO"));
				itemDetailsTO.setItemDesc(rs.getString("ITEM_DESC"));
				itemDetailsTO.setItemManufacturer(rs.getString("ITEM_MANUFACTURER"));
				itemDetailsTO.setItemMasterDtlsId(Integer.toString(rs.getInt("ITEM_MASTER_DTLS_ID")));
				itemDetailsTO.setItemNm(rs.getString("ITEM_NM"));
				itemDetailsTO.setItemPckgInfo(rs.getString("ITEM_PCKG_INFO"));
				itemDetailsTO.setItemPckgTypeCd(rs.getString("ITEM_PCKG_TYPE"));
				itemDetailsTO.setItemSubcategoryCd(rs.getString("ITEM_SUB_CATEGORY"));
				itemDetailsTO.setMrp(rs.getBigDecimal("MRP").toString());
				itemDetailsTO.setOfferId(rs.getString("OFFER_DTLS_ID"));
				itemDetailsTO.setThrshldVal(rs.getBigDecimal("THRHLD_VAL").toString());
				return itemDetailsTO;
			}
		});
		return itemDetailsList;
	}

	/**
	 * Description : Returns the list of items that match the given keyword for category,sub-category and manufacturer
	 * @param	String	keyword to search the item details against
	 * @return	{@link List<ItemDetailsTO>}
	 * @throws 	{@link ServicesException}
	 */
	@Override
	public List<ItemDetailsTO> selectItemsDetails(String keyword) {
		
		List<ItemDetailsTO> itemsDetails = jdbcTemplate.query(sqlProperties.getProperty("select.itemdetails.bykeyword"), new String[] {"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"}, new RowMapper<ItemDetailsTO>(){
			@Override  
			public ItemDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemDetailsTO itemDetailsTO = new ItemDetailsTO();
				itemDetailsTO.setAvlQty(rs.getBigDecimal("AVL_QTY").toString());
				itemDetailsTO.setBookedQty(rs.getBigDecimal("BOOKED_QTY").toString());
				itemDetailsTO.setItemCategoryCd(rs.getString("ITEM_CATEGORY"));
				itemDetailsTO.setItemContentInfo(rs.getString("ITEM_CONTENT_INFO"));
				itemDetailsTO.setItemDesc(rs.getString("ITEM_DESC"));
				itemDetailsTO.setItemManufacturer(rs.getString("ITEM_MANUFACTURER"));
				itemDetailsTO.setItemMasterDtlsId(rs.getString("ITEM_MASTER_DTLS_ID"));
				itemDetailsTO.setItemNm(rs.getString("ITEM_NM"));
				itemDetailsTO.setItemPckgInfo(rs.getString("ITEM_PCKG_INFO"));
				itemDetailsTO.setItemPckgTypeCd(rs.getString("ITEM_PCKG_TYPE"));
				itemDetailsTO.setItemSubcategoryCd(rs.getString("ITEM_SUB_CATEGORY"));
				itemDetailsTO.setMrp(rs.getBigDecimal("MRP").toString());
				itemDetailsTO.setOfferId(rs.getString("OFFER_DTLS_ID"));
				itemDetailsTO.setThrshldVal(rs.getBigDecimal("THRHLD_VAL").toString());
				return itemDetailsTO;
			}
		});
		return itemsDetails;
	}

    /**
     * Description
     */
	@Override
	public List<NewCartDetailsTO> selectCartDetaisByPageNum(String trackId, int pageNum,int maxOrders) throws DataAccessException {
		
		int startIdx = (pageNum - 1) * maxOrders;
		return jdbcTemplate.query(sqlProperties.getProperty("select.cartdtls.orderlist"),new Object[]{trackId,startIdx,maxOrders},  new RowMapper<NewCartDetailsTO>() {
			@Override
			public NewCartDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				NewCartDetailsTO newCartDetailsTO = new NewCartDetailsTO();
				
				newCartDetailsTO.setCartDtlsId(rs.getInt("CART_DTLS_ID"));
				newCartDetailsTO.setExpDlvryDate(rs.getDate("EXP_DLVRY_DT"));
				newCartDetailsTO.setActualDlvryDate(rs.getDate("ACTUAL_DLVRY_DT"));
				newCartDetailsTO.setDlvryType(rs.getString("DLVRY_TYPE"));

				newCartDetailsTO.setFirstName(rs.getString("FIRST_NAME"));
				newCartDetailsTO.setLastName(rs.getString("LAST_NAME"));
				newCartDetailsTO.setCartPrice(rs.getDouble("CART_PRICE"));
				newCartDetailsTO.setCartNotes(rs.getString("CART_NOTES"));
				newCartDetailsTO.setCartStatus(rs.getString("CART_STATUS"));
				
				newCartDetailsTO.setAddressType(rs.getString("ADDRESS_TYPE"));
				newCartDetailsTO.setCity(rs.getString("CITY"));
				newCartDetailsTO.setCountry(rs.getString("COUNTRY"));
				newCartDetailsTO.setState(rs.getString("STATE"));
				newCartDetailsTO.setStAddress1(rs.getString("ST_ADDRESS_1"));
				newCartDetailsTO.setStAddress2(rs.getString("ST_ADDRESS_2"));
				newCartDetailsTO.setStAddress3(rs.getString("ST_ADDRESS_3"));
				newCartDetailsTO.setPostalCode(rs.getString("POSTAL_CODE"));
				newCartDetailsTO.setOrderDate(rs.getDate("CREATED_TS"));
				newCartDetailsTO.setLrNo((null == rs.getString("LR_NO")) ? "" : rs.getString("LR_NO"));
				newCartDetailsTO.setLrNoDate((null == rs.getString("LR_NO_DATE")) ? "" : rs.getString("LR_NO_DATE"));
				newCartDetailsTO.setNoOfCartonLoaded((null == rs.getString("NO_OF_CARTON_LOADED")) ? "" : rs.getString("NO_OF_CARTON_LOADED"));
				
				return newCartDetailsTO;
			}
		});
	}

	/**
	 * Description: select orders count
	 * @param trackId
	 * @return
	 * @throws Exception
	 */
	@Override
	public int selectOrderCount(String trackId) throws DataAccessException {
		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.cartdtls.ordercount"),Integer.class, trackId);
	}

	/**
	 * Description :
	 * @param  itemMasterDtlsId
	 * @return {@link ItemInventoryDtl}
	 */
	@Override
	public ItemsInventoryDtl selectItemInventoryDtl(String itemMasterDtlsId) {

		return jdbcTemplate.queryForObject(sqlProperties.getProperty("select.iteminventorydtls.all"), new Object[] {itemMasterDtlsId}, new BeanPropertyRowMapper<>(ItemsInventoryDtl.class));
	}
	
	/**
	 * Description : 
	 * @param avlQty
	 * @param bookedQty
	 * @param itemInventoryDetailsId
	 */
	@Override
	public void updateItemInventoryDetails(Float avlQty, Float bookedQty, int itemInventoryDetailsId) {
		
		jdbcTemplate.update(sqlProperties.getProperty("update.iteminventorydetails"), new Object[] {avlQty, bookedQty, itemInventoryDetailsId});
	}

	@Override
	public List<ItemOrderDetailsTO> selectItemDetails(String trackId) throws Exception {
		return jdbcTemplate.query(sqlProperties.getProperty("select.cartitemdtl.itemnamequantity"),new Object[]{trackId},  new RowMapper<ItemOrderDetailsTO>() {
			@Override
			public ItemOrderDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemOrderDetailsTO itemOrderDetailsTO = new ItemOrderDetailsTO();
				
				itemOrderDetailsTO.setItemName(rs.getString("ITEM_NM"));
				itemOrderDetailsTO.setItemQty(rs.getInt("ITEM_QTY"));
				
				return itemOrderDetailsTO;
			}
		});
	}
	
	@Override
	public UserDetailsTO selectUserDetails(String trackId) {
//		sqlProperties.getProperty("select.userdetails.bytrackid")
		return jdbcTemplate.query("select * from user_dtls ud inner join user_login_dtls uld on ud.TRACK_ID=uld.TRACK_ID and uld.TRACK_ID=?", new Object[] {trackId}, new ResultSetExtractor<UserDetailsTO>() {
			@Override
			public UserDetailsTO extractData(ResultSet rs) throws SQLException, DataAccessException {
				UserDetailsTO userDetails = null;
				
				rs.beforeFirst();
				if(rs.next()) {
					userDetails = new UserDetailsTO();
					userDetails.setFirstName(rs.getString("FIRST_NAME"));
					userDetails.setLastName(rs.getString("LAST_NAME"));
					userDetails.setCntc_num(rs.getString("CNTC_NUM"));
					userDetails.setLoginId(rs.getString("LOGIN_ID"));
					userDetails.setStatus(rs.getString("STATUS"));
					userDetails.setDisplayName(rs.getString("DISPLAY_NAME"));
					userDetails.setGstNo(rs.getString("GSTNO"));
					userDetails.setDiscount(rs.getFloat("DISCOUNT"));
				}
				
				return userDetails;
			}
		});
	}
	
	@Override
	public List<CartItemDetailsListTO> selectCartItemDtls(int cartDtlsId) {
		
		return jdbcTemplate.query(sqlProperties.getProperty("select.cartitemdtls.bycartdtlsid"), new Object[] {cartDtlsId}, 
				 new RowMapper<CartItemDetailsListTO>() {
			@Override
			public CartItemDetailsListTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CartItemDetailsListTO cartItemDetailsTO = new CartItemDetailsListTO();
				
				cartItemDetailsTO.setIsOfferApld(rs.getString("IS_OFFER_APPLD"));
				cartItemDetailsTO.setItemName(rs.getString("ITEM_NM"));
				cartItemDetailsTO.setItemMasterDtlsId(rs.getString("ITEM_MASTER_DTLS_ID"));
				cartItemDetailsTO.setItemPrice(rs.getString("ITEM_PRICE"));
				cartItemDetailsTO.setItemQty(rs.getString("ITEM_QTY"));
				cartItemDetailsTO.setOfferId(rs.getString("OFFER_DTLS_ID"));
				cartItemDetailsTO.setUom(rs.getString("UOM"));
				
				return cartItemDetailsTO;
			}
		});
	}
	
     /**Description: select wuser order info
      * 
      */
	@Override
	public List<WUserOrderInfo> selectUserOrderInfo(String id) {
		return jdbcTemplate.query(sqlProperties.getProperty("select.userorderdetails1.all"),new Object[]{id}, new RowMapper<WUserOrderInfo>() {
			@Override
			public WUserOrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				WUserOrderInfo wUserOrderInfo = new WUserOrderInfo();
				
				wUserOrderInfo.setOrderId(rs.getInt("CART_DTLS_ID"));
				wUserOrderInfo.setInvoiceId(rs.getInt("INVOICE_DTLS_ID"));
				wUserOrderInfo.setStatus(rs.getString("CART_STATUS"));
				wUserOrderInfo.setOrderDate(rs.getDate("CREATED_TS"));
				
				return wUserOrderInfo;
			}
		});
	}

	/**
	 * Description: select wusercartdetails
	 */
	@Override
	public List<WUserCartDetails> selectUserCartDetails(int invoiceId) {
		return jdbcTemplate.query(sqlProperties.getProperty("select.userinvoice.details"),new Object[]{invoiceId}, new RowMapper<WUserCartDetails>() {
			@Override
			public WUserCartDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
				WUserCartDetails wUserCartDetails = new WUserCartDetails();
				wUserCartDetails.setCartDlvyDtlId(rs.getInt("CART_DLVRY_DTLS_ID"));
				wUserCartDetails.setCartDtlsId(rs.getInt("CART_DTLS_ID"));
				wUserCartDetails.setCartPrice(rs.getDouble("CART_PRICE"));
				wUserCartDetails.setCartStatus(rs.getNString("CART_STATUS"));
				
				return wUserCartDetails;
			}
		});
	}

	@Override
	public List<WOrderDetailsTO> selectOrderDetails() {
		
		AppConfiguration appConfig=adminDAO.selectConfigurationValue(KomalIndustriesConstants.WMAX_LIMIT);
		int wmaxRecords=Integer.parseInt(appConfig.getConfigVal());
		
		return jdbcTemplate.query(sqlProperties.getProperty("select.orderdetails.all"),new Object[]{wmaxRecords}, new RowMapper<WOrderDetailsTO>() {
			@Override
			public WOrderDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				WOrderDetailsTO wOrderDetailsTO = new WOrderDetailsTO();
				
				wOrderDetailsTO.setTrackId(rs.getString("TRACK_ID"));
				wOrderDetailsTO.setCity(rs.getString("CITY"));
				wOrderDetailsTO.setCountry(rs.getString("COUNTRY"));
				wOrderDetailsTO.setCartDtlId(rs.getInt("CART_DTLS_ID"));
				wOrderDetailsTO.setDeliveryDate(rs.getDate("EXP_DLVRY_DT"));
				wOrderDetailsTO.setDeliveryType(rs.getString("DLVRY_TYPE"));
				wOrderDetailsTO.setFirstName(rs.getString("FIRST_NAME"));
				wOrderDetailsTO.setLastName(rs.getString("LAST_NAME"));
				wOrderDetailsTO.setNotes(rs.getString("CART_NOTES"));
				wOrderDetailsTO.setOrderDate(rs.getDate("CREATED_TS"));
				wOrderDetailsTO.setOrderPrice(rs.getString("CART_PRICE"));
				wOrderDetailsTO.setMark(rs.getString("MARK"));
				wOrderDetailsTO.setDestination(rs.getString("DESTINATION"));
				wOrderDetailsTO.setTransNm(rs.getString("TRAN_NM"));
				wOrderDetailsTO.setVatTinNo(rs.getString("TINNO"));
				wOrderDetailsTO.setPostalCode(rs.getString("POSTAL_CODE"));
				wOrderDetailsTO.setState(rs.getString("STATE"));
				wOrderDetailsTO.setStatus(rs.getString("CART_STATUS"));
				wOrderDetailsTO.setStreet2(rs.getString("ST_ADDRESS_2"));
				wOrderDetailsTO.setStreet1(rs.getString("ST_ADDRESS_1"));
				wOrderDetailsTO.setStreet3(rs.getString("ST_ADDRESS_3"));
				wOrderDetailsTO.setInvoiceId(rs.getInt("INVOICE_DTLS_ID"));
				wOrderDetailsTO.setCnctNum(rs.getString("ALTERNATE_CNTC"));
				wOrderDetailsTO.setLrNo(rs.getString("LR_NO"));
				wOrderDetailsTO.setLrNoDate(rs.getDate("LR_NO_DATE"));
				wOrderDetailsTO.setNoOfCartonLoaded(rs.getString("NO_OF_CARTON_LOADED"));
				wOrderDetailsTO.setIsLrMssgSent(rs.getInt("IS_LR_MSSG_SENT"));
				wOrderDetailsTO.setCourierNm(rs.getString("COURIER_NM"));
				wOrderDetailsTO.setDocateNo(rs.getString("DOCATE_NO"));
				wOrderDetailsTO.setIsCourierMssgSent(rs.getInt("IS_COURIER_MSSG_SENT"));
				
				return wOrderDetailsTO;
			}
		});
	}

	@Override
	public List<WCartItemTO> selectCartItemDetails(int orderId) {
		return jdbcTemplate.query(sqlProperties.getProperty("select.orderitem.details"),new Object[]{orderId}, new RowMapper<WCartItemTO>() {
			@Override
			public WCartItemTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				WCartItemTO wCartItemTO = new WCartItemTO();
				wCartItemTO.setItemName(rs.getString("ITEM_NM"));
				wCartItemTO.setQuantity(rs.getInt("ITEM_QTY"));
				/*wCartItemTO.setPrice(rs.getDouble("ITEM_PRICE"));*/
				wCartItemTO.setCartItemDtlsId(rs.getInt("CART_ITEM_DTLS_ID"));
//				wCartItemTO.setItemPerCartonPrice(rs.getFloat("MASTER_CARTON_PRICE"));
				
				return wCartItemTO;
			}
		});
	}
	
	@Override
	public List<CartItemDtlsTO> selectOrderItemDetailsByCartId(int orderDetailsId) {
		
		return jdbcTemplate.query("select imd.ITEM_NM,imd.ITEM_MASTER_DTLS_ID,cid.ITEM_QTY,cid.ITEM_PRICE,imd.UOM from item_master_dtls as imd inner join cart_item_dtls as cid on "
				+ "imd.ITEM_MASTER_DTLS_ID=cid.ITEM_MASTER_DTLS_ID and cid.CART_DTLS_ID=?",
				 new Object[] {orderDetailsId}, new BeanPropertyRowMapper<CartItemDtlsTO>(CartItemDtlsTO.class));
	}
	
	@Override
	public List<WOrderDetailsTO> searchOrders(String searchBy, String searchDateRange) throws DataAccessException, ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("mm/dd/yyyy");
//		System.out.println("DATE RANGE" + searchDateRange);
		String[] dateRange = searchDateRange.split("-");
//		System.out.println("1 : " + dateRange[0] + " 2 : " + dateRange[1]);
		String startDate = sdf1.format(sdf2.parse(dateRange[0]));
		String endDate = sdf1.format(sdf2.parse(dateRange[1]));
//		System.out.println("STart Date : " + startDate + " End Date : "+ endDate);
		String sql = "";
		Object[] params;
		if(searchBy.equals("All")) {
			/*sql = "select ud.TRACK_ID,oad.CITY,oad.COUNTRY,cd.CART_DTLS_ID,cdd.EXP_DLVRY_DT,cdd.DLVRY_TYPE,ud.FIRST_NAME,ud.LAST_NAME,cd.CART_NOTES,cd.CREATED_TS,"
				+ "cd.CART_PRICE,pd.PAYMENT_MODE,pd.PAYMENT_STATUS,oad.POSTAL_CODE,oad.STATE,cd.CART_STATUS,oad.ST_ADDRESS_2,oad.ST_ADDRESS_1,oad.ST_ADDRESS_3,cd.INVOICE_DTLS_ID,"
				+ "ud.CNTC_NUM,cd.LR_NO,cd.LR_NO_DATE,cd.NO_OF_CARTON_LOADED from cart_dtls as cd inner join cart_dlvry_dtls as cdd on cd.CART_DLVRY_DTLS_ID=cdd.CART_DLVRY_DTLS_ID inner join other_address_details as oad "
				+ "on cdd.SHIPPING_ADDRESS_ID=oad.OTHER_ADDRESS_ID inner join payment_dtls as pd on cd.PAYMENT_DTLS_ID=pd.PAYMENT_DTLS_ID inner join user_dtls as ud "
				+ "on cd.TRACK_ID=ud.TRACK_ID and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%Y')>=? and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%Y')<=? limit ?";*/
			sql = "select ud.TRACK_ID,oad.CITY,oad.COUNTRY,cd.CART_DTLS_ID,cdd.EXP_DLVRY_DT,cdd.DLVRY_TYPE,cdd.COURIER_NM,cdd.DOCATE_NO,ud.FIRST_NAME,ud.LAST_NAME,cd.CART_NOTES,cd.CREATED_TS,"
					+ "cd.CART_PRICE,oad.POSTAL_CODE,oad.STATE,cd.CART_STATUS,oad.ST_ADDRESS_2,oad.ST_ADDRESS_1,oad.ST_ADDRESS_3,cd.INVOICE_DTLS_ID,"
					+ "oad.MARK,oad.DESTINATION,oad.TRAN_NM,oad.TINNO,cdd.IS_COURIER_MSSG_SENT,"
					+ "ud.CNTC_NUM,cd.LR_NO,cd.LR_NO_DATE,cd.NO_OF_CARTON_LOADED,cd.IS_LR_MSSG_SENT from cart_dtls as cd inner join cart_dlvry_dtls as cdd on cd.CART_DLVRY_DTLS_ID=cdd.CART_DLVRY_DTLS_ID inner join other_address_details as oad "
					+ "on cdd.SHIPPING_ADDRESS_ID=oad.OTHER_ADDRESS_ID inner join user_dtls as ud "
					+ "on cd.TRACK_ID=ud.TRACK_ID inner join user_login_dtls as uld on ud.TRACK_ID=uld.TRACK_ID and uld.STATUS='Active' and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%Y')>=? and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%Y')<=? "
					+ " order by cd.CART_DTLS_ID limit ?";
			//TODO Get max records to be shown from db
			params = new Object[] {startDate, endDate, 200};
		} else {
			/*sql = "select ud.TRACK_ID,oad.CITY,oad.COUNTRY,cd.CART_DTLS_ID,cdd.EXP_DLVRY_DT,cdd.DLVRY_TYPE,ud.FIRST_NAME,ud.LAST_NAME,cd.CART_NOTES,cd.CREATED_TS,"
				+ "cd.CART_PRICE,pd.PAYMENT_MODE,pd.PAYMENT_STATUS,oad.POSTAL_CODE,oad.STATE,cd.CART_STATUS,oad.ST_ADDRESS_2,oad.ST_ADDRESS_1,oad.ST_ADDRESS_3,cd.INVOICE_DTLS_ID,"
				+ "ud.CNTC_NUM,cd.LR_NO,cd.LR_NO_DATE,cd.NO_OF_CARTON_LOADED from cart_dtls as cd inner join cart_dlvry_dtls as cdd on cd.CART_DLVRY_DTLS_ID=cdd.CART_DLVRY_DTLS_ID inner join other_address_details as oad "
				+ "on cdd.SHIPPING_ADDRESS_ID=oad.OTHER_ADDRESS_ID inner join payment_dtls as pd on cd.PAYMENT_DTLS_ID=pd.PAYMENT_DTLS_ID inner join user_dtls as ud "
				+ "on cd.TRACK_ID=ud.TRACK_ID and cd.CART_STATUS=? and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%Y')>=? and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%Y')<=? limit ?";*/
			sql = "select ud.TRACK_ID,oad.CITY,oad.COUNTRY,cd.CART_DTLS_ID,cdd.EXP_DLVRY_DT,cdd.DLVRY_TYPE,cdd.COURIER_NM,cdd.DOCATE_NO,ud.FIRST_NAME,ud.LAST_NAME,cd.CART_NOTES,cd.CREATED_TS,"
					+ "cd.CART_PRICE,oad.POSTAL_CODE,oad.STATE,cd.CART_STATUS,oad.ST_ADDRESS_2,oad.ST_ADDRESS_1,oad.ST_ADDRESS_3,cd.INVOICE_DTLS_ID,"
					+ "oad.MARK,oad.DESTINATION,oad.TRAN_NM,oad.TINNO,cdd.IS_COURIER_MSSG_SENT,"
					+ "ud.CNTC_NUM,cd.LR_NO,cd.LR_NO_DATE,cd.NO_OF_CARTON_LOADED,cd.IS_LR_MSSG_SENT from cart_dtls as cd inner join cart_dlvry_dtls as cdd on cd.CART_DLVRY_DTLS_ID=cdd.CART_DLVRY_DTLS_ID inner join other_address_details as oad "
					+ "on cdd.SHIPPING_ADDRESS_ID=oad.OTHER_ADDRESS_ID inner join user_dtls as ud "
					+ "on cd.TRACK_ID=ud.TRACK_ID "
					+ " inner join user_login_dtls as uld on ud.TRACK_ID=uld.TRACK_ID and uld.STATUS='Active' "
					+ "and cd.CART_STATUS=? and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%Y')>=? and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%Y')<=? "
					+ " order by cd.CART_DTLS_ID limit ?";
			//TODO Get max records to be shown from db
			params = new Object[] {searchBy, startDate, endDate, 200};
		}
		
//		System.out.println(sql);
		return jdbcTemplate.query(sql, params, new RowMapper<WOrderDetailsTO>() {
					@Override
					public WOrderDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						WOrderDetailsTO wOrderDetailsTO = new WOrderDetailsTO();
						
						wOrderDetailsTO.setTrackId(rs.getString("TRACK_ID"));
						wOrderDetailsTO.setCity(rs.getString("CITY"));
						wOrderDetailsTO.setCountry(rs.getString("COUNTRY"));
						wOrderDetailsTO.setCartDtlId(rs.getInt("CART_DTLS_ID"));
						wOrderDetailsTO.setDeliveryDate(rs.getDate("EXP_DLVRY_DT"));
						wOrderDetailsTO.setDeliveryType(rs.getString("DLVRY_TYPE"));
						wOrderDetailsTO.setFirstName(rs.getString("FIRST_NAME"));
						wOrderDetailsTO.setLastName(rs.getString("LAST_NAME"));
						wOrderDetailsTO.setNotes(rs.getString("CART_NOTES"));
						wOrderDetailsTO.setOrderDate(rs.getDate("CREATED_TS"));
						wOrderDetailsTO.setOrderPrice(rs.getString("CART_PRICE"));
						wOrderDetailsTO.setMark(rs.getString("MARK"));
						wOrderDetailsTO.setMark(rs.getString("DESTINATION"));
						wOrderDetailsTO.setMark(rs.getString("TRAN_NM"));
						wOrderDetailsTO.setVatTinNo(rs.getString("TINNO"));
						wOrderDetailsTO.setPostalCode(rs.getString("POSTAL_CODE"));
						wOrderDetailsTO.setState(rs.getString("STATE"));
						wOrderDetailsTO.setStatus(rs.getString("CART_STATUS"));
						wOrderDetailsTO.setStreet2(rs.getString("ST_ADDRESS_2"));
						wOrderDetailsTO.setStreet1(rs.getString("ST_ADDRESS_1"));
						wOrderDetailsTO.setStreet3(rs.getString("ST_ADDRESS_3"));
						wOrderDetailsTO.setInvoiceId(rs.getInt("INVOICE_DTLS_ID"));
						wOrderDetailsTO.setCnctNum(rs.getString("CNTC_NUM"));
						wOrderDetailsTO.setLrNo(rs.getString("LR_NO"));
						wOrderDetailsTO.setLrNoDate(rs.getDate("LR_NO_DATE"));
						wOrderDetailsTO.setNoOfCartonLoaded(rs.getString("NO_OF_CARTON_LOADED"));
						wOrderDetailsTO.setIsLrMssgSent(rs.getInt("IS_LR_MSSG_SENT"));
						wOrderDetailsTO.setCourierNm(rs.getString("COURIER_NM"));
						wOrderDetailsTO.setDocateNo(rs.getString("DOCATE_NO"));
						wOrderDetailsTO.setIsCourierMssgSent(rs.getInt("IS_COURIER_MSSG_SENT"));
						
						return wOrderDetailsTO;
					}
				});
		
	}
	
	@Override
	public UserDetailsTO selectUserUserDetailsByTrackId(String trackId) {
		
//		System.out.println("TRACK ID : " + trackId);
		List<UserDetailsTO> userDetailsList = jdbcTemplate.query("select * from user_dtls as ud inner join user_login_dtls as uld on ud.TRACK_ID=uld.TRACK_ID and ud.TRACK_ID=?", new Object[] {trackId}, 
				new RowMapper<UserDetailsTO>(){
			@Override
			public UserDetailsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserDetailsTO userDetailsTO = new UserDetailsTO();
				userDetailsTO.setFirstName(rs.getString("FIRST_NAME"));
				userDetailsTO.setLastName(rs.getString("LAST_NAME"));
				userDetailsTO.setUserTrackId(rs.getString("TRACK_ID"));
				userDetailsTO.setStatus(rs.getString("STATUS"));
				userDetailsTO.setDisplayName(rs.getString("DISPLAY_NAME"));
				
				return userDetailsTO;
			}
		});
		
		if(userDetailsList.size() != 1) {
			return null;
		} else {
			return userDetailsList.get(0);
		}
	}
	
	@Override
	public ShippingAddressDetail selectShippingAddressDetailsById(int addressDtlsId) {
		
		String sql = "select * from other_address_details where OTHER_ADDRESS_ID=?";
		return jdbcTemplate.query(sql, new Object[] {addressDtlsId}, 
				new BeanPropertyRowMapper<ShippingAddressDetail>(ShippingAddressDetail.class)).get(0);
	}
	
	@Override
	public int selectLatestOrderId() {
		
		String sql = "select MAX(CART_DTLS_ID) from cart_dtls";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
}
