package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.dao.ProductDAO;
import co.in.replete.komalindustries.webcontroller.beans.CategoryMasterTO;

public class SubcategoryMasterDetails extends SimpleTagSupport {

	ProductDAO productDAO;

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	public void doTag() throws JspException,IOException
	{
		JspWriter out=getJspContext().getOut();
		try
		{
			List<CategoryMasterTO> subcategoryMasterDetailsList = productDAO.selectSubCategoryMasterDetails();
			if(subcategoryMasterDetailsList.size() > 0) {
				int i=1;
				for(CategoryMasterTO subcategoryMasterDetails : subcategoryMasterDetailsList) {
					out.println("<tr>");
					out.println("<td>"+ i++ +"</td>");
					out.println("<td>" + subcategoryMasterDetails.getId() + "</td>");
					out.println("<td>" + subcategoryMasterDetails.getName() + "</td>");
					out.println("<td>" + subcategoryMasterDetails.getParantNm() + "</td>");
					out.println("<td>" + subcategoryMasterDetails.getCatDesc() + "</td>");
					out.println("<td>" + subcategoryMasterDetails.getUrl() + "</td>");
					out.println("<td>");
					out.print("<a data-toggle=\"modal\" data-target=\"#editProduct\" onClick=\"sendSubCategoryDetailsEdit('"
							+ subcategoryMasterDetails.getId() +"','"
							+ subcategoryMasterDetails.getName() +"','"
							+ subcategoryMasterDetails.getCatDesc() +"','"
							+ subcategoryMasterDetails.getParentId() +"','"
							+ subcategoryMasterDetails.getUrl() + "'"
							+ ")\"><i class=\"fa fa-pencil\"></i></a>");
//					out.print("&nbsp&nbsp&nbsp&nbsp<a href=\"DeleteSubCategoryMaster?id=" +subcategoryMasterDetails.getId() + "\" onclick=\"return confirm('Are you sure?')\"><i class=\"fa fa-trash\"></i></a>");
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
