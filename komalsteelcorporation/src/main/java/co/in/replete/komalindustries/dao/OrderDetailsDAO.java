package co.in.replete.komalindustries.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.beans.AddItemsToCartTO;
import co.in.replete.komalindustries.beans.CartAndCartItemDetailTO;
import co.in.replete.komalindustries.beans.CartItemDtlsTO;
import co.in.replete.komalindustries.beans.OrderEditTO;
import co.in.replete.komalindustries.beans.entity.AddressDetail;
import co.in.replete.komalindustries.beans.entity.CartDlvryDtl;
import co.in.replete.komalindustries.beans.entity.CartDtl;
import co.in.replete.komalindustries.beans.entity.CartItemDtl;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.ItemsInventoryDtl;
import co.in.replete.komalindustries.exception.ServicesException;
import co.in.replete.komalindustries.webcontroller.beans.EditCartItemDtlsTO;
import co.in.replete.komalindustries.webcontroller.beans.WOrderDetailsTO;

public interface OrderDetailsDAO {

	void updateOrderDetails(OrderEditTO request) throws DataAccessException;

	List<WOrderDetailsTO> searchOrders(String searchBy, String searchDateRange) throws DataAccessException;

	void updateLrNo(String cartDtldId, String lrNo, String lrDate, String noofcarton);

	CartDtl selectOrderDetailsById(int orderId);

	String getContactNumberFromTrackId(String trackId);

	void insertCartItemForAnOrder(AddItemsToCartTO request);

	void deleteItemFromOrderCart(int cartItemDtlsId);

	void updateCartItemDtls(EditCartItemDtlsTO request);

	CartItemDtl selectCartItemDtlsById(int cartItemDtlsId);

	void updateOrderPrice(int orderDtlsId, Float priceToUpdate, String updateType) throws ServicesException;

	CartAndCartItemDetailTO selectCartAndCartItemDetailsByCartItemDtlsId(int cartItemDtlsId);

	List<CartItemDtl> selectCartItemsByCartDtlsId(int orderId);

	ItemsInventoryDtl selectItemInventoryDetailsByItemMasterId(int itemMasterDtlsId);

	void updateInventoryDetailsByItemInventoryDtlsId(Float finalAvailableQty, Float finalBookedQty,
			int itemsInventoryDtlsId);

	CartDlvryDtl selectCartDeliveryDetailsById(int cartDlvrDtlsId);

	AddressDetail selectShippingAddressDetailsById(int shippingAddressId);

	String selectOrderedItemQtyByOrderId(int cartDtlsId);

	void updateTransportDetails(String transporterNm, String destination, String mark, int otherAddressDtlsId);

	CartDtl selectCartDetailsByCartDtlsId(String cartDtldId);

	int selectOtherAddressIdByCartDlvryDtlsId(int cartDlvryDtlsId);

	void updateCourierDetails(String courierNm, String docateNo, String delvryDate, int cartDlvryDtlsId);

	List<ItemMasterDtl> selectActiveProducts();

	List<ItemMasterDtl> selectActiveProductsWithHsnNo();

	String selectTrackingUrlByCourierName(String courierNm);

	List<CartItemDtlsTO> selectCartItemsToByCartDtlsId(int orderId);

	AddressDetail selectTransportationDetailsByCartDlvryDtlsId(int cartDlvryDtlsId);

}
