package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.dao.ProductDAO;
import co.in.replete.komalindustries.webcontroller.beans.CategoryMasterTO;

public class CategoryMasterDetails extends SimpleTagSupport{
	
	ProductDAO productDAO;

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	public void doTag() throws JspException,IOException
	{
		JspWriter out=getJspContext().getOut();
		try
		{
			List<CategoryMasterTO> categoryMasterDetailsList = productDAO.selectCategoryMasterDetails();
			if(categoryMasterDetailsList.size() > 0) {
				int i=1;
				for(CategoryMasterTO categoryMasterDetails : categoryMasterDetailsList) {
					out.println("<tr>");
					out.println("<td>"+ i++ +"</td>");
					out.println("<td>" + categoryMasterDetails.getId() + "</td>");
					out.println("<td>" + categoryMasterDetails.getName() + "</td>");
					out.println("<td>" + categoryMasterDetails.getCatDesc() + "</td>");
					/*out.println("<td>" + categoryMasterDetails.getUrl() + "</td>");*/
					out.println("<td>");
					out.print("<a data-toggle=\"modal\" data-target=\"#editCategory\" onClick=\"sendCategoryDetailsEdit('"
							+ categoryMasterDetails.getId() +"','"
							+ categoryMasterDetails.getName() +"','"
							+ categoryMasterDetails.getCatDesc() +"'"
							/*+ categoryMasterDetails.getUrl() +"'"*/
							+ ")\"><i class=\"fa fa-pencil\"></i></a>");
//					out.print("&nbsp&nbsp&nbsp&nbsp<a href=\"DeleteCategoryMaster?id=" +categoryMasterDetails.getId() + "\" onclick=\"return confirm('Are you sure?')\"><i class=\"fa fa-trash\"></i></a>");
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
