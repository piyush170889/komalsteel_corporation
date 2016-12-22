package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.dao.DashboardDAO;

public class OrdersCount extends SimpleTagSupport {

  DashboardDAO dashboardDAO;
  String orderStatus;
  
    public void setOrderStatus(String orderStatus) {
      this.orderStatus = orderStatus;
    }
    
	public void setDashboardDAO(DashboardDAO dashboardDAO) {
		this.dashboardDAO = dashboardDAO;
	}

	@Override
	public void doTag() throws JspException, IOException {
		
		JspWriter out = getJspContext().getOut();
		
		try {
			int outOfStockProducts = dashboardDAO.selectOrdersCountByOrderStatus(orderStatus);
			out.println(outOfStockProducts);
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			out.println("-11");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("-11");
		}
	}
}
