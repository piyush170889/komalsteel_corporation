package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.beans.UserDetailsTO;
import co.in.replete.komalindustries.dao.CartDAO;

public class UserDetailsByTrackId extends SimpleTagSupport {

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
			if(null != trackId && !trackId.isEmpty()) {
				UserDetailsTO userDetails = cartDAO.selectUserUserDetailsByTrackId(trackId);
				if(null != userDetails) {
					int i=1;
					out.println("<tr>");
					out.println("<td>"+ i++ +"</td>");
					out.println("<td>"+"<a href=\"userdetails?trackid="+userDetails.getUserTrackId()+"\">"+ userDetails.getFirstName() + " " + userDetails.getLastName() +"</a>"+"</td>");
					out.println("<td>" + userDetails.getDisplayName() + "</td>");
					out.println("<td>" + userDetails.getStatus() + "</td>");
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
