package co.in.replete.komalindustries.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.beans.AddItemsToCartTO;
import co.in.replete.komalindustries.beans.OrderEditTO;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.webcontroller.beans.EditCartItemDtlsTO;
import co.in.replete.komalindustries.webcontroller.beans.WOrderDetailsTO;

public interface OrderDetailsService {

	void editOrderDetails(OrderEditTO request) throws DataAccessException, Exception;

	List<WOrderDetailsTO> searchOrders(String searchBy, String searchDateRange) throws DataAccessException, Exception;

	void editLRNo(String cartDtldId, String lrNo, String lrDate, String noofcarton) throws Exception;

	void addCartItemToOrder(AddItemsToCartTO request) throws ServicesException;

	void deleteItemFromOrderCart(int cartItemDtlsId) throws ServicesException;

	void editItemFromOrderCart(EditCartItemDtlsTO request) throws ServicesException;

	void addDiscount(String discountPrice, String orderDtlsId) throws ServicesException;
	
}
