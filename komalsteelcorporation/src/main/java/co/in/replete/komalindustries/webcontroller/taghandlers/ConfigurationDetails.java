package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.dao.AdminDAO;
import co.in.replete.komalindustries.webcontroller.beans.WConfigurationTO;

public class ConfigurationDetails extends SimpleTagSupport{

	
	private AdminDAO adminDAO;

	public AdminDAO getAdminDAO() {
		return adminDAO;
	}

	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}
	
	public void doTag() throws JspException, IOException
	{
		JspWriter out=getJspContext().getOut();
		try
		{
			List<WConfigurationTO> configurationDetailsList = adminDAO.selectWConfigurationList();
			if(configurationDetailsList.size() > 0) {
				int i=1;
				for(WConfigurationTO configurationDetails : configurationDetailsList) {
					out.println("<tr>");
					out.println("<td>"+ i++ +"</td>");
					out.println("<td>" + configurationDetails.getConfigName() + "</td>");
					out.println("<td>" + configurationDetails.getConfigValue() + "</td>");
					out.println("<td>" + configurationDetails.getConfigDesc() + "</td>");
					out.println("<td>");
					out.print("<a data-toggle=\"modal\" data-target=\"#editProduct\" onclick=\"sendEditConfigValues('"
							+ configurationDetails.getConfigName() + "','"
							+ configurationDetails.getConfigValue() + "','"
							+ configurationDetails.getConfigDesc() + "')\"><i class=\"fa fa-pencil\"></i></a>");
					/*out.print("&nbsp&nbsp&nbsp&nbsp<a href=\"DeleteProduct?id=" +configurationDetails.getConfigName() + "\" onclick=\"return confirm('Are you sure?')\"><i class=\"fa fa-trash\"></i></a>");*/
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
