package co.in.replete.komalindustries.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.in.replete.komalindustries.dao.AdminDAO;
import co.in.replete.komalindustries.dao.AdminDAOImpl;
import co.in.replete.komalindustries.dao.CartDAO;
import co.in.replete.komalindustries.dao.CartDAOImpl;
import co.in.replete.komalindustries.dao.DashboardDAO;
import co.in.replete.komalindustries.dao.DashboardDAOimpl;
import co.in.replete.komalindustries.dao.ProductDAO;
import co.in.replete.komalindustries.dao.ProductDAOImpl;
import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.dao.UserManagementDAOImpl;

@Configuration
public class TagHandlerClassConfig extends BaseModuleConfiguration{

	@Bean(name="dashboardDAO")
    public DashboardDAO giveDashBoardDAO() {
    	return new DashboardDAOimpl();
    }
	
	@Bean(name="productDAO")
    public ProductDAO giveProductDAO() {
    	return new ProductDAOImpl();
    }
	
	@Bean(name="userDAO")
	public UserManagementDAO giveUserDAO() {
		return new UserManagementDAOImpl();
	}
	
	@Bean(name="cartDAO")
	public CartDAO giveCartDAO() {
		return new CartDAOImpl();
	}
	
	@Bean(name="adminDAO")
	public AdminDAO giveAdminDAO() {
		return new AdminDAOImpl();
	}
}
