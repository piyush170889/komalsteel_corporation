package co.in.replete.komalindustries.webcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.in.replete.komalindustries.beans.AddItemsToCartTO;
import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.CartDetailRequest;
import co.in.replete.komalindustries.beans.CartDetailsTO;
import co.in.replete.komalindustries.beans.CartItemDetailsListTO;
import co.in.replete.komalindustries.beans.CartItemDtlsTO;
import co.in.replete.komalindustries.beans.OrderEditTO;
import co.in.replete.komalindustries.beans.UserDetailsAllTO;
import co.in.replete.komalindustries.beans.UserOrderDetailsTO;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.AdminDAO;
import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.exception.PrepareViewModelException;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.service.CartService;
import co.in.replete.komalindustries.service.OrderDetailsService;
import co.in.replete.komalindustries.utils.CommonUtility;
import co.in.replete.komalindustries.utils.PrepareViewModelUtilty;
import co.in.replete.komalindustries.webcontroller.beans.EditCartItemDtlsTO;

@Controller
public class OrderManagement extends KomalIndustriesConstants {
	
	@Autowired
	PrepareViewModelUtilty prepareViewModelUtilty;
	
	@Autowired
	CommonUtility commonUtility;
	
	@Autowired
	Properties responseMessageProperties;
	
	@Autowired 
	UserManagementDAO userDAO;
	
	@Autowired
	OrderDetailsService orderDetailsService;
	
	@Autowired
	AdminDAO adminDAO;
	
	@Autowired
	CartService cartService;
	
	/**
	 * Description: get current orders
	 * @param model
	 * @return
	 * @throws PrepareViewModelException 
	 */
	@RequestMapping(value="/order",method=RequestMethod.GET)
	public String orderHistoryPageView(Model model) throws PrepareViewModelException
	{
		String returnViewURL = VIEW_URL_ORDER;
		try {
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, null, null);
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, ERROR_OCCURED);
			returnViewURL = ERROR_PAGE_URL;
		}
		return returnViewURL;
	}
	
	
	
	
	
	/**
	 * Description: Displays Add Order
	 * @param model
	 * @return
	 * @throws PrepareViewModelException 
	 */
	@RequestMapping(value="/add-order",method=RequestMethod.GET)
	public String addOrderView(@RequestParam(value="user-id", required=true) String userTrackId, 
			ModelMap modelAndView) throws PrepareViewModelException
	{
		String returnViewURL = VIEW_URL_ADD_ORDER;
		try {
			UserDetailsAllTO userDetails = orderDetailsService.getUserDetailsByTrackId(userTrackId);
			List<ItemMasterDtl> productsList = orderDetailsService.getActiveProducts();
			modelAndView.addAttribute("stateList", userDAO.getStateList());
			modelAndView.addAttribute("userDetails", userDetails);
			modelAndView.addAttribute("productsList", productsList);
			modelAndView.addAttribute("userId", userTrackId);
		} catch (Exception e) {
			e.printStackTrace();
			returnViewURL = "redirect:order";
		}
		
		return returnViewURL;
	}
	
	/**
	 * Description: Displays Add Order
	 * @param model
	 * @return
	 * @throws PrepareViewModelException 
	 */
	@RequestMapping(value="/add-order",method=RequestMethod.POST)
	public String addOrder(HttpServletRequest servletRequest, RedirectAttributes redirectAttributes, 
			ModelMap modelAndView) throws PrepareViewModelException
	{
		String returnViewURL = "redirect:order";
		String userTrackId = servletRequest.getParameter("userId");
		try {
			String addressId = servletRequest.getParameter("addressId");
			String stAddress = servletRequest.getParameter("submitStAddressVal");
			String city = servletRequest.getParameter("submitCityVal");
			String pincode = servletRequest.getParameter("submitPincoeVal");
			String state = servletRequest.getParameter("submitStateVal");
			String mark = servletRequest.getParameter("submitMarkVal");
			String dest = servletRequest.getParameter("submitDestVal");
			String trans = servletRequest.getParameter("submitTransVal");
			String []orderedItems = servletRequest.getParameterValues("itemMasterDtls[]");
			String []orderedItemsQty = servletRequest.getParameterValues("itemQtyDtls[]");
			String cartNotes = servletRequest.getParameter("cartNotes");
			String alternateContact = servletRequest.getParameter("alternateContact");
			String userGstNo = servletRequest.getParameter("userGstNo1");
			
			/*System.out.println(userTrackId + "-" + addressId + "-" + stAddress + "-" + city + "-" + pincode + "-" + state + "-" + mark + "-" + dest
					 + "-" + trans + "-" + cartNotes + "-" + alternateContact + "-" + userGstNo);
			
			System.out.println("\n Ordered items :-" + Arrays.asList(orderedItems) + "\n orderedItemQty :-" + Arrays.asList(orderedItemsQty));*/
			
			CartDetailsTO cartDetailsTO = new CartDetailsTO();
			
			//Set Cart Item Listing
			List<CartItemDetailsListTO> cartItemsList = new ArrayList<CartItemDetailsListTO>();
			for (int i=0; i<orderedItems.length; i++) {
				CartItemDetailsListTO cartItemDetailsListTo = new CartItemDetailsListTO("", "", orderedItemsQty[i], orderedItems[i], "false", "");
				cartItemsList.add(cartItemDetailsListTo);
			}
			cartDetailsTO.setCartItemsList(cartItemsList);
			
			//Set Shipping Details
			if (null == addressId || addressId.isEmpty()) {
				cartDetailsTO.setIsDefaultAddress("false");
				cartDetailsTO.setStAddress1(stAddress);
				cartDetailsTO.setCity(city);
				cartDetailsTO.setPostalCode(pincode);
				cartDetailsTO.setState(state);
				cartDetailsTO.setCountry("India");
				cartDetailsTO.setMark(mark);
				cartDetailsTO.setDestination(dest);
				cartDetailsTO.setTranNm(trans);
			} else {
				cartDetailsTO.setIsDefaultAddress("true");
				cartDetailsTO.setShippingDtlsId(addressId);
			}
			
			//Set Other Details
			cartDetailsTO.setCartNotes(cartNotes);
			cartDetailsTO.setAlternateCntc(alternateContact);
			cartDetailsTO.setGstNo(userGstNo);
			
			//Prepare Place Order Object
			List<CartDetailsTO> cartDetails = new ArrayList<CartDetailsTO>();
			cartDetails.add(cartDetailsTO);
			CartDetailRequest cartDetailRequest = new CartDetailRequest(cartDetails);
			
			BaseWrapper response = cartService.saveOrder(cartDetailRequest, userTrackId);	//Place Order
			
			if (null != response.getResponseMessage()) {
				if (!response.getResponseMessage().getStatus().equalsIgnoreCase("200")) {
					throw new Exception(response.getResponseMessage().getMessage());
				}
			}
			
			redirectAttributes.addFlashAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, "Order Placed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
			returnViewURL = "redirect:add-order?user-id="+userTrackId;
		}
		
		return returnViewURL;
	}
	
	
	@RequestMapping(value="/printOrderItems",method=RequestMethod.GET)
	public String wPrintOrderedItemsPage(@RequestParam(value="oid") int oid, ModelMap modelMap) {
		
		String returnViewUrl = "orders/printOrderItems";
		try {
			List<CartItemDtlsTO> orderItems = orderDetailsService.getCartItemDetailsByOrderId(oid);
			modelMap.addAttribute("orderedItems", orderItems);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return returnViewUrl;
	}
	
	
	@RequestMapping(value="/orderdetails",method=RequestMethod.GET)
	public String wuserInvoicePageView(Model model, @RequestParam(value = "orderId", required = true) int orderId)
	{ 
	  try{
		List<UserOrderDetailsTO> userOrderDetailsList=userDAO.selectUserOrderDetails(orderId);
		if(userOrderDetailsList.size()==1)
		{
			UserOrderDetailsTO userorderDetails = userOrderDetailsList.get(0);
			
			model.addAttribute("userorderDetails",userorderDetails);
		} else {
		    model.addAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL,"failed to get order  details ");
		}
		
		model.addAttribute("orderId", orderId);
		model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, null, null);
	  } catch(Exception e) {
		  
           e.printStackTrace();
	  }
	  
		return VIEW_URL_ORDER_DTLS;

	}
	
	/**
	 * 
	 * @param {@link OrderEditTO}
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editOrder", method=RequestMethod.POST)
	public String orderDetailsProcessEdit(@ModelAttribute("orderEdit") OrderEditTO request, BindingResult result, Model model) throws Exception {
		String returnViewURL = VIEW_URL_ORDER;
		try {
			orderDetailsService.editOrderDetails(request);
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, SUCCESS_MSSG_LABEL, "Order Details Edited Successfully");
		} catch(PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, dae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, ERROR_OCCURED);
		}
		
		return returnViewURL;
	}
	
	/*@RequestMapping(value="/editLrNo", method=RequestMethod.POST)
	public String doEditLrNo(Model model, HttpServletRequest servletRequest) throws PrepareViewModelException  {
		String returnViewURL = VIEW_URL_ORDER;
		
		try {
			String cartDtldId = servletRequest.getParameter("cartDtlsId");
			
			System.out.println("cartDtldId :"+cartDtldId);
			//LR Details
			String lrNo = servletRequest.getParameter("lrNo");
			String lrDate = servletRequest.getParameter("lrdate");
			String noofcarton = servletRequest.getParameter("noofcarton");
			
			//transportation details
			String transporterNm = servletRequest.getParameter("transporterNm");
			String destination = servletRequest.getParameter("destination");
			String mark = servletRequest.getParameter("mark");
			
			//Courier Details
			String courierNm = servletRequest.getParameter("courierNm");
			String docateNo = servletRequest.getParameter("docateNo");
			String delvryDate = servletRequest.getParameter("delvryDate");
			
			if(null == cartDtldId || cartDtldId.isEmpty() || null == lrNo || lrNo.isEmpty()) {
				throw new Exception("Required Fields Are Empty");
			}
			
			orderDetailsService.editLRNo(cartDtldId, lrNo, lrDate, noofcarton, 
					transporterNm, destination, mark, courierNm, docateNo, delvryDate);
			
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, 
					SUCCESS_MSSG_LABEL, "Dispatch Details Edited Successfully");
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, dae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return returnViewURL;
	}*/
	
	@RequestMapping(value="/editLrNo", method=RequestMethod.POST)
	public String doEditLrNo(Model model, HttpServletRequest servletRequest) throws PrepareViewModelException  {
		String returnViewURL = VIEW_URL_ORDER;
		
		try {
			String cartDtldId = servletRequest.getParameter("cartDtlsId");
			String sendLrMssg = servletRequest.getParameter("sendLrMssg");
			System.out.println("cartDtldId :"+cartDtldId + ", sendLrMssg: " + sendLrMssg);
			//LR Details
			String lrNo = servletRequest.getParameter("lrNo");
			String lrDate = servletRequest.getParameter("lrdate");
			String noofcarton = servletRequest.getParameter("noofcarton");
			
			//transportation details
			String transporterNm = servletRequest.getParameter("transporterNm");
			String destination = servletRequest.getParameter("destination");
			String mark = servletRequest.getParameter("mark");
			
			
			
			if(null == cartDtldId || cartDtldId.isEmpty() || null == lrNo || lrNo.isEmpty()) {
				throw new Exception("Required Fields Are Empty");
			}
			
			orderDetailsService.editLRNo(cartDtldId, lrNo, lrDate, noofcarton, 
					transporterNm, destination, mark, sendLrMssg);
			
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, 
					SUCCESS_MSSG_LABEL, "Dispatch Details Edited Successfully");
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, dae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return returnViewURL;
	}
	
	
	
	@RequestMapping(value="/editCourierDtls", method=RequestMethod.POST)
	public String editCourierDtlsModal(Model model, HttpServletRequest servletRequest) throws PrepareViewModelException  {
		String returnViewURL = VIEW_URL_ORDER;
		
		try {
			String cartDtldId = servletRequest.getParameter("cartDtlsId");
			String sendCourierMssg = servletRequest.getParameter("sendCourierMssg");
			
			//Courier Details
			String courierNm = servletRequest.getParameter("courierNm");
			String docateNo = servletRequest.getParameter("docateNo");
			String delvryDate = servletRequest.getParameter("delvryDate");
			System.out.println("cartDtldId :"+cartDtldId+",courierNm:"+courierNm+", docateNo:"+docateNo+",delvryDate:"+delvryDate);
			
			if(null == cartDtldId || cartDtldId.isEmpty() || null==courierNm || courierNm.isEmpty()) {
				throw new Exception("Required Fields Are Empty");
			}
			
			orderDetailsService.editCourierDtls(cartDtldId,courierNm, docateNo, delvryDate, sendCourierMssg);
			
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, 
					SUCCESS_MSSG_LABEL, "Dispatch Details Edited Successfully");
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, dae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER, model, ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return returnViewURL;
	}
	
	@RequestMapping(value="orderdetails", method=RequestMethod.POST)
	public String editOrderCartItemsProcessEdit(@ModelAttribute("addCartItem") AddItemsToCartTO request, Model model) throws PrepareViewModelException {
		
		String returnViewURL = VIEW_URL_ORDER_DTLS;
		try {
			
			System.out.println("CARTON QTY : " + request.getCartonQty() + " PRODUCT ID : " + request.getItemMasterDtlsId() + " ORDER DTLS ID : " + request.getOrderDtlsId());
			 
			//validate Request 
			/*if(request.getCartonPrice() == 0 || request.getCartonQty() == 0 || request.getItemMasterDtlsId() == 0 || request.getOrderDtlsId() == 0) {
				throw new Exception("Invalid values supplied");
			}*/
			if(request.getCartonQty() == 0 || request.getItemMasterDtlsId() == 0 || request.getOrderDtlsId() == 0) {
				throw new Exception("Invalid values supplied");
			} else {
				//Insert Cart item details
				orderDetailsService.addCartItemToOrder(request);
				
				//Prepare View model
				model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, null, null);
				model.addAttribute("orderId", request.getOrderDtlsId());
				List<UserOrderDetailsTO> userOrderDetailsList=userDAO.selectUserOrderDetails(request.getOrderDtlsId());
				if(userOrderDetailsList.size()==1)
				{
					UserOrderDetailsTO userorderDetails = userOrderDetailsList.get(0);
					
					model.addAttribute("userorderDetails",userorderDetails);
					
					model.addAttribute(SUCCESS_MSSG_LABEL, "Item Added to Order Successfully");
				} else {
				    throw new ServicesException("failed to get order  details ");
				}
				
			}
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model.addAttribute("orderId", request.getOrderDtlsId());
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			model.addAttribute("orderId", request.getOrderDtlsId());
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, dae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("orderId", request.getOrderDtlsId());
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return returnViewURL;
	}
	
	@RequestMapping(value="deleteCartItem", method=RequestMethod.GET)
	public String deleteCartItems(@RequestParam("cartItemDtlsId") int cartItemDtlsId, @RequestParam("orderId") int orderId, Model model) throws PrepareViewModelException {
		
		String returnViewURL = "redirect:orderdetails";
		
		try {
			//Delete item from order cart
			orderDetailsService.deleteItemFromOrderCart(cartItemDtlsId);
			
			//Prepare View model
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, null, null);
			model.addAttribute("orderId", orderId);
			model.addAttribute(SUCCESS_MSSG_LABEL, "Item Deleted from Order Successfully");
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model.addAttribute("orderId", orderId);
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			model.addAttribute("orderId", orderId);
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, dae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("orderId", orderId);
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return returnViewURL;
	}
	
	@RequestMapping(value="/editCartItems", method=RequestMethod.POST)
	public String cartItemDetailsProcessEdit(@ModelAttribute("editCartItemDetails") EditCartItemDtlsTO request, Model model) throws PrepareViewModelException {
		String returnViewURL = "redirect:orderdetails";
		
		try {
			//validate Input params
			/*if(request.getCartItemDtlsId() == 0 || request.getItemPrice() == 0 || request.getItemQty() ==0 || request.getOrderDtlsId() == 0) {
				throw new Exception("Invalid values found");
			}*/
			if(request.getCartItemDtlsId() == 0 || request.getItemQty() ==0 || request.getOrderDtlsId() == 0) {
				throw new Exception("Invalid values found");
			}
			
			//Edit item from order cart
			orderDetailsService.editItemFromOrderCart(request);
			
			//Prepare View model
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, null, null);
			model.addAttribute("orderId", request.getOrderDtlsId());
			model.addAttribute(SUCCESS_MSSG_LABEL, "Order Item Updated Successfully");
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model.addAttribute("orderId", request.getOrderDtlsId());
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			model.addAttribute("orderId", request.getOrderDtlsId());
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, dae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("orderId", request.getOrderDtlsId());
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return returnViewURL;
	}
	
	@RequestMapping(value="/addDiscount", method=RequestMethod.POST)
	public String doProcessAddDiscount(Model model, HttpServletRequest servletRequest) throws PrepareViewModelException {
		String returnViewURL = "redirect:orderdetails";
		String orderDtlsId = servletRequest.getParameter("orderDtlsId");
		try {
			//Check the order details Id recieved form UI side
			if(Integer.parseInt(orderDtlsId) == 0) {
				throw new ServiceException("Invalid Order Details Id");
			} 
			
			//Get discount price
			String discountPrice = servletRequest.getParameter("discountPrice");
			
			//Update order price details
			orderDetailsService.addDiscount(discountPrice, orderDtlsId);
			
			//Prepare View model
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, null, null);
			model.addAttribute("orderId", orderDtlsId);
			model.addAttribute(SUCCESS_MSSG_LABEL, "Discount Added Successfully");
		} catch (PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model.addAttribute("orderId", orderDtlsId);
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			model.addAttribute("orderId", orderDtlsId);
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, dae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("orderId", orderDtlsId);
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_URL_ORDER_DTLS, model, ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return returnViewURL;
	}
	
	/**
	 * Description: Send Message to User
	 * @param model
	 * @return
	 * @throws PrepareViewModelException 
	 */
	@RequestMapping(value="/send-message",method=RequestMethod.GET)
	public String sendMessage(@RequestParam(value="orderId", required=true) String orderId, 
			RedirectAttributes redirectAttribute) throws PrepareViewModelException
	{
		
		try {
			cartService.resendMessagesToUser(orderId);
			redirectAttribute.addFlashAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, "Message Sent Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttribute.addFlashAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return  "redirect:order";
	}
	
	
	/**
	 * Description: Send Message to User
	 * @param model
	 * @return
	 * @throws PrepareViewModelException 
	 */
	@RequestMapping(value="/send-email",method=RequestMethod.GET)
	public String resendEmail(@RequestParam(value="orderId", required=true) String orderId, 
			RedirectAttributes redirectAttribute) throws PrepareViewModelException
	{
		
		try {
			cartService.resendEmailToUserAndAdmin(orderId);
			redirectAttribute.addFlashAttribute(KomalIndustriesConstants.SUCCESS_MSSG_LABEL, "Email Sent Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttribute.addFlashAttribute(KomalIndustriesConstants.ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return  "redirect:order";
	}
	
	/*@RequestMapping(value="/searchOrders", method=RequestMethod.POST)
	public String orderDetailsProcessSearch(HttpServletRequest request, Model model) throws Exception {
		
		String returnViewURL = VIEW_URL_ORDER;
		try {
			if(null == request.getParameter("searchBy") || null == request.getParameter("searchDateRange") || 
					request.getParameter("searchBy").isEmpty() || request.getParameter("searchDateRange").isEmpty()) {
				throw new Exception("Required Values Not Specified");
			} else {
				String searchBy = request.getParameter("searchBy");
				String searchDateRange = request.getParameter("searchDateRange");
				
				orderDetailsService.searchOrders(searchBy, searchDateRange);
			}
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_NM_ORDER_HISTORY, model, SUCCESS_MSSG_LABEL, SUCCESS_OK);
		}  catch(PrepareViewModelException pvmme) {
			pvmme.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_NM_ORDER_HISTORY, model, ERROR_MSSG_LABEL, pvmme.getMessage());
			returnViewURL = ERROR_PAGE_URL;
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_NM_ORDER_HISTORY, model, ERROR_MSSG_LABEL, dae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model = prepareViewModelUtilty.prepareViewModelMap(VIEW_NM_ORDER_HISTORY, model, ERROR_MSSG_LABEL, e.getMessage());
		}
		
		return returnViewURL;
	}*/
}
