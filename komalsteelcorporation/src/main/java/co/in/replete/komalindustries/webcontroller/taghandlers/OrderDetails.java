package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.beans.CartItemDtlsTO;
import co.in.replete.komalindustries.dao.CartDAO;
import co.in.replete.komalindustries.webcontroller.beans.WOrderDetailsTO;

public class OrderDetails extends SimpleTagSupport {

	private String searchBy;
	
	private String searchDateRange;
	
	private CartDAO cartDAO;

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public void setSearchDateRange(String searchDateRange) {
		this.searchDateRange = searchDateRange;
	}

	public void setCartDAO(CartDAO cartDAO) {
		this.cartDAO = cartDAO;
	}

	@Override
    public void doTag() throws JspException,IOException
    {
    	JspWriter out=getJspContext().getOut();
    	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    	
    	try{
    		List<WOrderDetailsTO> orderDetailsList;
    		if(null!=searchBy && !searchBy.isEmpty() || null!=searchDateRange && !searchDateRange.isEmpty()) {
    			orderDetailsList = cartDAO.searchOrders(searchBy, searchDateRange);
    		} else {
    			orderDetailsList= cartDAO.selectOrderDetails();
    		}
    		
			if(orderDetailsList.size() > 0) {
				
				for(WOrderDetailsTO orderDetails : orderDetailsList) {
					int i=1;
					String mark = orderDetails.getMark();
					String destination = orderDetails.getDestination();
					String transporterName = orderDetails.getTransNm();
					
					out.println("<tr>");
					int orderDetailsId = orderDetails.getCartDtlId();
					out.println("<td>"+"<a href='orderdetails?orderId="+orderDetailsId+"'>"+ orderDetailsId +"</a>"+ "</td>");
					out.println("<td>" + df.format(orderDetails.getOrderDate()) + "</td>");
					List<CartItemDtlsTO> orderItemdetails = cartDAO.selectOrderItemDetailsByCartId(orderDetailsId);
					out.println("<td>" + orderDetails.getStreet1()+ orderDetails.getCity()+" "+ orderDetails.getState()+
							" "+orderDetails.getCountry()+" "+orderDetails.getPostalCode()+ "</br> Mark : " + mark + 
							"</br> Destination : " + destination + "</br> Transporter Name : "  + transporterName
							+ "</br>VAT No : " + orderDetails.getVatTinNo() + "</td>");
					/*out.println("<td>" + orderDetails.getOrderPrice() + "</td>");*/
					out.println("<td>" + orderDetails.getNotes() + "</td>");
					out.println("<td>" +"<a href='userdetails?trackid="+ orderDetails.getTrackId()+ "'>"+ orderDetails.getFirstName()+" "+orderDetails.getLastName() +"</a>"+"</td>");
					out.println("<td>" + orderDetails.getCnctNum() + "</td>");
					out.println("<td>");
					for(CartItemDtlsTO itemDetails : orderItemdetails) {
						out.println(i + ". " + itemDetails.getItemNm() + "(" + itemDetails.getUom() + ")" + " - " + itemDetails.getItemQty() + "</br>");
						++i;
					}
					out.println("</td>");
//					out.println("<td>" + orderDetails.getDeliveryDate() + "</td>");
//					out.println("<td>" + orderDetails.getPaymentMode() + "</td>");
//					out.println("<td>" + orderDetails.getPaymentStatus() + "</td>");
/*					out.println("<td>" + orderDetails.getDeliveryType() + "</td>");*/
//					out.println("<td>"+orderDetails.getInvoiceId()+"</td>");
					out.println("<td>" + orderDetails.getStatus() + 
							"<div style=\"display:none\" id=\"odraddr_" + orderDetailsId + "\" />" + orderDetails.getStreet1() + "</td>");
					if(null == orderDetails.getLrNo()) {
						out.println("<td>Not Specified</td>");
					} else {
						String lrDate = "Not Specified";
						if(null != orderDetails.getLrNoDate()) {
							lrDate = df.format(orderDetails.getLrNoDate());
						}
						out.println("<td><strong>LR NO - </strong>" + orderDetails.getLrNo() + "<br/>"
								+ "<strong>LR Date - </strong>" + lrDate + "<br/>"
								+ "<strong>Items Loaded - </strong>" + orderDetails.getNoOfCartonLoaded() + 
								"</td>");
					}
					out.println("<td>");
					out.print("<a data-toggle=\"modal\" data-target=\"#editOrder\" style=\"margin:0 5px;\" onClick=\"sendEditOrderDetails('"
							+ orderDetailsId + "','"
							+ orderDetails.getStatus() + "','"
							+ orderDetails.getOrderDate() + "','"
							+ orderDetails.getDeliveryDate() + "','"
							+ orderDetails.getCity() + "','"
							+ orderDetails.getState() + "','"
							/*+ orderDetails.getPaymentMode() + "','"
							+ orderDetails.getPaymentStatus() + "','"
							+ orderDetails.getOrderPrice() + "','"
							*/+ "odraddr_" + orderDetailsId + "','"
							+ orderDetails.getCnctNum() + "'"
							+ ")\"><i class=\"fa fa-pencil\"></i></a>"
							+ "<a data-toggle=\"modal\" data-target=\"#editLrNo\" style=\"margin:0 10px;\" onClick=\"sendEditLrNo('"
							+ orderDetails.getLrNo() + "','"
							+ orderDetails.getCartDtlId() + "','"
							+ orderDetails.getLrNoDate() + "','"
							+ orderDetails.getNoOfCartonLoaded() + "','"
							+ mark + "','"
							+ transporterName + "','"
							+ destination + "','"
							+ orderDetails.getCourierNm() + "','"
							+ orderDetails.getDocateNo() + "','"
							+ orderDetails.getDeliveryDate() + "'"
							+ ")\")><i class=\"fa fa-truck\"></i></a>");
					out.print("</td>");
					out.println("</tr>");
    	       }
			}
    	}
    	catch (DataAccessException dae) {
			dae.printStackTrace();
			out.println("-11");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("-11");
		}

    }
}
