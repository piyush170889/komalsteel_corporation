package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.dao.ProductDAO;
import co.in.replete.komalindustries.webcontroller.beans.CategoryMasterTO;

public class CategoryName extends SimpleTagSupport{

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
					out.println("<option value=\""+ categoryMasterDetails.getId() + "\">" + categoryMasterDetails.getName() + "</option>");
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
