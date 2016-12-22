package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.beans.entity.PackagingInfo;
import co.in.replete.komalindustries.dao.ProductDAO;
import co.in.replete.komalindustries.webcontroller.beans.CategoryMasterTO;

public class PackagingInfoDetails extends SimpleTagSupport {

	ProductDAO productDAO;

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		
		JspWriter out=getJspContext().getOut();
		try
		{
			List<PackagingInfo> packagingInfoDetailsList = productDAO.selectPackagingInfoDetails();
			if(packagingInfoDetailsList.size() > 0) {
				int i=1;
				for(PackagingInfo packagingDetails : packagingInfoDetailsList) {
					out.println("<tr>");
					out.println("<td>"+ i++ +"</td>");
					out.println("<td>" + packagingDetails.getPackagingInfoId() + "</td>");
					out.println("<td>" + packagingDetails.getPackagingInfo() + "</td>");
					out.println("<td>" + packagingDetails.getPackagingDescription() + "</td>");
					out.println("<td>");
					if(packagingDetails.getPackagingInfo().contains("\"")) {
						packagingDetails.setPackagingInfo(packagingDetails.getPackagingInfo().replace("\"", "&quot;"));
					}
					if(packagingDetails.getPackagingDescription().contains("\"")) {
						packagingDetails.setPackagingDescription(packagingDetails.getPackagingDescription().replace("\"", "&quot;"));
					}
					out.print("<a data-toggle=\"modal\" data-target=\"#editPackagingInfo\" onClick=\"sendEditPackagingInfo('"
							+ packagingDetails.getPackagingInfoId() + "','"
							+ packagingDetails.getPackagingInfo() + "','"							
							+ packagingDetails.getPackagingDescription() + "'"
							+ ")\"><i class=\"fa fa-pencil\"></i></a>");
//					out.print("&nbsp&nbsp&nbsp&nbsp<a href=\"DeleteCategoryMaster?id=" +packagingDetails.getPackagingInfoId() + "\" onclick=\"return confirm('Are you sure?')\"><i class=\"fa fa-trash\"></i></a>");
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
