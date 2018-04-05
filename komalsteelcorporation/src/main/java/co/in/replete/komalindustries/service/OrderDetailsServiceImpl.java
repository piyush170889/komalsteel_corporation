package co.in.replete.komalindustries.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.in.replete.komalindustries.beans.AddItemsToCartTO;
import co.in.replete.komalindustries.beans.OrderEditTO;
import co.in.replete.komalindustries.beans.entity.AddressDetail;
import co.in.replete.komalindustries.beans.entity.CartDlvryDtl;
import co.in.replete.komalindustries.beans.entity.CartDtl;
import co.in.replete.komalindustries.beans.entity.CartItemDtl;
import co.in.replete.komalindustries.beans.entity.ItemsInventoryDtl;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.OrderDetailsDAO;
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
				if(0 != cartDetails.getCartDlvryDtlsId()) {
					int cartDlvrDtlsId = cartDetails.getCartDlvryDtlsId();
					CartDlvryDtl cartDlvryDtl = orderDetailsDAO.selectCartDeliveryDetailsById(cartDlvrDtlsId);
					if(null != request.getDlvryDate() || !request.getDlvryDate().isEmpty() || !request.getDlvryDate().equalsIgnoreCase("null")) {
						AddressDetail addressDetail = orderDetailsDAO.selectShippingAddressDetailsById(cartDlvryDtl.getShippingAddressId()); //Get shipping details of customer
						String contactNo = orderDetailsDAO.getContactNumberFromTrackId(cartDetails.getTrackId());	//	get contact number of customer
						messageUtility.sendMessage(contactNo, String.format(configProperties.getProperty("order.dispatch"), addressDetail.getTranNm(), 
								cartDetails.getLrNo(), new SimpleDateFormat("dd/MM/yyyy").format(new Date()), cartDetails.getNoOfCartonLoaded()));
					}
				}
				
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
	
	@Override
	public void editLRNo(String cartDtldId, String lrNo, String lrDate, String noofcarton) throws Exception {
		if(lrDate.isEmpty()) {
			lrDate = null;
		}
		orderDetailsDAO.updateLrNo(cartDtldId, lrNo, lrDate, noofcarton);
		CartDtl cartDetails = orderDetailsDAO.selectOrderDetailsById(Integer.parseInt(cartDtldId));
		String contactNo = orderDetailsDAO.getContactNumberFromTrackId(cartDetails.getTrackId());
		messageUtility.sendMessage(contactNo, "Thank You for choosing us. Your order's LR NO for tracing the order is : " + lrNo);
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
	
}