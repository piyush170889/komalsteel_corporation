package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.dao.CartDAO;
import co.in.replete.komalindustries.webcontroller.beans.WUserOrderInfo;

public class UserOrderDetailsList extends SimpleTagSupport {
	
	private CartDAO cartDAO;
	
	private String trackId;

	public void setCartDAO(CartDAO cartDAO) {
		this.cartDAO = cartDAO;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public void doTag() throws JspException,IOException
	{
		JspWriter out=getJspContext().getOut();
		
		try
		{
			List<WUserOrderInfo> userOrderDetailsList = cartDAO.selectUserOrderInfo(trackId);
			if(userOrderDetailsList.size() > 0) {
				int i=1;
				for(WUserOrderInfo orderDetails : userOrderDetailsList) {
					out.println("<tr>");
					out.println("<td>"+ i++ +"</td>");
					out.println("<td>"+"<a href=\"orderdetails?orderId="+orderDetails.getOrderId()+"\">"+ orderDetails.getOrderId() +"</a>"+"</td>");
					out.println("<td>" + orderDetails.getOrderDate() + "</td>");
					out.println("<td>" + orderDetails.getStatus() + "</td>");
//					out.println("<td><a href=\"invoiceDetails?invoiceId="+orderDetails.getInvoiceId() + "\">" + orderDetails.getInvoiceId()+"</td></a>");
					out.print("</td>");
					out.println("</tr>");
				}
			}
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			out.println("-11");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("-11");
		}
	}
}
