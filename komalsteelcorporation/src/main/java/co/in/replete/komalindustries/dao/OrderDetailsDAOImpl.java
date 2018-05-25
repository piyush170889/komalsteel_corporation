package co.in.replete.komalindustries.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import co.in.replete.komalindustries.beans.AddItemsToCartTO;
import co.in.replete.komalindustries.beans.CartAndCartItemDetailTO;
import co.in.replete.komalindustries.beans.OrderEditTO;
import co.in.replete.komalindustries.beans.entity.AddressDetail;
import co.in.replete.komalindustries.beans.entity.CartDlvryDtl;
import co.in.replete.komalindustries.beans.entity.CartDtl;
import co.in.replete.komalindustries.beans.entity.CartItemDtl;
import co.in.replete.komalindustries.beans.entity.InvoiceDtl;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.ItemsInventoryDtl;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.utils.UDValues;
import co.in.replete.komalindustries.webcontroller.beans.EditCartItemDtlsTO;
import co.in.replete.komalindustries.webcontroller.beans.WOrderDetailsTO;

@Repository
public class OrderDetailsDAOImpl implements OrderDetailsDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Properties sqlProperties; 
	
	@Override
	public void updateOrderDetails(OrderEditTO request) throws DataAccessException {
		int cartDtlsId = request.getOrderId();
		String dlvryDate = null;
		if(null == request.getDlvryDate() || request.getDlvryDate().isEmpty() || request.getDlvryDate().equalsIgnoreCase("null")) {
			dlvryDate = null;
		}else {
			dlvryDate = request.getDlvryDate();
		}
		
		//Update cart Details
		/*jdbcTemplate.update("update cart_dtls set CART_STATUS=?,CART_PRICE=? where CART_DTLS_ID=?", new Object[] {request.getOrderStatus(), request.getOrderprice(),
				cartDtlsId});*/
		
		jdbcTemplate.update("update cart_dtls set CART_STATUS=? where CART_DTLS_ID=?", new Object[] {request.getOrderStatus(), /*request.getOrderprice(),*/
				cartDtlsId});
		
		//Get cart Details
		CartDtl cartDetails = jdbcTemplate.query("select CART_DLVRY_DTLS_ID,INVOICE_DTLS_ID,PAYMENT_DTLS_ID from cart_dtls where CART_DTLS_ID=?", new Object[] {cartDtlsId},
				new BeanPropertyRowMapper<CartDtl>(CartDtl.class)).get(0);
		
		//Update Delivery Details
		jdbcTemplate.update("update cart_dlvry_dtls set EXP_DLVRY_DT=?,ALTERNATE_CNTC=? where CART_DLVRY_DTLS_ID=?", new Object[] {dlvryDate, request.getAlternateContact() ,cartDetails.getCartDlvryDtlsId()});
		
		/*//Update Payment details
		jdbcTemplate.update("update payment_dtls set PAYMENT_MODE=?,PAYMENT_STATUS=? where PAYMENT_DTLS_ID=?", new Object[] {request.getPaymode(), 
				request.getPaymentstatus(), cartDetails.getPaymentDtlsId()});
		*/
		// Update Shipping Address 
		int addressDtlsId = jdbcTemplate.queryForObject("select SHIPPING_ADDRESS_ID from cart_dlvry_dtls where CART_DLVRY_DTLS_ID=?", new Object[] {cartDetails.getCartDlvryDtlsId()},
				Integer.class);
		jdbcTemplate.update("update other_address_details set CITY=?,STATE=?,ST_ADDRESS_1=? where OTHER_ADDRESS_ID=?", new Object[] {request.getCity(), request.getState(),
				request.getDlvryAddr(), addressDtlsId});
	}
	
	@Override
	public List<WOrderDetailsTO> searchOrders(String searchBy, String searchDateRange) throws DataAccessException {
		
		String[] dateRange = searchDateRange.split("-");
		String startDate = dateRange[0];
		String endDate = dateRange[1];
		
		String sql = "";
		Object[] params;
		if(searchBy.equals("All")) {
			sql = "select ud.TRACK_ID,oad.CITY,oad.COUNTRY,cd.CART_DTLS_ID,cdd.EXP_DLVRY_DT,cdd.DLVRY_TYPE,ud.FIRST_NAME,ud.LAST_NAME,cd.CART_NOTES,cd.CREATED_TS,"
				+ "cd.CART_PRICE,pd.PAYMENT_MODE,pd.PAYMENT_STATUS,oad.POSTAL_CODE,oad.STATE,cd.CART_STATUS,oad.ST_ADDRESS_2,oad.ST_ADDRESS_1,oad.ST_ADDRESS_3,cd.INVOICE_DTLS_ID,"
				+ "ud.CNTC_NUM from cart_dtls as cd inner join cart_dlvry_dtls as cdd on cd.CART_DLVRY_DTLS_ID=cdd.CART_DLVRY_DTLS_ID inner join other_address_details as oad "
				+ "on cdd.SHIPPING_ADDRESS_ID=oad.OTHER_ADDRESS_ID inner join payment_dtls as pd on cd.PAYMENT_DTLS_ID=pd.PAYMENT_DTLS_ID inner join user_dtls as ud "
				+ "on cd.TRACK_ID=ud.TRACK_ID and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%y')>=? and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%y')<=? limit ?";
			params = new Object[] {startDate, endDate, 10};
		} else {
			sql = "select ud.TRACK_ID,oad.CITY,oad.COUNTRY,cd.CART_DTLS_ID,cdd.EXP_DLVRY_DT,cdd.DLVRY_TYPE,ud.FIRST_NAME,ud.LAST_NAME,cd.CART_NOTES,cd.CREATED_TS,"
				+ "cd.CART_PRICE,pd.PAYMENT_MODE,pd.PAYMENT_STATUS,oad.POSTAL_CODE,oad.STATE,cd.CART_STATUS,oad.ST_ADDRESS_2,oad.ST_ADDRESS_1,oad.ST_ADDRESS_3,cd.INVOICE_DTLS_ID,"
				+ "ud.CNTC_NUM from cart_dtls as cd inner join cart_dlvry_dtls as cdd on cd.CART_DLVRY_DTLS_ID=cdd.CART_DLVRY_DTLS_ID inner join other_address_details as oad "
				+ "on cdd.SHIPPING_ADDRESS_ID=oad.OTHER_ADDRESS_ID inner join payment_dtls as pd on cd.PAYMENT_DTLS_ID=pd.PAYMENT_DTLS_ID inner join user_dtls as ud "
				+ "on cd.TRACK_ID=ud.TRACK_ID and cd.CART_STATUS=? and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%y')>=? and DATE_FORMAT(cd.CREATED_TS,'%d-%m-%y')<=? limit ?";
			params = new Object[] {searchBy, startDate, endDate, 10};
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
						wOrderDetailsTO.setPaymentMode(rs.getString("PAYMENT_MODE"));
						wOrderDetailsTO.setPaymentStatus(rs.getString("PAYMENT_STATUS"));
						wOrderDetailsTO.setPostalCode(rs.getString("POSTAL_CODE"));
						wOrderDetailsTO.setState(rs.getString("STATE"));
						wOrderDetailsTO.setStatus(rs.getString("CART_STATUS"));
						wOrderDetailsTO.setStreet2(rs.getString("ST_ADDRESS_2"));
						wOrderDetailsTO.setStreet1(rs.getString("ST_ADDRESS_1"));
						wOrderDetailsTO.setStreet3(rs.getString("ST_ADDRESS_3"));
						wOrderDetailsTO.setInvoiceId(rs.getInt("INVOICE_DTLS_ID"));
						wOrderDetailsTO.setCnctNum(rs.getString("CNTC_NUM"));
					
						return wOrderDetailsTO;
					}
				});
		
	}
	
	@Override
	public void updateLrNo(String cartDtldId, String lrNo, String lrDate, String noofcarton) {
		
		jdbcTemplate.update("update cart_dtls set LR_NO=?,LR_NO_DATE=?,NO_OF_CARTON_LOADED=? where CART_DTLS_ID=?", new Object[] {lrNo, lrDate, noofcarton, cartDtldId});
	}
	
	@Override
	public CartDtl selectOrderDetailsById(int orderId) {
		
		return jdbcTemplate.query("select * from cart_dtls where CART_DTLS_ID=?", new Object[] {orderId}, new BeanPropertyRowMapper<CartDtl>(CartDtl.class)).get(0);
	}
	
	@Override
	public String getContactNumberFromTrackId(String trackId) {
		
		return jdbcTemplate.queryForObject("select CNTC_NUM from user_dtls where TRACK_ID=?", new Object[] {trackId}, String.class);
	}
	
	@Override
	public void insertCartItemForAnOrder(AddItemsToCartTO request) {
		
		//Get track id from order details Id
		String trackId = jdbcTemplate.queryForObject("select TRACK_ID from cart_dtls where CART_DTLS_ID=?", new Object[] {request.getOrderDtlsId()}, 
				String.class);
		
		//Insert into cart item dtls 
		/*jdbcTemplate.update("insert into cart_item_dtls(CART_DTLS_ID,ITEM_QTY,ITEM_MASTER_DTLS_ID,ITEM_PRICE,TRACK_ID) values(?,?,?,?,?)", 
				new Object[] {request.getOrderDtlsId(), request.getCartonQty(), request.getItemMasterDtlsId(), request.getCartonPrice(), trackId});*/
		jdbcTemplate.update("insert into cart_item_dtls(CART_DTLS_ID,ITEM_QTY,ITEM_MASTER_DTLS_ID,TRACK_ID) values(?,?,?,?)", 
				new Object[] {request.getOrderDtlsId(), request.getCartonQty(), request.getItemMasterDtlsId(), trackId});
	}
	
	@Override
	public void deleteItemFromOrderCart(int cartItemDtlsId) {
		
		jdbcTemplate.update("delete from cart_item_dtls where CART_ITEM_DTLS_ID=?", new Object[] {cartItemDtlsId});
	}
	
	@Override
	public void updateCartItemDtls(EditCartItemDtlsTO request) {
		
		jdbcTemplate.update("update cart_item_dtls set ITEM_QTY=?,ITEM_PRICE=? where CART_ITEM_DTLS_ID=?", new Object[] {request.getItemQty(),
				request.getItemPrice(), request.getCartItemDtlsId()});
	}
	
	public CartItemDtl selectCartItemDtlsById(int cartItemDtlsId) {
		 return jdbcTemplate.query("select * from cart_item_dtls where CART_ITEM_DTLS_ID=?", new Object[] {cartItemDtlsId}, 
				 new BeanPropertyRowMapper<CartItemDtl>(CartItemDtl.class)).get(0);
	};
	
	@Override
	public void updateOrderPrice(int orderDtlsId, Float priceToUpdate, final String updateType) throws ServicesException {
		Float finalPriceToUpdate = 0F;
		
		//TODO 1) Check if the order is completed or not. If completed do not update the values else do the updation
		//Select cart details
		CartDtl cartDtl = jdbcTemplate.query("select * from cart_dtls where CART_DTLS_ID=?", new Object[] {orderDtlsId},
				new BeanPropertyRowMapper<CartDtl>(CartDtl.class)).get(0);
		
		//Apply taxes to get final price
		finalPriceToUpdate = getFinalPriceByAddingTaxesAndUpadteInvoiceDetails(priceToUpdate, cartDtl.getInvoiceDtlsId(), updateType);
		
		//Update cart price in cart details
		jdbcTemplate.update("update cart_dtls set CART_PRICE=? where CART_DTLS_ID=?", new Object[] {finalPriceToUpdate, orderDtlsId});
		
		//Update the final price in payment details
		jdbcTemplate.update("update payment_dtls set PAYMENT_AMOUNT=? where PAYMENT_DTLS_ID=?", new Object[] {finalPriceToUpdate, cartDtl.getPaymentDtlsId()});
	}

	private Float getFinalPriceByAddingTaxesAndUpadteInvoiceDetails(Float priceToUpdate, int invoiceDtlsId, String updateType) throws ServicesException {
		
		//Select sub total from Invoice details
		InvoiceDtl invoiceDtl = jdbcTemplate.query("select * from invoice_dtls where INVOICE_DTLS_ID=?", new Object[] {invoiceDtlsId}, 
				new BeanPropertyRowMapper<InvoiceDtl>(InvoiceDtl.class)).get(0);
		
		Float subTotal = invoiceDtl.getSubTotal();
		
		//Get Sub Total by depending on the type of update
		Float subTotalAfterUpdatingPrice = 0F;
		if(updateType.equalsIgnoreCase(KomalIndustriesConstants.UPDATE_TYPE_ADD_ORDER_PRICE)) {
			subTotalAfterUpdatingPrice = subTotal + priceToUpdate;
		} else if (updateType.equalsIgnoreCase(KomalIndustriesConstants.UPDATE_TYPE_MINUS_ORDER_PRICE)) {
			subTotalAfterUpdatingPrice = subTotal - priceToUpdate;
		} else if (updateType.equalsIgnoreCase(KomalIndustriesConstants.UPDATE_TYPE_DISCOUNT)) {
			//Update discount value and grand total
			Float finalDiscountVal = invoiceDtl.getDiscountValue() + priceToUpdate;
			Float finalGrandTotal = invoiceDtl.getGrandTotal() - priceToUpdate;
			jdbcTemplate.update("update invoice_dtls set DISCOUNT_VALUE=?,GRAND_TOTAL=?,AMOUNT_BAL=? where INVOICE_DTLS_ID=?",
					new Object[] {finalDiscountVal, finalGrandTotal, finalGrandTotal, invoiceDtlsId} );
			
			//Return the grand total
			return finalGrandTotal;
		} else {
			throw new ServicesException("Unknown update type specified");
		}
		
		//Select Service tax and get service tax value to add
		String serviceTax = jdbcTemplate.queryForObject("select CONFIG_VAL from configuration where CONFIG_NAME=?", 
				new Object[] {UDValues.CONFIG_VAL_SERVICE_TAX.toString()}, String.class);
		
		//Select Vat and get vat value to add
		String vat = jdbcTemplate.queryForObject("select CONFIG_VAL from configuration where CONFIG_NAME=?", 
				new Object[] {UDValues.CONFIG_VAL_VAT.toString()}, String.class);
		
		//Apply Service tax and get service tax value
		Float serviceTaxValueToAdd = (subTotalAfterUpdatingPrice * Float.parseFloat(serviceTax))/100;
		
		//Get vat value and Apply Vat 
		Float vatValueToAdd = (subTotalAfterUpdatingPrice * Float.parseFloat(vat))/100;
		
		//Get Final Price
		Float grandTotal = subTotalAfterUpdatingPrice + serviceTaxValueToAdd + vatValueToAdd;
		
		//Update the final price in invoice details
		jdbcTemplate.update("update invoice_dtls set SUB_TOTAL=?,SERVICE_TAX=?,SERVICE_TAX_VALUE=?,VAT=?,VAT_VALUE=?,AMOUNT_BAL=?,GRAND_TOTAL=? where INVOICE_DTLS_ID=?", 
				new Object[] {subTotalAfterUpdatingPrice, serviceTax, serviceTaxValueToAdd, vat, vatValueToAdd, grandTotal
						, grandTotal, invoiceDtlsId});
		
		//Return finalPrice
		return grandTotal;
	}
	
	@Override
	public CartAndCartItemDetailTO selectCartAndCartItemDetailsByCartItemDtlsId(int cartItemDtlsId) {
//		System.out.println(cartItemDtlsId);
		return jdbcTemplate.query("select CART_DTLS_ID,MASTER_CARTON_PRICE from cart_item_dtls as cid inner join "
				+ "item_master_dtls as imd on cid.ITEM_MASTER_DTLS_ID=imd.ITEM_MASTER_DTLS_ID and cid.CART_ITEM_DTLS_ID=?", 
				new Object[] {cartItemDtlsId}, new RowMapper<CartAndCartItemDetailTO>() {
					@Override
					public CartAndCartItemDetailTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						CartAndCartItemDetailTO cartAndCartItemDetailTO = new CartAndCartItemDetailTO();
						cartAndCartItemDetailTO.setCartDtlsId(rs.getInt("CART_DTLS_ID"));
						cartAndCartItemDetailTO.setMasterCartonPrice(rs.getFloat("MASTER_CARTON_PRICE"));
						
						return cartAndCartItemDetailTO;
					}
				}).get(0);
	}
	
	@Override
	public List<CartItemDtl> selectCartItemsByCartDtlsId(int orderId) {
		
		return jdbcTemplate.query("select * from cart_item_dtls where CART_DTLS_ID=?", new Object[] {orderId}, new BeanPropertyRowMapper<CartItemDtl>(CartItemDtl.class));
	}
	
	@Override
	public ItemsInventoryDtl selectItemInventoryDetailsByItemMasterId(int itemMasterDtlsId) {
		
		return jdbcTemplate.query("select * from items_inventory_dtls where ITEMS_INVENTORY_DTLS_ID=?", new Object[] {itemMasterDtlsId}, new BeanPropertyRowMapper<ItemsInventoryDtl>(ItemsInventoryDtl.class)).get(0);
	}
	
	@Override
	public void updateInventoryDetailsByItemInventoryDtlsId(Float finalAvailableQty, Float finalBookedQty,
			int itemsInventoryDtlsId) {
		
		jdbcTemplate.update("update items_inventory_dtls set AVL_QTY=?,BOOKED_QTY=? where ITEMS_INVENTORY_DTLS_ID=?", new Object[] {finalAvailableQty
				, finalBookedQty, itemsInventoryDtlsId});
	}
	
	@Override
	public CartDlvryDtl selectCartDeliveryDetailsById(int cartDlvrDtlsId) {
		
		return jdbcTemplate.query("select * from cart_dlvry_dtls where CART_DLVRY_DTLS_ID=?", new Object[] {cartDlvrDtlsId}, 
				new BeanPropertyRowMapper<CartDlvryDtl>(CartDlvryDtl.class)).get(0);
	}
	
	@Override
	public AddressDetail selectShippingAddressDetailsById(int shippingAddressId) {
		
		return jdbcTemplate.query("select * from other_address_details where OTHER_ADDRESS_ID=?", new Object[] {shippingAddressId}, 
				new BeanPropertyRowMapper<AddressDetail>(AddressDetail.class)).get(0);
	}
	
	@Override
	public String selectOrderedItemQtyByOrderId(int cartDtlsId) {
		
		return jdbcTemplate.queryForObject("select SUM(ITEM_QTY) from cart_item_dtls where CART_DTLS_ID=?", String.class, new Object[] {cartDtlsId});
	}
	
	@Override
	public void updateTransportDetails(String transporterNm, String destination, String mark, int otherAddressDtlsId) {
		
		jdbcTemplate.update("update other_address_details set MARK=?, DESTINATION=?, TRAN_NM=? where OTHER_ADDRESS_ID=?", new Object[] {mark
				, destination, transporterNm, otherAddressDtlsId});
	}
	
	@Override
	public CartDtl selectCartDetailsByCartDtlsId(String cartDtldId) {
		
		return jdbcTemplate.query("select * from cart_dtls where CART_DTLS_ID=?", new Object[] {cartDtldId}, 
				new BeanPropertyRowMapper<CartDtl>(CartDtl.class)).get(0);
	}
	
	@Override
	public int selectOtherAddressIdByCartDlvryDtlsId(int cartDlvryDtlsId) {
		
		return jdbcTemplate.query("select * from cart_dlvry_dtls where CART_DLVRY_DTLS_ID=?", new Object[] {cartDlvryDtlsId}, 
				new BeanPropertyRowMapper<CartDlvryDtl>(CartDlvryDtl.class)).get(0).getShippingAddressId();
	}
	
	@Override
	public void updateCourierDetails(String courierNm, String docateNo, String delvryDate, int cartDlvryDtlsId) {
		
		jdbcTemplate.update("update cart_dlvry_dtls set COURIER_NM=?, DOCATE_NO=?, EXP_DLVRY_DT=? where CART_DLVRY_DTLS_ID=?", new Object[] {courierNm
				, docateNo, delvryDate, cartDlvryDtlsId});
	}
	
	@Override
	public List<ItemMasterDtl> selectActiveProducts() {
		
		return jdbcTemplate.query("select * from item_master_dtls where IS_ACTIVE='Active'", 
				new BeanPropertyRowMapper<ItemMasterDtl>(ItemMasterDtl.class));
	}
	
	@Override
	public List<ItemMasterDtl> selectActiveProductsWithHsnNo() {
		
		return jdbcTemplate.query(sqlProperties.getProperty("select.products.withhsn"), new BeanPropertyRowMapper<ItemMasterDtl>(ItemMasterDtl.class));
	}
	
	@Override
	public String selectTrackingUrlByCourierName(String courierNm) {
		
		return jdbcTemplate.query("select TRACKING_URL from courier_master where COURIER_NM=?", new Object[] {courierNm}, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return rs.getString("TRACKING_URL");
			}
		}).get(0);
	}
}
