package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.beans.ProductInventoryDetailsTo;
import co.in.replete.komalindustries.dao.ProductDAO;

public class ProductInventoryDetails extends SimpleTagSupport {
	
	ProductDAO productDAO;
	
	int doShowOnlyOutOfStockProducts;
	
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	public void setDoShowOnlyOutOfStockProducts(int doShowOnlyOutOfStockProducts) {
		this.doShowOnlyOutOfStockProducts = doShowOnlyOutOfStockProducts;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		try {	
			List<ProductInventoryDetailsTo> productInventoryDetailsList;
			
			if(doShowOnlyOutOfStockProducts == 1) {
				productInventoryDetailsList=productDAO.selectInventoryDetailsOfOutOfStockProducts();
			} else {
				productInventoryDetailsList=productDAO.selectInventoryDetailsOfProducts();
			}
			
			if (productInventoryDetailsList.size()>0) {
				for (ProductInventoryDetailsTo productInvventoryDetails : productInventoryDetailsList) {
					Float availableQty = Float.parseFloat(productInvventoryDetails.getAvlQty());
					Float bookedQty = Float.parseFloat(productInvventoryDetails.getBookedQty());
					Float thrsHldVal = Float.parseFloat(productInvventoryDetails.getThrhldVal());
					int itemMasterDtlsId = productInvventoryDetails.getItemMasterDtlsId();
					String itemNm = productInvventoryDetails.getItemNm();
					String itemUom = productInvventoryDetails.getUom();
					String itemMrp = productInvventoryDetails.getMrp();
					
					displayRow(availableQty, bookedQty, thrsHldVal, itemMasterDtlsId, itemNm, 
							itemUom, itemMrp, productInvventoryDetails, out);
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

	private void displayRow(Float availableQty, Float bookedQty, Float thrsHldVal, int itemMasterDtlsId, String itemNm,
			String itemUom, String itemMrp, ProductInventoryDetailsTo productInvventoryDetails, JspWriter out) throws IOException {
		out.println("<tr>");
		out.println("<td>"+ productInvventoryDetails.getItemNo()+"</td>");
		out.println("<td>"+ productInvventoryDetails.getIsActive()+"</td>");
		out.println("<td><img src=\"Image/productImg?pid="+ itemMasterDtlsId +"\" width=\"100\" height=\"100\" /></td>");
		out.println("<td>"+ itemNm +"</td>");
		out.println("<td>"+productInvventoryDetails.getCategory()+"</td>");
		out.println("<td>"+productInvventoryDetails.getSubcategory()+"</td>");
		out.println("<td>"+ itemUom +"</td>");
		if (availableQty <= thrsHldVal) {
			out.println("<td style=\"background:red;\">"+ availableQty +"</td>");
		} else if ((availableQty-bookedQty) <= thrsHldVal) {
			out.println("<td style=\"background:yellow;\">"+ availableQty +"</td>");
		} else {
			out.println("<td>"+ availableQty +"</td>");
		}
		out.println("<td>"+ bookedQty +"</td>");
		out.println("<td>"+ thrsHldVal +"</td>");
		out.println("<td>"+ itemMrp +"</td>");
		out.print("<td><a data-toggle=\"modal\" data-target=\"#editInventoryDetails\" onClick=\"sendProductInventoryDetails('"
			+ itemMasterDtlsId +"','"	
			+ itemNm + "(" + itemUom + ")" +"','"
			+ bookedQty +"','"
			+ availableQty +"','"
			+ itemMrp 
			+ "')\"><i class=\"fa fa-pencil\"></i></a></td>");
		out.println("</tr>");
		
	}

}
