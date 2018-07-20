
package co.in.replete.komalindustries.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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
import co.in.replete.komalindustries.beans.Invoice;
import co.in.replete.komalindustries.beans.ItemDetailsResponse;
import co.in.replete.komalindustries.beans.ItemDetailsTO;
import co.in.replete.komalindustries.beans.ItemsDetailsRequest;
import co.in.replete.komalindustries.beans.NewCartDetailsTO;
import co.in.replete.komalindustries.beans.PaginationDetailsTO;
import co.in.replete.komalindustries.beans.ResponseMessage;
import co.in.replete.komalindustries.beans.TaxDescription;
import co.in.replete.komalindustries.beans.Transaction;
import co.in.replete.komalindustries.beans.UserDetailsTO;
import co.in.replete.komalindustries.beans.UserOrderDetailsResposneWrapper;
import co.in.replete.komalindustries.beans.entity.AppConfiguration;
import co.in.replete.komalindustries.beans.entity.CartDlvryDtl;
import co.in.replete.komalindustries.beans.entity.CartDtl;
import co.in.replete.komalindustries.beans.entity.CartItemDtl;
import co.in.replete.komalindustries.beans.entity.HSNDetails;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.ItemsInventoryDtl;
import co.in.replete.komalindustries.beans.entity.ShippingAddressDetail;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.AdminDAO;
import co.in.replete.komalindustries.dao.CartDAO;
import co.in.replete.komalindustries.dao.ProductDAO;
import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.dao.WMasterDAO;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.utils.CommonUtility;
import co.in.replete.komalindustries.utils.GSTINValidator;
import co.in.replete.komalindustries.utils.GeneratePdf;
import co.in.replete.komalindustries.utils.MessageUtility;
import co.in.replete.komalindustries.utils.UDValues;

@Service
@Transactional(rollbackFor=Throwable.class)
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDAO cartDAO;
	
	@Autowired
	private UserManagementDAO userDAO;
	
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
	
	@Autowired
	private WMasterDAO wMasterDAO;
	
	@Autowired
	private GeneratePdf generatePdf;
	
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
			Float userDiscount = userDetails.getDiscount()/100;
			
			String custEmailId = userDetails.getLoginId();
			int addressDtlsId = 0;
			if(null != userDetails) {
				//Check if user is verified and activated
				if(userDetails.getStatus().equals(UDValues.USER_STATUS_INACTIVE.toString())) {
					/*throw new ServicesException("Your Account is not yet activated. Please contact our support to activate your account");*/
					ResponseMessage responseMessage = new ResponseMessage("601", "Your Account is not yet activated. Please contact our support to activate your account", 
							configProperties.getProperty("api.version"));
					return new BaseWrapper(responseMessage);					
				}
				
				List<CartDetailsTO> cartDetailsList = request.getOrdersList();
				for(CartDetailsTO cartDetails : cartDetailsList)
				{
					String gstNo = userDetails.getGstNo();
					//If GST for user is empty than update the GST sent in the request
					if (null == gstNo || gstNo.isEmpty()) {
						gstNo = cartDetails.getGstNo();
						if (null == gstNo || gstNo.isEmpty()) {
							throw new ServicesException("GST No not supplied");
						}
						//Check if GST no sent is Valid
						GSTINValidator gstinValidator = new GSTINValidator();
						if (!gstinValidator.validateGSTIN(gstNo)) {
							throw new Exception("Invalid GST No. Supplied. Please supply a legitimate GSTIN NO");
						}
						int gstUpdateRowAffected =userDAO.updateGstNo(trackId, gstNo);
						if (gstUpdateRowAffected != 1) {
							throw new Exception("GST No. cannot be updated to your profile now. Please try again later");
						}
					}
					
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
					/*if(cartDetails.isOfferApld().equals(UDValues.BOOLEAN_TRUE))
					{
						offerAppldId = Integer.parseInt(cartDetails.getOfferApldId());
					}*/
					
//					cartDetail = cartDetails.convertToCartDtlEntity(cartDlvryDtlsId, invoiceDtlsId, offerAppldId, paymentDtlId, trackId);
					cartDetail = cartDetails.convertToCartDtlEntity(cartDlvryDtlsId, 0, offerAppldId, null, trackId);
					
					int cartDtlsId = cartDAO.selectLatestOrderId() + 1;
					System.out.println("Order No - " + cartDtlsId);
					cartDetail.setCartDtlsId(cartDtlsId);
					cartDAO.insertCartDetails(cartDetail);
					
					List<CartItemDetailsListTO> cartItemDetailsListTO = cartDetails.getCartItemsList();
					List<CartItemDtl> cartItemDtls = new ArrayList<CartItemDtl>();
					
					//Send Order Email to Admin
					String emailContentPrefix = responseMessageProperties.getProperty("order.book.prefix");
					String emailContentSuffix = responseMessageProperties.getProperty("order.book.suffix");
					String customerEmailContentPrefix = responseMessageProperties.getProperty("customer.order.book.prefix");
					
					StringBuilder sb = new StringBuilder(emailContentPrefix);
					
					/*for (CartItemDtl cartItemDtl : cartItemDtls){
						ItemMasterDtl itemMasterDtl = productDAO.selectProductDetailsByItemId(Integer.toString(cartItemDtl.getItemMasterDtlsId()));
						sb = appendData(itemMasterDtl, sb, cartItemDtl.getItemQty());
					}*/
					
					for(CartItemDetailsListTO cartItemDetails : cartItemDetailsListTO)
					{
						CartItemDtl cartItemDtl;
						Integer cartItemsOfferId = 0;
						
						int cartItemQty = Integer.parseInt(cartItemDetails.getItemQty());
						
						ItemMasterDtl itemMasterDtl = productDAO.selectProductDetailsByItemId(cartItemDetails.getItemMasterDtlsId());
						sb = appendData(itemMasterDtl, sb, cartItemQty);
						
						double cartItemPrice = itemMasterDtl.getPerUnitPrice();
						double discountedCartItemPrice = cartItemPrice - (cartItemPrice * userDiscount);
						
						System.out.println("Cart Item Price: " + cartItemPrice +  ", Discuonted Price: " + discountedCartItemPrice);
						/*if(cartItemDetails.isOfferApld().equals(UDValues.BOOLEAN_TRUE.toString()))
						{
							cartItemsOfferId = Integer.parseInt(cartItemDetails.getOfferId());
						}*/
						
						cartItemDtl = new CartItemDtl((null == cartItemDetails.isOfferApld() || cartItemDetails.isOfferApld().isEmpty()) ? "0" : cartItemDetails.isOfferApld(), 
								Float.parseFloat(Double.toString(discountedCartItemPrice)), cartItemQty, cartDtlsId, Integer.parseInt(cartItemDetails.getItemMasterDtlsId()), 
								cartItemsOfferId, trackId);
						
						cartItemDtls.add(cartItemDtl);
						
						//Update Inventory Details for the Product
						//Get ordered stock
						Float orderedStock = Float.parseFloat(cartItemDetails.getItemQty());
						
						//Get item inventory details
						ItemsInventoryDtl itemsInventoryDtl = cartDAO.selectItemInventoryDtl(cartItemDetails.getItemMasterDtlsId());
						
						//Notify Admin If available quantity goes down after placing order for this product
						/*Float availableStock = itemsInventoryDtl.getAvlQty();
						Float updatedAvailableStock = availableStock - orderedStock;
						
						if(updatedAvailableStock <= itemsInventoryDtl.getThrhldVal()) {
						  commonUtility.sendEmailToAdmin("", "Product Threshold Reached for product: " + );
						}*/
						
//						itemsInventoryDtl.setAvlQty(updatedAvailableStock);
						
						//Set booked quantity
						Float bookedQuantity = itemsInventoryDtl.getBookedQty();
						Float updatedBookedQuantity = bookedQuantity + orderedStock;
						itemsInventoryDtl.setBookedQty(updatedBookedQuantity);
						
						//Update the Items Inventory details
						cartDAO.updateItemInventoryDetails(itemsInventoryDtl.getAvlQty(), itemsInventoryDtl.getBookedQty(), itemsInventoryDtl.getItemsInventoryDtlsId());
					}
					
					sb.append(emailContentSuffix);
					
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
					
					ShippingAddressDetail shippingAddressDetail = cartDAO.selectShippingAddressDetailsById(addressDtlsId);
					
					String finalEmailString = String.format(sb.toString(), 
							(null == userDetails.getDisplayName() || userDetails.getDisplayName().isEmpty() || userDetails.getDisplayName().equalsIgnoreCase("null")) ? "Not Specified" : userDetails.getDisplayName().trim(), 
							null == userDetails.getFirstName() ? "" : userDetails.getFirstName()
							, null == userDetails.getLastName() ? "" : userDetails.getLastName(),  
							(null == userDetails.getCntc_num() || userDetails.getCntc_num().isEmpty()) ? "Not Specified": userDetails.getCntc_num(), cartDtlsId,
							(null == shippingAddressDetail.getMark() || shippingAddressDetail.getMark().isEmpty()) ? "Not Specified" : shippingAddressDetail.getMark(), 
							(null == shippingAddressDetail.getDestination() || shippingAddressDetail.getDestination().isEmpty()) ? "Not Specified" : shippingAddressDetail.getDestination(), 
							(null == shippingAddressDetail.getTranNm() || shippingAddressDetail.getTranNm().isEmpty()) ? "Not Specified" : shippingAddressDetail.getTranNm(),
									cartDetail.getCartNotes());
					
					//Send Order Email to customer if email id is present and create Invoice Data to attach and send
					List<Transaction> transactionList = new ArrayList<Transaction>();
					List<TaxDescription> taxDescriptionList = new ArrayList<TaxDescription>();
					int totalItemInCart = 0;
					double totalChargableAmount = 0;
					double totalTaxableValue = 0;
					double iGstAmount = 0;
					double totalSGstAmount = 0;
					double totalCGstAmount = 0;
					
					String gstCode = commonUtility.getGstCode(gstNo.trim());		//Get the GST code associated
					
					if (null != custEmailId && !custEmailId.isEmpty() && configProperties.getProperty("details.isfilled").equalsIgnoreCase("Y")) {
						StringBuilder sbCust = new StringBuilder(customerEmailContentPrefix);
						
						for (CartItemDtl cartItemDtl : cartItemDtls){
							ItemMasterDtl itemMasterDtl = productDAO.selectProductDetailsByItemId(Integer.toString(cartItemDtl.getItemMasterDtlsId()));
							System.out.println("ItemMasterDtl - " + itemMasterDtl.getItemMasterDtlsId() + "HSN No - " +itemMasterDtl.getHsnDtlsId());
							HSNDetails hsnDetails = wMasterDAO.selectHsnDetailsByHsnDtlsId(itemMasterDtl.getHsnDtlsId());
							
							int itemQty = cartItemDtl.getItemQty();
							
//							double perUnitPrice = itemMasterDtl.getPerUnitPrice();
							double perUnitPrice = cartItemDtl.getItemPrice();
							Double amount = perUnitPrice * itemQty;
							String hsnSac = Integer.toString(hsnDetails.getHsnNo());
							float gstRate = 0F;
							TaxDescription taxDescription = null;
							float cGstRate = 0; 
							float cGsttaxAmount = 0; 
							float sGstRate = 0; 
							float sGsttaxAmount = 0;
							
							if (gstCode.equalsIgnoreCase("27")) {
								cGstRate = hsnDetails.getcGst();
								cGsttaxAmount = (float) ((amount * cGstRate)/100);
								sGstRate = hsnDetails.getsGst(); 
								sGsttaxAmount = (float) ((amount * sGstRate)/100);
								gstRate = cGstRate + sGstRate;
							} else {
								gstRate = hsnDetails.getiGst();
							}
							
							float taxAmount = (float) ((amount * gstRate)/100);
							Transaction transaction = new Transaction(itemMasterDtl.getItemNm() + "-" + itemMasterDtl.getUom(), 
									hsnSac, gstRate, itemQty, Float.parseFloat(Double.toString(perUnitPrice)), "Pc.", 
									Float.parseFloat(Double.toString(amount)));
							transactionList.add(transaction);
							
							if (existsHsn(hsnSac, taxDescriptionList)) {
								//If exists than update the taxable amount and tax detials
								int taxDescriptionListSize = taxDescriptionList.size();
								for (int i=0; i<taxDescriptionListSize; i++) {
									TaxDescription taxDescriptionToCompare = taxDescriptionList.get(i);
									if (hsnSac.equalsIgnoreCase(taxDescriptionToCompare.getHsnSac())) {
										float updatedCGstTaxAmmount = 0F;
										float updatedSGstTaxAmmount = 0F;
										float updatedIGstTaxAmmount = 0F;
										
										Float updatedTaxableValue = taxDescriptionToCompare.getTaxableValue() + Float.parseFloat(Double.toString(amount));
										if (gstCode.equalsIgnoreCase("27")) {
											updatedCGstTaxAmmount = taxDescriptionToCompare.getcGsttaxAmount() + cGsttaxAmount;
											updatedSGstTaxAmmount = taxDescriptionToCompare.getsGsttaxAmount() + sGsttaxAmount;
											taxDescription = new TaxDescription(hsnSac, updatedTaxableValue, 
													cGstRate, updatedCGstTaxAmmount, sGstRate, updatedSGstTaxAmmount);
										} else {
											updatedIGstTaxAmmount = taxDescriptionToCompare.getiGsttaxAmount() + taxAmount;
											taxDescription = new TaxDescription(hsnSac, updatedTaxableValue, hsnDetails.getiGst(), updatedIGstTaxAmmount);
										}
										taxDescriptionList.set(i, taxDescription);
										break;
									}
								}
							} else {
								if (gstCode.equalsIgnoreCase("27")) {
									taxDescription = new TaxDescription(hsnSac, Float.parseFloat(Double.toString(amount)), 
											cGstRate, cGsttaxAmount, sGstRate, sGsttaxAmount);
								} else {
									taxDescription = new TaxDescription(hsnSac, Float.parseFloat(Double.toString(amount)), hsnDetails.getiGst(), taxAmount);
								}
								taxDescriptionList.add(taxDescription);
							}
							
							totalItemInCart += itemQty;
							totalChargableAmount += amount + taxAmount;
							totalTaxableValue += amount;
							iGstAmount += taxAmount;
							totalSGstAmount +=  sGsttaxAmount;
							totalCGstAmount +=  cGsttaxAmount;
							
//							sb = appendData(itemMasterDtl, sb, itemQty);
							sbCust = appendData(itemMasterDtl, sbCust, cartItemDtl.getItemQty());
						}
						
						sbCust.append(emailContentSuffix);
						
						String mark = (null == shippingAddressDetail.getMark() || shippingAddressDetail.getMark().isEmpty()) ? "Not Specified" : shippingAddressDetail.getMark();
						
						String finalEmailStringCustomer = String .format(sbCust.toString(), 
								(null == userDetails.getDisplayName() || userDetails.getDisplayName().isEmpty() || userDetails.getDisplayName().equalsIgnoreCase("null")) ? "Not Specified" : userDetails.getDisplayName().trim(), 
								null == userDetails.getFirstName() ? "" : userDetails.getFirstName()
								, null == userDetails.getLastName() ? "" : userDetails.getLastName(),  
								(null == userDetails.getCntc_num() || userDetails.getCntc_num().isEmpty()) ? "Not Specified": userDetails.getCntc_num(), cartDtlsId,
								mark, 
								(null == shippingAddressDetail.getDestination() || shippingAddressDetail.getDestination().isEmpty()) ? "Not Specified" : shippingAddressDetail.getDestination(), 
								(null == shippingAddressDetail.getTranNm() || shippingAddressDetail.getTranNm().isEmpty()) ? "Not Specified" : shippingAddressDetail.getTranNm(),
										cartDetail.getCartNotes());
						String todaysDate = new Date().toString();
						
						ShippingAddressDetail buyersShippingAddress = null;
						if(cartDetails.getIsDefaultAddress().equalsIgnoreCase("true")) {
							buyersShippingAddress = cartDAO.selectShippingAddressDetailsById(addressDtlsId);
						} else {
							buyersShippingAddress = shippingAddressDetail;
						}
						
						Invoice invoiceDetails = null;
						
						if (gstCode.equalsIgnoreCase("27")) {
							invoiceDetails = new Invoice(Integer.toString(cartDtlsId), todaysDate, Integer.toString(totalItemInCart), 
									"", "", "", "", todaysDate, cartDetails.getTranNm(), cartDetails.getDestination(), "Komal Trading Corporation", 
									"komal@vsnl.com", "27AABPB6207H1Z6", "AABPB6207H", "HDFC BANK", "01452560000971", "HDFC0000145", 
									(null == userDetails.getDisplayName() || userDetails.getDisplayName().isEmpty() || userDetails.getDisplayName().equalsIgnoreCase("null")) ? "Not Specified" : userDetails.getDisplayName().trim(), 
									buyersShippingAddress.getStAddress1(), buyersShippingAddress.getCity(), buyersShippingAddress.getState(), 
									buyersShippingAddress.getState(), gstNo, (float)totalCGstAmount, (float)totalSGstAmount, 
									totalItemInCart, (float)totalChargableAmount, (float)totalTaxableValue, 
									(float)totalCGstAmount, (float)totalSGstAmount, (float)iGstAmount, transactionList, taxDescriptionList, mark); //Create the PDF to send
						} else {
							//Outside Maharashtra GST
							invoiceDetails = new Invoice(Integer.toString(cartDtlsId), todaysDate, Integer.toString(totalItemInCart), 
									"", "", "", "", todaysDate, cartDetails.getTranNm(), cartDetails.getDestination(), "Komal Trading Corporation", 
									"komal@vsnl.com", "27AABPB6207H1Z6", "AABPB6207H", "HDFC BANK", "01452560000971", "HDFC0000145", 
									(null == userDetails.getDisplayName() || userDetails.getDisplayName().isEmpty() || userDetails.getDisplayName().equalsIgnoreCase("null")) ? "Not Specified" : userDetails.getDisplayName().trim(), 
									buyersShippingAddress.getStAddress1(), buyersShippingAddress.getCity(), buyersShippingAddress.getState(), buyersShippingAddress.getState(), 
									gstNo, (float)iGstAmount, totalItemInCart, (float)totalChargableAmount, (float)totalTaxableValue, 
									(float)iGstAmount, (float)iGstAmount, transactionList, taxDescriptionList, mark); //Create the PDF to send
						}
						
						String pdfFilePath = configProperties.getProperty("pdf.savepath")+cartDtlsId + ".pdf";
						
						System.out.println("PDF Filepath: " + pdfFilePath);
						
						if (gstCode.equalsIgnoreCase("27")) {
							generatePdf.genearatePDFForMaharashtra(invoiceDetails, pdfFilePath);
						} else {
							generatePdf.genearatePDFOutsideMaharashtra(invoiceDetails, pdfFilePath);
						}
						
						//Replace the email id with user-email Id
						/*commonUtility.sendEmail(configProperties.getProperty("email.test"), finalEmailStringCustomer, 
								configProperties.getProperty("order.book.subject"), pdfFilePath);*/
						
						try {
							//Send Email To Admin about the order
							commonUtility.sendEmailToAdmin(finalEmailString, 
									configProperties.getProperty("order.book.subject"));
							
							//Send Email To Customer about the order
							commonUtility.sendEmail(custEmailId, finalEmailStringCustomer, 
									configProperties.getProperty("order.book.subject"), pdfFilePath);
						} catch (Exception e) {
//							e.printStackTrace();
						}
						
						//Send Message Notification to alternate number
						messageUtility.sendMessage(cartDetails.getAlternateCntc(), 
								String.format(configProperties.getProperty("sms.orderplaced.success"), cartDtlsId));
						
						//Send Message Notification to Admin
						String custName = userDetails.getFirstName() + (null == userDetails.getLastName() ? "" : " " + userDetails.getLastName());
						String adminContactNumber = KomalIndustriesConstants.ADMIN_MOBILE_NO;
						
						System.out.println("Admin Contact No - " + adminContactNumber + ", \n Message - " + 
								MessageFormat.format(configProperties.getProperty("sms.orderplaced.admin"), 
										custName, cartDtlsId));
						messageUtility.sendMessage(adminContactNumber, 
								MessageFormat.format(configProperties.getProperty("sms.orderplaced.admin"), 
										custName, cartDtlsId));
					}
					
					return new BaseWrapper();
				}
			}
		} catch(DataAccessException e){
			e.printStackTrace();
			throw new Exception(responseMessageProperties.getProperty("error.dataaccess"));
		} 
		
		return new BaseWrapper();
	}

	private boolean existsHsn(String hsnSac, List<TaxDescription> taxDescriptionList) {
		boolean isHsnSacNoExists = false;
		
		for (TaxDescription taxDescription : taxDescriptionList) {
			if (hsnSac.equalsIgnoreCase(taxDescription.getHsnSac().trim())) {
				isHsnSacNoExists = true;
				break;
			}
		}
		
		return isHsnSacNoExists;
	}

	private StringBuilder appendData(ItemMasterDtl itemMasterDtl, StringBuilder sb, int qty) {
		sb.append("<tr>");
		sb.append("<td>" + itemMasterDtl.getItemNm() + "</td>");
		sb.append("<td>" + itemMasterDtl.getUom() + "</td>");
		sb.append("<td>" + qty + "</td>");
		sb.append("</tr>");
		return sb;
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
