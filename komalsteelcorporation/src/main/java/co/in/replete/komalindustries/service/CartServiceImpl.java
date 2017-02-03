package co.in.replete.komalindustries.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.CartDetailRequest;
import co.in.replete.komalindustries.beans.CartDetailsTO;
import co.in.replete.komalindustries.beans.CartItemDetailsListTO;
import co.in.replete.komalindustries.beans.ItemDetailsResponse;
import co.in.replete.komalindustries.beans.ItemDetailsTO;
import co.in.replete.komalindustries.beans.ItemsDetailsRequest;
import co.in.replete.komalindustries.beans.NewCartDetailsTO;
import co.in.replete.komalindustries.beans.PaginationDetailsTO;
import co.in.replete.komalindustries.beans.ResponseMessage;
import co.in.replete.komalindustries.beans.UserDetailsTO;
import co.in.replete.komalindustries.beans.UserOrderDetailsResposneWrapper;
import co.in.replete.komalindustries.beans.entity.AppConfiguration;
import co.in.replete.komalindustries.beans.entity.CartDlvryDtl;
import co.in.replete.komalindustries.beans.entity.CartDtl;
import co.in.replete.komalindustries.beans.entity.CartItemDtl;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.ShippingAddressDetail;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.AdminDAO;
import co.in.replete.komalindustries.dao.CartDAO;
import co.in.replete.komalindustries.dao.ProductDAO;
import co.in.replete.komalindustries.utils.CommonUtility;
import co.in.replete.komalindustries.utils.MessageUtility;
import co.in.replete.komalindustries.utils.UDValues;

@Service
@Transactional(rollbackFor=Throwable.class)
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDAO cartDAO;
	
	@Autowired
	Properties responseMessageProperties;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	Properties sqlProperties;
	
	@Autowired
	Properties configProperties;
	
	@Autowired
	AdminDAO adminDAO;
	
	@Autowired
	CommonUtility commonUtility;
	
	@Autowired
	MessageUtility messageUtility;
	
	@Autowired
	private ProductDAO productDAO;
	
	/**
	 * Description : Get's the list of order details for the user as per the page number supplied
	 * @return {@link BaseWrapper}
	 * @throws {@link Exception}
	 */
	@Override
	public BaseWrapper getOrdersList(String trackId, int pageNum) throws Exception {
		
		//Validate input parameters
		if(pageNum <=0) {
		  throw new Exception(responseMessageProperties.getProperty("error.invalid.pagenum"));
		}
		
		 try
		 {
             // get max records per page
			int maxOrders=0;
		    AppConfiguration appConfiguration=adminDAO.selectConfigurationValue(KomalIndustriesConstants.MAX_LIMIT);
			   maxOrders=Integer.parseInt(appConfiguration.getConfigVal());
			// get 	order records count
			int recordsCount=cartDAO.selectOrderCount(trackId);
			if(recordsCount==0)
			{
				UserOrderDetailsResposneWrapper resposne = new UserOrderDetailsResposneWrapper();
				resposne.setCartDetails(new ArrayList<>());
				return resposne;
			}
			else
			{   
				
		    // Get order details for the track Id
			List<NewCartDetailsTO> cartDetails = cartDAO.selectCartDetaisByPageNum(trackId, pageNum,maxOrders);
			UserOrderDetailsResposneWrapper response = new UserOrderDetailsResposneWrapper();
			
			List<NewCartDetailsTO> resposneCartDetailsList = new ArrayList<NewCartDetailsTO>(); 
			
			//Get products detais for each order
			for(NewCartDetailsTO cartDetail : cartDetails) {
				
				List<CartItemDetailsListTO> cartItemDetailsList = cartDAO.selectCartItemDtls(cartDetail.getCartDtlsId());
				cartDetail.setItemOrderDetailsList(cartItemDetailsList);
				
				resposneCartDetailsList.add(cartDetail);
			}
			
			//Sret the order details of resposne
			response.setCartDetails(resposneCartDetailsList);
			
			//Set pagination details
			PaginationDetailsTO paginationTO=new PaginationDetailsTO();
			paginationTO.setNumOfOrders(recordsCount);
			paginationTO.setPageNumber(pageNum);
			
			paginationTO.setRecordPerPage(maxOrders);
			response.setPaginationDetails(paginationTO);
			
			return response;
		 }
	   }
		catch(DataAccessException e)
		 {  
			 e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		 }
		
	}

	/**
	 * Description : 
	 * @return {@link BaseWrapper}
	 * @throws {@link Exception}
	 */
	@Override
	public BaseWrapper saveOrder(CartDetailRequest request, String trackId/*, HttpServletResponse servletResponse*/) throws Exception  {

		try{
			//Get the user details 
			UserDetailsTO userDetails = cartDAO.selectUserDetails(trackId);
			int addressDtlsId = 0;
			if(null != userDetails) {
				//Check if user is verified and activated
				if(userDetails.getStatus().equals(UDValues.USER_STATUS_INACTIVE.toString())) {
					/*throw new ServicesException("Your Account is not yet activated. Please contact our support to activate your account");*/
					ResponseMessage responseMessage = new ResponseMessage("601", "Your Account is not yet activated. Please contact our support to activate your account");
					return new BaseWrapper(responseMessage);					
				}
				List<CartDetailsTO> cartDetailsList = request.getOrdersList();
				for(CartDetailsTO cartDetails : cartDetailsList)
				{
					
					//Add Shipping Address details
					if(cartDetails.getIsDefaultAddress().equalsIgnoreCase("true")) {
						addressDtlsId = Integer.parseInt(cartDetails.getShippingDtlsId().trim());
					} else {
						ShippingAddressDetail shippingAddressDetail =cartDetails.convertToAddressDetailsEntity(trackId);
						addressDtlsId = cartDAO.insertAddress(shippingAddressDetail);
					}
					
					//Add cart delivery details
					CartDlvryDtl cartDelivryDtls = cartDetails.convertToCartDlvryDetailsEntity(addressDtlsId);
					int cartDlvryDtlsId = cartDAO.insertCartDeliveryDtls(cartDelivryDtls);
					
					/*//Add Invoice Details
					InvoiceDtl invoiceDtl = cartDetails.convertToInvoiceDtlsEntity(trackId);
					int invoiceDtlsId = cartDAO.insertInvoiceDetails(invoiceDtl);
					
					//Add payment details
					PaymentDtl paymentDtl = cartDetails.convertToPaymentDtlEntity();
					String paymentDtlId = cartDAO.insertPaymentDetails(paymentDtl);
*/
					CartDtl cartDetail = null;
					Integer offerAppldId=0;
					if(cartDetails.isOfferApld().equals(UDValues.BOOLEAN_TRUE))
					{
						offerAppldId = Integer.parseInt(cartDetails.getOfferApldId());
					}
					
//					cartDetail = cartDetails.convertToCartDtlEntity(cartDlvryDtlsId, invoiceDtlsId, offerAppldId, paymentDtlId, trackId);
					cartDetail = cartDetails.convertToCartDtlEntity(cartDlvryDtlsId, 0, offerAppldId, null, trackId);
						
					int cartDtlsId = cartDAO.insertCartDetails(cartDetail);
					
					List<CartItemDetailsListTO> cartItemDetailsListTO = cartDetails.getCartItemsList();
					List<CartItemDtl> cartItemDtls = new ArrayList<CartItemDtl>();
					for(CartItemDetailsListTO cartItemDetails : cartItemDetailsListTO)
					{
						CartItemDtl cartItemDtl;
						Integer cartItemsOfferId = 0;
						/*if(cartItemDetails.isOfferApld().equals(UDValues.BOOLEAN_TRUE.toString()))
						{
							cartItemsOfferId = Integer.parseInt(cartItemDetails.getOfferId());
						}*/
							cartItemDtl = new CartItemDtl((null == cartItemDetails.isOfferApld() || cartItemDetails.isOfferApld().isEmpty()) ? "0" : cartItemDetails.isOfferApld(), 
									Float.parseFloat((null == cartItemDetails.getItemPrice() || cartItemDetails.getItemPrice().isEmpty()) ? "0" : cartItemDetails.getItemPrice()), 
									Integer.parseInt(cartItemDetails.getItemQty()), cartDtlsId, Integer.parseInt(cartItemDetails.getItemMasterDtlsId()), 
									cartItemsOfferId, trackId);
						
						cartItemDtls.add(cartItemDtl);
						

						/*//Get ordered stock
						Float orderedStock = Float.parseFloat(cartItemDetails.getItemQty());
						
						//Get item inventory details
						ItemsInventoryDtl itemsInventoryDtl = cartDAO.selectItemInventoryDtl(cartItemDetails.getItemMasterDtlsId());
						
						//Set available quantity
						Float availableStock = itemsInventoryDtl.getAvlQty();
						Float updatedAvailableStock = availableStock - orderedStock;
						
						if(updatedAvailableStock <= itemsInventoryDtl.getThrhldVal()) {
						  //TODO Notify admin about depletion of stock for the item	
						}
						
						itemsInventoryDtl.setAvlQty(updatedAvailableStock);
						
						//Set booked quantity
						Float bookedQuantity = itemsInventoryDtl.getBookedQty();
						itemsInventoryDtl.setBookedQty(bookedQuantity + orderedStock);
						
						//Update the Items Inventory details
						cartDAO.updateItemInventoryDetails(itemsInventoryDtl.getAvlQty(), itemsInventoryDtl.getBookedQty(), itemsInventoryDtl.getItemsInventoryDtlsId());*/
					}
					
					if(cartItemDtls.size() > 0)
					{
						cartDAO.insertCartItemDetails(cartItemDtls);
					}else{
						throw new Exception(responseMessageProperties.getProperty("error.cartitemdetails.empty"));
					}
					
					/*//Call the service to accept the payment if payment mode is online
					if(paymentDtl.getPaymentModeCd().equals(UDValues.PAYMENT_MODE_ONLINE.toString())) {
						PaymentDetailsRequest paymentDetailsRequest = new PaymentDetailsRequest(paymentDtl.getPaymentAmount().toString(), paymentDtl.getPaymentGatewayCd(), 
								userDetails.getFirstName(), userDetails.getLoginId(),userDetails.getCntc_num(), "ABC", paymentDtl.getPaymentModeCd(), paymentDtlId);
						response = paymentService.doAcceptPayment(paymentDetailsRequest, servletResponse);
					} else if(paymentDtl.getPaymentModeCd().equals(UDValues.PAYMENT_MODE_COD.toString())) {
						response = new BaseWrapper();
					} else {
						throw new Exception(responseMessageProperties.getProperty("error.paymentmode.invalid"));
					}*/
					
					//TODO send customized message based on active and inactive users. Also send messages from all the application in ascynchronous way and using 
					// template structure from DB
					
					//Send Notification to alternate number
					messageUtility.sendMessage(cartDetails.getAlternateCntc(), 
							String.format(configProperties.getProperty("sms.orderplaced.success"), cartDtlsId));
					
					//Send Email Content
					String emailContentPrefix = responseMessageProperties.getProperty("order.book.prefix");
					String emailContentSuffix = responseMessageProperties.getProperty("order.book.suffix");
					
					StringBuilder sb = new StringBuilder(emailContentPrefix);
					
					for (CartItemDtl cartItemDtl : cartItemDtls){
						ItemMasterDtl itemMasterDtl = productDAO.selectProductDetailsByItemId(Integer.toString(cartItemDtl.getItemMasterDtlsId()));
						sb.append("<tr>");
						sb.append("<td>" + itemMasterDtl.getItemNm() + "</td>");
						sb.append("<td>" + itemMasterDtl.getUom() + "</td>");
						sb.append("<td>" + cartItemDtl.getItemQty() + "</td>");
						sb.append("</tr>");
					}
					
					sb.append(emailContentSuffix);
					
					ShippingAddressDetail shippingAddressDetail = cartDAO.selectShippingAddressDetailsById(addressDtlsId);
					
					String finalEmailString = String .format(sb.toString(), 
							(null == userDetails.getDisplayName() || userDetails.getDisplayName().isEmpty() || userDetails.getDisplayName().equalsIgnoreCase("null")) ? "Not Specified" : userDetails.getDisplayName().trim(), 
							null == userDetails.getFirstName() ? "" : userDetails.getFirstName()
							, null == userDetails.getLastName() ? "" : userDetails.getLastName(),  
							(null == userDetails.getCntc_num() || userDetails.getCntc_num().isEmpty()) ? "Not Specified": userDetails.getCntc_num(), cartDtlsId,
							(null == shippingAddressDetail.getMark() || shippingAddressDetail.getMark().isEmpty()) ? "Not Specified" : shippingAddressDetail.getMark(), 
							(null == shippingAddressDetail.getDestination() || shippingAddressDetail.getDestination().isEmpty()) ? "Not Specified" : shippingAddressDetail.getDestination(), 
							(null == shippingAddressDetail.getTranNm() || shippingAddressDetail.getTranNm().isEmpty()) ? "Not Specified" : shippingAddressDetail.getTranNm(),
									cartDetail.getCartNotes());
					
//					System.out.println("FINAL EMAIL STRING: " + finalEmailString);
					commonUtility.sendEmailToAdmin(finalEmailString, 
							responseMessageProperties.getProperty("order.book.subject"));
					return new BaseWrapper();
				}
			} else {
				throw new Exception(responseMessageProperties.getProperty("error.trackId.invalid"));
			}
		} catch(DataAccessException e){
			e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		} 
		
		return new BaseWrapper();
	}

	/**
	 * Description : 
	 * @return {@link BaseWrapper}
	 * @throws {@link Exception}
	 */
	@Override
	public BaseWrapper getItemsList(ItemsDetailsRequest itemDetailsRequest) throws Exception {
		String categoryCd = itemDetailsRequest.getCategoryCd();
		String subCategoryCd = itemDetailsRequest.getSubCategoryCd();
		String brandName = itemDetailsRequest.getBrandName();
		ItemDetailsResponse itemDetailsResponse = new ItemDetailsResponse();
		String[] filterValues;
		if(!categoryCd.isEmpty() && subCategoryCd.isEmpty() && brandName.isEmpty())
		{
			filterValues = new String[] {categoryCd};
			itemDetailsResponse.setItemsList(cartDAO.selectItemsListByFilter(sqlProperties.getProperty("select.itemdetails.bycategory"), filterValues));
		}else if(!categoryCd.isEmpty() && !subCategoryCd.isEmpty() && brandName.isEmpty())
		{
			filterValues = new String[] {categoryCd, subCategoryCd};
			itemDetailsResponse.setItemsList(cartDAO.selectItemsListByFilter(sqlProperties.getProperty("select.itemdetails.bycategoryandsubcategory"), filterValues));
		}else if(categoryCd.isEmpty() && subCategoryCd.isEmpty() && !brandName.isEmpty())
		{
			filterValues = new String[] {brandName};
			itemDetailsResponse.setItemsList(cartDAO.selectItemsListByFilter(sqlProperties.getProperty("select.itemdetails.bymanufacturer"), filterValues));
		}else{
			throw new Exception(responseMessageProperties.getProperty("error.requiredfields.empty"));
		}
		
		return itemDetailsResponse;
	}
	
	/**
	 * Description : Search items list that match the keyword specified. Search is done for records that have
	 * relevant match for category, sub-category, manufacturer against the keyword specified
	 * 
	 * @return {@link BaseWrapper}
	 * @throws {@link Exception}
	 */
	@Override
	public BaseWrapper searchItems(String keyword) throws Exception{
		
		try {
		  ItemDetailsResponse itemDetailsResponse = new ItemDetailsResponse();
          List<ItemDetailsTO> itemDetailsTOList = cartDAO.selectItemsDetails(keyword);
		  itemDetailsResponse.setItemsList(itemDetailsTOList);
		  return itemDetailsResponse;
		} catch ( DataAccessException dae) {
			dae.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		}
	}
}
