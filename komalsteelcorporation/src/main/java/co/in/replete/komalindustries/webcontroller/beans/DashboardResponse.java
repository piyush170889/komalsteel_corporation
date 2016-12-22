package co.in.replete.komalindustries.webcontroller.beans;

public class DashboardResponse {

	private String newOrders;
	
	private String newDealerRegistrations;
	
	private String newDistributorsRegistration;
	
	private String outOfStockProducts;
	
	private String ordersDispatched;
	
	private String ordersBooked;
	
	private String ordersCompleted;
	
	private String ordersPendingApproval;

	public DashboardResponse() {}
	
	public DashboardResponse(String newOrders, String newDealerRegistrations, String newDistributorsRegistration,
			String outOfStockProducts, String ordersDispatched, String ordersBooked, String ordersCompleted,
			String ordersPendingApproval) {
		super();
		this.newOrders = newOrders;
		this.newDealerRegistrations = newDealerRegistrations;
		this.newDistributorsRegistration = newDistributorsRegistration;
		this.outOfStockProducts = outOfStockProducts;
		this.ordersDispatched = ordersDispatched;
		this.ordersBooked = ordersBooked;
		this.ordersCompleted = ordersCompleted;
		this.ordersPendingApproval = ordersPendingApproval;
	}

	public String getNewOrders() {
		return newOrders;
	}

	public void setNewOrders(String newOrders) {
		this.newOrders = newOrders;
	}

	public String getNewDealerRegistrations() {
		return newDealerRegistrations;
	}

	public void setNewDealerRegistrations(String newDealerRegistrations) {
		this.newDealerRegistrations = newDealerRegistrations;
	}

	public String getNewDistributorsRegistration() {
		return newDistributorsRegistration;
	}

	public void setNewDistributorsRegistration(String newDistributorsRegistration) {
		this.newDistributorsRegistration = newDistributorsRegistration;
	}

	public String getOutOfStockProducts() {
		return outOfStockProducts;
	}

	public void setOutOfStockProducts(String outOfStockProducts) {
		this.outOfStockProducts = outOfStockProducts;
	}

	public String getOrdersDispatched() {
		return ordersDispatched;
	}

	public void setOrdersDispatched(String ordersDispatched) {
		this.ordersDispatched = ordersDispatched;
	}

	public String getOrdersBooked() {
		return ordersBooked;
	}

	public void setOrdersBooked(String ordersBooked) {
		this.ordersBooked = ordersBooked;
	}

	public String getOrdersCompleted() {
		return ordersCompleted;
	}

	public void setOrdersCompleted(String ordersCompleted) {
		this.ordersCompleted = ordersCompleted;
	}

	public String getOrdersPendingApproval() {
		return ordersPendingApproval;
	}

	public void setOrdersPendingApproval(String ordersPendingApproval) {
		this.ordersPendingApproval = ordersPendingApproval;
	}
	
	
}
