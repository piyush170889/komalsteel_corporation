package co.in.replete.komalindustries.utils;

public enum UDValues {

	BOOLEAN_TRUE("true"),
	BOOLEAN_FALSE("false"),
	USER_STATUS_ACTIVE("Active"),
	USER_STATUS_INACTIVE("Inactive"),
	CART_STATUS_BOOKED("Booked"),
	CART_STATUS_PENDING("Pending"), 
	CART_STATUS_PACKED("Packed"),
	CART_STATUS_DISPATCHED("Dispatched"),
	CART_STATUS_COMPLETED("Completed"),
	CART_STATUS_ORDERED("Ordered"),
	CART_STATUS_CANCELLED("Cancelled"),
	PAYMENT_MODE_ONLINE("Online"),
	PAYMENT_MODE_COD("COD"),
	USER_TYPE_DEALER("Dealer"),
	USER_TYPE_DISTRIBUTOR("Distributor"),
	USER_TYPE_ADMIN("Admin"),
	ADDRES_TYPE_DEFAULT_SHIPPING("Default Shipping"),
	CONFIG_VAL_SERVICE_TAX("ServiceTax"),
	CONFIG_VAL_VAT("VatTax"), 
	ADDRESS_TYPE_SHIPPING("Shipping"),
	DEFAULT_IMAGE("DefaultImage");
	
	private String udValue;
	
	UDValues(String udvalue) {
		this.udValue = udvalue;
	}
	
	@Override
	public String toString() {
		
		return this.udValue.toString();
	}
}
