package co.in.replete.komalindustries.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.in.replete.komalindustries.beans.AddItemsToCartTO;
import co.in.replete.komalindustries.beans.CartItemDtlsTO;
import co.in.replete.komalindustries.beans.OrderEditTO;
import co.in.replete.komalindustries.beans.UserDetailsAllTO;
import co.in.replete.komalindustries.beans.entity.AddressDetail;
import co.in.replete.komalindustries.beans.entity.CartDtl;
import co.in.replete.komalindustries.beans.entity.CartItemDtl;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.ItemsInventoryDtl;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.OrderDetailsDAO;
import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.utils.CommonUtility;
import co.in.replete.komalindustries.utils.MessageUtility;
import co.in.replete.komalindustries.utils.UDValues;
import co.in.replete.komalindustries.webcontroller.beans.EditCartItemDtlsTO;
import co.in.replete.komalindustries.webcontroller.beans.WOrderDetailsTO;

@Service
@Transactional(rollbackFor=Throwable.class)
public class OrderDetailsServiceImpl implements OrderDetailsService {

	@Autowired
	OrderDetailsDAO orderDetailsDAO;
	
	@Autowired
	CommonUtility commonUtility;
	
	@Autowired
	MessageUtility messageUtility;
	
	@Autowired
	Properties configProperties;
	
	@Autowired
	private UserManagementDAO userDAO;
	

	@Override
	public void editOrderDetails(OrderEditTO request) throws DataAccessException, Exception {
		
		//Get the original status of the cart
		int orderId = request.getOrderId();
		CartDtl cartDetails = orderDetailsDAO.selectOrderDetailsById(orderId);
		String cartStatus = cartDetails.getCartStatus();
		
		//Update the order details
		orderDetailsDAO.updateOrderDetails(request);
		
		//Notify the user of the changed status if the order status has changed
		String orderStatus = request.getOrderStatus();
		
		if(!cartStatus.equalsIgnoreCase(orderStatus)) {
			//If Order Status is Dispatched than send proper message to the Customer and deduct the qty from available stock in inventory and booked Qty
			if(orderStatus.equals(UDValues.CART_STATUS_DISPATCHED.toString())) {
				/*if(0 != cartDetails.getCartDlvryDtlsId()) {
					int cartDlvrDtlsId = cartDetails.getCartDlvryDtlsId();
					CartDlvryDtl cartDlvryDtl = orderDetailsDAO.selectCartDeliveryDetailsById(cartDlvrDtlsId);
					if(null != request.getDlvryDate() || !request.getDlvryDate().isEmpty() || !request.getDlvryDate().equalsIgnoreCase("null")) {
						AddressDetail addressDetail = orderDetailsDAO.selectShippingAddressDetailsById(cartDlvryDtl.getShippingAddressId()); //Get shipping details of customer
						String contactNo = orderDetailsDAO.getContactNumberFromTrackId(cartDetails.getTrackId());	//	get contact number of customer
						messageUtility.sendMessage(contactNo, String.format(configProperties.getProperty("order.dispatch"), addressDetail.getTranNm(), 
								cartDetails.getLrNo(), new SimpleDateFormat("dd/MM/yyyy").format(new Date()), cartDetails.getNoOfCartonLoaded()));
					}
				}*/
				
				doUpdateInventoryDetailsAfterProductDispatch(orderId);
			}
		}
		
	}
	
	private void doUpdateInventoryDetailsAfterProductDispatch(int orderId) {
		//Get Item details by cart Dtls id
		List<CartItemDtl> cartItemsList = orderDetailsDAO.selectCartItemsByCartDtlsId(orderId);
		
		//Deduct Each item quantity from the Inventory
		for(CartItemDtl cartItem : cartItemsList) {
			int itemMasterDtlsId = cartItem.getItemMasterDtlsId();
			int itemQty = cartItem.getItemQty();
			
			//Get Inventory Details
			ItemsInventoryDtl itemsInventoryDtl = orderDetailsDAO.selectItemInventoryDetailsByItemMasterId(itemMasterDtlsId);
			
			//Get Final Booked Qty 
			Float finalBookedQty = itemsInventoryDtl.getBookedQty() - itemQty;
			
			//Get Final Available Quantity
			Float finalAvailableQty = itemsInventoryDtl.getAvlQty() - itemQty;
			
			//Update the final available and booked qty of the item
			orderDetailsDAO.updateInventoryDetailsByItemInventoryDtlsId(finalAvailableQty, finalBookedQty, itemsInventoryDtl.getItemsInventoryDtlsId());
		}
	}

	@Override
	public List<WOrderDetailsTO> searchOrders(String searchBy, String searchDateRange)
			throws DataAccessException, Exception {

		return orderDetailsDAO.searchOrders(searchBy, searchDateRange);
	}
	
	/*@Override
	public void editLRNo(String cartDtldId, String lrNo, String lrDate, String noofcarton, 
			String transporterNm, String destination, String mark, String courierNm, 
			String docateNo, String delvryDate) throws Exception {
		if(lrDate.isEmpty()) {
			lrDate = null;
		}
		
		orderDetailsDAO.updateLrNo(cartDtldId, lrNo, lrDate, noofcarton);
		
		CartDtl cartDtl = orderDetailsDAO.selectCartDetailsByCartDtlsId(cartDtldId);
		
		int cartDlvryDtlsId = cartDtl.getCartDlvryDtlsId();
		int otherAddressDtlsId = orderDetailsDAO.selectOtherAddressIdByCartDlvryDtlsId(cartDlvryDtlsId);
		
		orderDetailsDAO.updateTransportDetails(transporterNm, destination, mark, otherAddressDtlsId);
		
		CartDtl cartDetails = orderDetailsDAO.selectOrderDetailsById(Integer.parseInt(cartDtldId));
		String contactNo = orderDetailsDAO.getContactNumberFromTrackId(cartDetails.getTrackId());
		
		DateFormat dfYYYYMMdd = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dfddMMMMYYYY = new SimpleDateFormat("dd MMMM yyyy");
		
		if (!lrNo.isEmpty() && courierNm.isEmpty()) {
			String lrDispatchDetailsMssg = "From Komal Trading Corporation:\n" +
					"Order No. - " + cartDtldId + "\n" + 
					"Transporter Name - " + transporterNm + "\n" + 
					"Destination - " + destination + "\n" + 
					"LR NO - " + lrNo + "\n" + 
					"LR Date - " + dfddMMMMYYYY.format(dfYYYYMMdd.parse(lrDate)) + "\n" + 
					"No. Of Carton - " + noofcarton;

			System.out.println("contactNo-" + contactNo + ",\n lrNoDispatchDetailsMssg - " + lrDispatchDetailsMssg);
			contactNo += "," + KomalIndustriesConstants.ADMIN_MOBILE_NO;
			
			messageUtility.sendMessage(contactNo, lrDispatchDetailsMssg);
		}

		if (!lrNo.isEmpty() && !courierNm.isEmpty()) {
			orderDetailsDAO.updateCourierDetails(courierNm, docateNo, delvryDate, cartDlvryDtlsId);
			String trackingUrl = orderDetailsDAO.selectTrackingUrlByCourierName(courierNm);
			String courierDispatchDetailsMssg = "From Komal Trading Corporation:\n" +
					"Order No. - " + cartDtldId + "\n" + 
					"Courier Name - " + courierNm + "\n" + 
					"Docate No - " + docateNo + "\n" + 
					"Delivery Date - " +  dfddMMMMYYYY.format(dfYYYYMMdd.parse(delvryDate)) + "\n" + 
					"Tracking Url - " + trackingUrl;

			System.out.println("contactNo-" + contactNo + ",\n courierDispatchDetailsMssg - " + courierDispatchDetailsMssg);
			contactNo += "," + KomalIndustriesConstants.ADMIN_MOBILE_NO;
			
			messageUtility.sendMessage(contactNo, courierDispatchDetailsMssg);
		}

		
	}*/
	
	@Override
	public void editLRNo(String cartDtldId, String lrNo, String lrDate, String noofcarton, 
			String transporterNm, String destination, String mark) throws Exception {
		if(lrDate.isEmpty()) {
			lrDate = null;
		}
		
		orderDetailsDAO.updateLrNo(cartDtldId, lrNo, lrDate, noofcarton);
		
		CartDtl cartDtl = orderDetailsDAO.selectCartDetailsByCartDtlsId(cartDtldId);
		
		int cartDlvryDtlsId = cartDtl.getCartDlvryDtlsId();
		int otherAddressDtlsId = orderDetailsDAO.selectOtherAddressIdByCartDlvryDtlsId(cartDlvryDtlsId);
		
		orderDetailsDAO.updateTransportDetails(transporterNm, destination, mark, otherAddressDtlsId);
		
		CartDtl cartDetails = orderDetailsDAO.selectOrderDetailsById(Integer.parseInt(cartDtldId));
		String contactNo = orderDetailsDAO.getContactNumberFromTrackId(cartDetails.getTrackId());
		
		DateFormat dfYYYYMMdd = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dfddMMMMYYYY = new SimpleDateFormat("dd MMMM yyyy");
		
		String lrDispatchDetailsMssg = "From Komal Trading Corporation:\n" +
				"Order No. - " + cartDtldId + "\n" + 
				"Transporter Name - " + transporterNm + "\n" + 
				"Destination - " + destination + "\n" + 
				"LR NO - " + lrNo + "\n" + 
				"LR Date - " + dfddMMMMYYYY.format(dfYYYYMMdd.parse(lrDate)) + "\n" + 
				"No. Of Carton - " + noofcarton;

		System.out.println("contactNo-" + contactNo + ",\n lrNoDispatchDetailsMssg - " + lrDispatchDetailsMssg);
		contactNo += "," + KomalIndustriesConstants.ADMIN_MOBILE_NO;
		
		messageUtility.sendMessage(contactNo, lrDispatchDetailsMssg);

		/*if (!lrNo.isEmpty() && !courierNm.isEmpty()) {
			orderDetailsDAO.updateCourierDetails(courierNm, docateNo, delvryDate, cartDlvryDtlsId);
			String trackingUrl = orderDetailsDAO.selectTrackingUrlByCourierName(courierNm);
			String courierDispatchDetailsMssg = "From Komal Trading Corporation:\n" +
					"Order No. - " + cartDtldId + "\n" + 
					"Courier Name - " + courierNm + "\n" + 
					"Docate No - " + docateNo + "\n" + 
					"Delivery Date - " +  dfddMMMMYYYY.format(dfYYYYMMdd.parse(delvryDate)) + "\n" + 
					"Tracking Url - " + trackingUrl;

			System.out.println("contactNo-" + contactNo + ",\n courierDispatchDetailsMssg - " + courierDispatchDetailsMssg);
			contactNo += "," + KomalIndustriesConstants.ADMIN_MOBILE_NO;
			
			messageUtility.sendMessage(contactNo, courierDispatchDetailsMssg);
		}*/		
	}
	
	@Override
	public void editCourierDtls(String cartDtldId, String courierNm, String docateNo, String delvryDate) throws Exception {
		
		
		CartDtl cartDtl = orderDetailsDAO.selectCartDetailsByCartDtlsId(cartDtldId);
		
		int cartDlvryDtlsId = cartDtl.getCartDlvryDtlsId();
		
		CartDtl cartDetails = orderDetailsDAO.selectOrderDetailsById(Integer.parseInt(cartDtldId));
		String contactNo = orderDetailsDAO.getContactNumberFromTrackId(cartDetails.getTrackId());
		
		AddressDetail transportationDetails = orderDetailsDAO.selectTransportationDetailsByCartDlvryDtlsId(cartDlvryDtlsId);
		
		DateFormat dfYYYYMMdd = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dfddMMMMYYYY = new SimpleDateFormat("dd MMMM yyyy");

		orderDetailsDAO.updateCourierDetails(courierNm, docateNo, delvryDate, cartDlvryDtlsId);
		String trackingUrl = orderDetailsDAO.selectTrackingUrlByCourierName(courierNm);
		
		String courierDispatchDetailsMssg = "From Komal Trading Corporation:\n" +
				"Order No. - " + cartDtldId + "\n" + 
				"Transporter Name - " + transportationDetails.getTranNm() + "\n" + 
				"Destination - " + transportationDetails.getDestination() + "\n" +
				"Mark - " + transportationDetails.getMark() + "\n" +
				"LR NO - " + cartDetails.getLrNo() + "\n" + 
				"LR Date - " + dfddMMMMYYYY.format(dfYYYYMMdd.parse(cartDetails.getLrNoDate())) + "\n" + 
				"No. Of Carton - " + cartDetails.getNoOfCartonLoaded() + "\n" +
				"Courier Name - " + courierNm + "\n" + 
				"Docate No - " + docateNo + "\n" + 
				"Delivery Date - " +  dfddMMMMYYYY.format(dfYYYYMMdd.parse(delvryDate)) + "\n" + 
				"Tracking Url - " + trackingUrl;

		System.out.println("contactNo-" + contactNo + ",\n courierDispatchDetailsMssg - " + courierDispatchDetailsMssg);
		contactNo += "," + KomalIndustriesConstants.ADMIN_MOBILE_NO;
		
		messageUtility.sendMessage(contactNo, courierDispatchDetailsMssg);
		
	}
	
	
	@Override
	public void addCartItemToOrder(AddItemsToCartTO request) throws ServicesException {
		
		//Insert Cart Item Details
		orderDetailsDAO.insertCartItemForAnOrder(request);
		
//		//Update Order Details
//		orderDetailsDAO.updateOrderPrice(request.getOrderDtlsId(), request.getCartonPrice(), KomalIndustriesConstants.UPDATE_TYPE_ADD_ORDER_PRICE);
	}
	
	@Override
	public void deleteItemFromOrderCart(int cartItemDtlsId) throws ServicesException {

		/*//Get the cart items details
		CartItemDtl cartItemDetail = orderDetailsDAO.selectCartItemDtlsById(cartItemDtlsId);
		*/
		//Delete the item from cart item details
		orderDetailsDAO.deleteItemFromOrderCart(cartItemDtlsId);
		
		/*//Update Order Details
		orderDetailsDAO.updateOrderPrice(cartItemDetail.getCartDtlsId(), cartItemDetail.getItemPrice(), 
				KomalIndustriesConstants.UPDATE_TYPE_MINUS_ORDER_PRICE);*/
	}
	
	@Override
	public void editItemFromOrderCart(EditCartItemDtlsTO request) throws ServicesException {

		//Get the cart item details 
		CartItemDtl cartItemDtl = orderDetailsDAO.selectCartItemDtlsById(request.getCartItemDtlsId());

		/*Float requestItemPrice = request.getItemPrice();
		Float cartItemPrice = cartItemDtl.getItemPrice();
		if(!(Float.compare(requestItemPrice, cartItemPrice) == 0 ? true : false) || 
				request.getItemQty()!=cartItemDtl.getItemQty()) {*/
		if( request.getItemQty()!= cartItemDtl.getItemQty()) {
			//Edit the cart item details
			orderDetailsDAO.updateCartItemDtls(request);
			
			//Update Order Details
			/*String updateType = null;
			Float priceToUpadte = 0F;
			if(requestItemPrice > cartItemPrice) {
				updateType = KomalIndustriesConstants.UPDATE_TYPE_ADD_ORDER_PRICE;
				priceToUpadte = requestItemPrice - cartItemPrice;
			} else if (requestItemPrice < cartItemPrice) {
				updateType = KomalIndustriesConstants.UPDATE_TYPE_MINUS_ORDER_PRICE;
				priceToUpadte = cartItemPrice - requestItemPrice;
			}
			
			orderDetailsDAO.updateOrderPrice(request.getOrderDtlsId(), priceToUpadte, updateType); 
			*/
		}
	}
	
	@Override
	public void addDiscount(String discountPrice, String orderDtlsId) throws ServicesException {
		
		orderDetailsDAO.updateOrderPrice(Integer.parseInt(orderDtlsId), Float.parseFloat(discountPrice), 
				KomalIndustriesConstants.UPDATE_TYPE_DISCOUNT);
	}
	
	@Override
	public UserDetailsAllTO getUserDetailsByTrackId(String userTrackId) throws Exception {
		
		return userDAO.selectUserDetailsByTrackId(userTrackId).get(0);
	}
	
	@Override
	public List<ItemMasterDtl> getActiveProducts() {
		
		return orderDetailsDAO.selectActiveProductsWithHsnNo();
	}

	@Override
	public List<CartItemDtlsTO> getCartItemDetailsByOrderId(int oid) {
		
		return orderDetailsDAO.selectCartItemsToByCartDtlsId(oid);
	}
	
}