package co.in.replete.komalindustries.dao;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardDAOimpl implements DashboardDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Properties sqlProperties; 
	
	@Override
	public int selectNewOrdersCount() {
		
		return jdbcTemplate.queryForObject(sqlProperties.getProperty("select.neworders.count"), Integer.class);
	}
	
	@Override
	public int selectUserDealerRegistrationCount(String userType) {
		
		if(userType.equalsIgnoreCase("All")) {
			String sql = "select count(*) from user_login_dtls where date_format(CREATED_TS, \"%d-%m-%y\")=DATE_FORMAT(NOW(),\"%d-%m-%y\")";
			return (int)jdbcTemplate.queryForObject(sql,Integer.class);
		} else {
			return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.newuserregistrations.count"), new Object[] {userType},Integer.class);
		}
	}
	
	@Override
	public int selectoutOfStockProducts() {
		
		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.productdetails.outofstockcount"),Integer.class);
	}
	
	@Override
	public int selectOrdersCountByOrderStatus(String orderStatus) {
	
		return (int)jdbcTemplate.queryForObject(sqlProperties.getProperty("select.orderscount.byorderstatus"), new Object[] {orderStatus},Integer.class);
	}
}
