package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.dao.ProductDAO;
import co.in.replete.komalindustries.webcontroller.beans.WItemDetailsTO;

public class ProductDetails extends SimpleTagSupport {

	ProductDAO productDAO;
	
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public void doTag() throws JspException, IOException {
		
		JspWriter out = getJspContext().getOut();
		
		try {
			List<WItemDetailsTO> productDetailsList = productDAO.selectAllItemsInfo();
			if(productDetailsList.size() > 0) {
				int i=1;
				for(WItemDetailsTO productDetails : productDetailsList) {
					out.println("<tr>");
					out.println("<td>" + i + "</td>");
					out.println("<td>" + productDetails.getItemNo() + "</td>");
					out.println("<td>" + productDetails.getIsActive() + "</td>");
					out.println("<td><img src=\"Image/productImg?pid="+productDetails.getProductId()+"\" width=\"100\" height=\"100\" /></td>");
					out.println("<td>" + productDetails.getProductName() + "</td>");
					out.println("<td>" + productDetails.getHsnNo() + "</td>");
					out.println("<td>" + productDetails.getCategoryName() + "</td>");
					out.println("<td>" + productDetails.getSubCategoryName() + "</td>");
//					out.println("<td>" + productDetails.getProductDescription() + "</td>");
					out.println("<td>" + productDetails.getItemsInMasterCarton() + "</td>");
					out.println("<td>" + productDetails.getPerUnitPrice() + "</td>");
					out.println("<td>" + productDetails.getProductPackagingInfo() + "</td>");
					out.println("<td>" + productDetails.getMasterCartonQtyRange() + "</td>");
					out.println("<td>" + productDetails.getMasterCartonQtyIncVal() + "</td>");
					/*if((Float.parseFloat(productDetails.getProductAvlQty()) - Float.parseFloat(productDetails.getProductBookedQty())) <= Float.parseFloat(productDetails.getProductThresholdVal())) {
					  out.println("<td style=\"background-color:red;\">" + productDetails.getProductAvlQty() + "</td>");
					} else {
					  out.println("<td>" + productDetails.getProductAvlQty() + "</td>");
					}
					out.println("<td>" + productDetails.getProductBookedQty() + "</td>");
					out.println("<td>" + productDetails.getProductThresholdVal() + "</td>");
					out.println("<td>" + productDetails.getProductMrp() + "</td>");*/
					out.println("<td>");
//					out.println("<a href=\"productDetailsForEdit?productId="+productDetails.getProductId()+"\"><i class=\"fa fa-pencil\"></i></a>");
					out.print("<a data-toggle=\"modal\" data-target=\"#editProduct\" onClick=\"sendProductDetails('"
							+ productDetails.getProductId() +"','"
							+ productDetails.getProductName() +"','"
							+ productDetails.getProductCategory() +"','"
							+ productDetails.getProductSubCategory() +"','"
							+ productDetails.getProductDescription() +"','"
							+ productDetails.getItemsInMasterCarton() +"','"
							+ productDetails.getMasterCartonPrice() +"','"
							+ productDetails.getProductPackaging() +"','"
							+ productDetails.getProductPackagingInfo() + "','"
							+ productDetails.getMasterCartonQtyRange() + "','"
							+ productDetails.getMasterCartonQtyIncVal() + "','"
							/*+ productDetails.getProductAvlQty() +"','"
							+ productDetails.getProductThresholdVal() + "','"*/
							+ productDetails.getItemNo() + "','"
							+ productDetails.getHsnDtlsId() + "','"
							+ productDetails.getPerUnitPrice() + "','"
							+ i	+ "')\"><i class=\"fa fa-pencil\"></i></a>");
					if(productDetails.getIsActive().equalsIgnoreCase("Active")) {
						out.print("&nbsp&nbsp&nbsp&nbsp<a title=\"Inactivate Product\" href=\"DeleteProduct?id=" + productDetails.getProductId() + 
								"&num=" + i +"\" onclick=\"return confirm('Are you sure you want to deactivate this product?')\"><i class=\"fa fa-times\"></i></a>");
					} else if(productDetails.getIsActive().equalsIgnoreCase("Inactive")) {
						out.print("&nbsp&nbsp&nbsp&nbsp<a title=\"Activate Product\" href=\"activateproduct?id=" + productDetails.getProductId() + 
								"&num=" + i +"\" onclick=\"return confirm('Are you sure you want to activate this product?')\"><i class=\"fa fa-check\"></i></a>");
					}
					
					/*out.println("&nbsp;&nbsp;&nbsp;<a data-toggle=\"modal\" data-target=\"#editInventoryDetails\" title=\"Update Inventory Details\" onClick=\"sendProductInventoryDetails('"
							+ productDetails.getProductId() + "','"
							+ productDetails.getProductName() + "','"
							+ productDetails.getProductBookedQty() + "','"
							+ productDetails.getProductAvlQty() + "','"
							+ productDetails.getProductMrp() 
							+ "')\"><i class=\"fa fa-external-link\" aria-hidden=\"true\"></i></a>");*/
					out.print("</td>");
					out.println("</tr>");
					++i;
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
