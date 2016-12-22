package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.dao.CartDAO;
import co.in.replete.komalindustries.webcontroller.beans.WCartItemTO;
import co.in.replete.komalindustries.webcontroller.beans.WUserOrderInfo;

public class OrderItemDetails extends SimpleTagSupport {
	
	CartDAO cartDAO;
	
	private String orderId;

	public CartDAO getCartDAO() {
		return cartDAO;
	}

	public void setCartDAO(CartDAO cartDAO) {
		this.cartDAO = cartDAO;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public void doTag() throws JspException,IOException
	{
		JspWriter out=getJspContext().getOut();
		try
		{
			List<WCartItemTO> OrderItemList = cartDAO.selectCartItemDetails(Integer.parseInt(orderId));
			if(OrderItemList.size() > 0) {
				int i=1;
				for(WCartItemTO itemDetails : OrderItemList) {
					out.println("<tr>");
					out.println("<td>"+ i++ +"</td>");
					out.println("<td>" + itemDetails.getItemName() + "</td>");
					out.println("<td>" + itemDetails.getQuantity() + "</td>");
					/*out.println("<td>" + itemDetails.getItemPerCartonPrice() + "</td>");
					out.println("<td>"+ itemDetails.getPrice() +"</td>");*/
					out.print("<td>"
							+ "<a data-toggle=\"modal\" data-target=\"#editCartProduct\" style=\"margin:0 10px;\" onclick=\"sendEditCartItemDetails('"
							+ itemDetails.getCartItemDtlsId() + "','"
							+ itemDetails.getItemName() + "','"
							+ itemDetails.getQuantity() /* + "','"*/
							/*+ itemDetails.getItemPerCartonPrice() + "','"
							+ itemDetails.getPrice()*/
							+ "')\"><i class=\"fa fa-pencil\"></i></a>"
							+ "<a onclick=\"return confirm('Do You want to remove this item from cart?');\" href=\"deleteCartItem?cartItemDtlsId="+ itemDetails.getCartItemDtlsId() 
							+ "&orderId=" + orderId + "\" style=\"margin:0 10px;\"><i class=\"fa fa-trash\"></i></a>"
							+ "</td>");
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
