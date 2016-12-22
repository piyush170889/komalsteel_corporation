package co.in.replete.komalindustries.service;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.CartDetailRequest;
import co.in.replete.komalindustries.beans.ItemsDetailsRequest;

public interface CartService {

	BaseWrapper getOrdersList(String userMasterId, int pageNum) throws Exception;
	
	BaseWrapper saveOrder(CartDetailRequest saveOrderRequest, String trackId/*, HttpServletResponse servletResponse*/) throws Exception;
	
	BaseWrapper getItemsList(ItemsDetailsRequest itemDetailsRequest) throws Exception;

	BaseWrapper searchItems(String keyword) throws Exception;
}
