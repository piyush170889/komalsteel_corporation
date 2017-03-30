package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.beans.ProductRefillDetailsTo;
import co.in.replete.komalindustries.dao.ProductDAO;

public class ProductInventoryRefillDetails extends SimpleTagSupport {
	
	ProductDAO productDAO;
	
	private String searchDateRange;
	
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	public void setSearchDateRange(String searchDateRange) {
		this.searchDateRange = searchDateRange;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
	
		try {	
			List<ProductRefillDetailsTo> productInventoryRefillDetailsList ;
			
			if (null == searchDateRange || searchDateRange.isEmpty()) {
				productInventoryRefillDetailsList = productDAO.selectProductInventoryRefillDetails();
			} else {
				productInventoryRefillDetailsList = productDAO.selectProductInventoryRefillDetailsByDateRange(searchDateRange);
			}
			
			
			for(ProductRefillDetailsTo productInventoryRefillDetails : productInventoryRefillDetailsList) {
				out.println("<tr>");
				out.println("<td>" + productInventoryRefillDetails.getItemNo() + "</td>");
				out.println("<td><img src=\"Image/productImg?pid="+ productInventoryRefillDetails.getItemMasterDtlsId() +"\" width=\"100\" height=\"100\" /></td>");
				out.println("<td>" + productInventoryRefillDetails.getItemNm() + "(" + productInventoryRefillDetails.getUom() + ")" + "</td>");
				out.println("<td>" + productInventoryRefillDetails.getIsActive() + "</td>");
				out.println("<td>" + productInventoryRefillDetails.getCategory() + "</td>");
				out.println("<td>" + productInventoryRefillDetails.getSubcategory() + "</td>");
				out.println("<td>" + productInventoryRefillDetails.getRefillDt() + "</td>");
				out.println("<td>" + productInventoryRefillDetails.getReceivedQty() + "</td>");
				out.println("<td>" + productInventoryRefillDetails.getRefillPrice() + "</td>");
				out.println("<td>" + productInventoryRefillDetails.getPerUnitCostPrice() + "</td>");
				out.println("<td>" + productInventoryRefillDetails.getMrp() + "</td>");
				out.println("<td>" + productInventoryRefillDetails.getComments() + "</td>");
				out.println("</tr>");
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
