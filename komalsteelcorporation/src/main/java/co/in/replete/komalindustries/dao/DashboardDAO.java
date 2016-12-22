package co.in.replete.komalindustries.dao;

public interface DashboardDAO {

	int selectNewOrdersCount();

	int selectUserDealerRegistrationCount(String userType);

	int selectoutOfStockProducts();

	int selectOrdersCountByOrderStatus(String orderStatus);
}
